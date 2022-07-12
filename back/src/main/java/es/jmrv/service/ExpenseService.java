package es.jmrv.service;

import es.jmrv.entity.Expense;
import es.jmrv.entity.Person;
import es.jmrv.repository.ExpenseRepository;
import es.jmrv.repository.PersonRepository;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import java.util.*;
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
            Person p1 = new Person("Pepito", 0);
            Person p2 = new Person("Manolito", 0);

            Random random = new Random();
            StreamSupport.stream(this.personRepository.saveAll(Arrays.asList(p1, p2)).spliterator(), false)
                    .forEach(p -> {
                        Expense expense = new Expense();
                        double cost = random.nextDouble(10);
                        expense.setCost(cost);
                        expense.setDescription("Example");
                        expense.setDate(new Date());
                        this.updateBalances(p, cost);
                        this.saveExpense(p.getId(), new Expense());
                    });
        }
    }

    public List<Expense> getExpenses() {
        return StreamSupport.stream(this.expenseRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Person savePerson(Person person) {
        return this.personRepository.save(person);
    }

    public Expense saveExpense(long personId, @Valid Expense expense) {
        Person person = this.personRepository.findById(personId).get();
        expense.setPerson(person);
        return this.expenseRepository.save(expense);
    }

    public List<Person> findPersonBalances() {
        return StreamSupport.stream(this.personRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Person> findPersonById(Long id){
        return this.personRepository.findById(id);
    }

    public void updateBalances(Person person, double cost) {
        Iterable<Person> personIterable = this.personRepository.findAll();
        long totalPeople = StreamSupport.stream(personIterable.spliterator(), false).count();
        double individualPart = cost / totalPeople;
        StreamSupport.stream(personIterable.spliterator(), false).forEach((p) -> {
            double updateBalance = p.getId() != person.getId() ? individualPart * (-1) : individualPart;
            p.updateBalance(updateBalance);
        });
    }
}
