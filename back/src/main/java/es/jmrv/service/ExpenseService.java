package es.jmrv.service;

import es.jmrv.dto.ExpenseDto;
import es.jmrv.dto.PersonDto;
import es.jmrv.entity.Expense;
import es.jmrv.entity.Person;
import es.jmrv.repository.ExpenseRepository;
import es.jmrv.repository.PersonRepository;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class ExpenseService {
    @Inject
    ExpenseRepository expenseRepository;

    @Inject
    PersonRepository personRepository;

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
}
