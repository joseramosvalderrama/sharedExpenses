package es.jmrv.service;

import es.jmrv.model.Duty;
import es.jmrv.dto.ExpenseDto;
import es.jmrv.dto.PersonDto;
import es.jmrv.model.Expense;
import es.jmrv.model.Person;
import es.jmrv.repository.ExpenseRepository;
import es.jmrv.repository.PersonRepository;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class ExpenseService {
    @Inject
    private ExpenseRepository expenseRepository;

    @Inject
    private PersonRepository personRepository;

    @EventListener
    void onStartup(ServerStartupEvent event) {
        if(StreamSupport.stream(personRepository.findAll().spliterator(), false).count() == 0){
            Random random = new Random();
            Person p1 = new Person("Pepito");
            Person p2 = new Person("Manolito");

            StreamSupport.stream(this.personRepository.saveAll(Arrays.asList(p1, p2)).spliterator(), false)
                    .forEach(p -> {
                        double cost = random.nextDouble(10);
                        Expense expense = new Expense(cost, "Expense by " + p.getName(), new Date());
                        this.saveExpense(p.getId(), expense);
                    });
        }
    }

    public List<ExpenseDto> getExpensesDto(){
        List<ExpenseDto> expenseDtos = new ArrayList<>();
        this.getExpenses().stream()
                .forEach(e -> {
                    expenseDtos.add(new ExpenseDto(e.getId(), e.getCost(), e.getDescription(), e.getDate(), e.getPerson().getName()));
                });
        return expenseDtos;
    }

    public List<Expense> getExpenses() {
        return StreamSupport.stream(this.expenseRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Person savePerson(Person person) {
        return this.personRepository.save(person);
    }

    @Transactional
    public void saveExpense(long personId, Expense expense) {
        Person person = this.findPersonById(personId).get();
        expense.setPerson(person);
        this.expenseRepository.save(expense);
        //person.addExpense(expense);
        //this.personRepository.update(person);
        this.updateBalances();
    }

    private void updateBalances() {
        Iterable<Person> personIterable = this.personRepository.findAll();
        long totalPeople = StreamSupport.stream(personIterable.spliterator(), false).count();
        double individualPart = this.getTotalCost() / totalPeople;
        StreamSupport.stream(personIterable.spliterator(), false).forEach((p) -> {
            p.updateBalance(individualPart);
        });
        this.personRepository.updateAll(personIterable);
    }

    public List<PersonDto> findPeopleDtos(){
        return this.findPeople().stream().map(p -> new PersonDto(p.getId(), p.getName(), p.getBalance())).collect(Collectors.toList());
    }

    public List<Person> findPeople() {
        return StreamSupport.stream(this.personRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Person> findPersonById(Long id){
        return this.personRepository.findById(id);
    }

    private double getTotalCost(){
        return this.getExpenses().stream().mapToDouble(e -> e.getCost()).sum();
    }

    public List<Duty> calculateDuties() {
        return this.calculateDuties(this.findPeople());
    }

    private List<Duty> calculateDuties(List<Person> personList){
        List<Duty> duties = new ArrayList<>();
        Person[] payers = personList.stream()
                .filter(p -> p.getBalance() > 0)
                .sorted(Comparator.comparingDouble(Person::getBalance).reversed())
                .toArray(Person[]::new);
        Person[] debtors = personList.stream()
                .filter(p -> p.getBalance() < 0)
                .sorted(Comparator.comparingDouble(Person::getBalance))
                .toArray(Person[]::new);
        int i=0, j=0;
        while(i < payers.length && j < debtors.length){
            duties.add(Duty.build(debtors[j], payers[i]));
            if(payers[i].getBalance() - debtors[j].getBalance() <= 0){
                i++;
            }
            else{
                j++;
            }
        }
        return duties;
    }
}
