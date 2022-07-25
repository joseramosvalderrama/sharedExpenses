package es.jmrv;

import es.jmrv.model.Expense;
import es.jmrv.model.Person;
import es.jmrv.repository.PersonRepository;
import es.jmrv.service.ExpenseService;
import es.jmrv.service.PersonService;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.stream.StreamSupport;

@Singleton
public class DatabaseLoader {
    @Inject
    private PersonRepository personRepository;

    @Inject
    private ExpenseService expenseService;

    @Inject
    private PersonService personService;

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
                        this.expenseService.save(p, expense);
                    });
            personService.updateBalances();
        }
    }
}
