package es.jmrv.model;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class DutyBuilderTest {

    private List<Person> personList;

    @Inject
    private DutyBuilder dutyBuilder;

    @BeforeEach
    void setup(){
        personList = new ArrayList<>();
    }

    @Test
    void givenEmptyListThenEmptyDuties(){
        List<Duty> duties = dutyBuilder.build(personList);
        assertEquals(0, duties.size());
    }

    @Test
    void givenOnePersonThenEmptyDuties(){
        personList.add(new Person("Manolo"));
        List<Duty> duties = dutyBuilder.build(personList);
        assertEquals(0, duties.size());
    }

    @Test
    void givenOneDebtorTwoPayersThenTwoDuties(){
        Person debtor1 = new Person("Manolo");
        Person payer1 = new Person("Luis");
        Person payer2 = new Person("Sergio");
        debtor1.setBalance(-16.5);
        payer1.setBalance(8.5);
        payer2.setBalance(8);
        personList.addAll(List.of(payer1,debtor1, payer2));
        List<Duty> duties = dutyBuilder.build(personList);
        assertEquals(2, duties.size());
        assertEquals(8.5, duties.get(0).getAmount());
        assertEquals(8, duties.get(1).getAmount());
    }

    @Test
    void givenTwoDebtorOnePayerThenTwoDuties(){
        Person debtor1 = new Person("Manolo");
        Person payer1 = new Person("Luis");
        Person debtor2 = new Person("Sergio");
        debtor1.setBalance(-9);
        payer1.setBalance(17);
        debtor2.setBalance(-8);
        personList.addAll(List.of(debtor2,payer1,debtor1));
        List<Duty> duties = dutyBuilder.build(personList);
        assertEquals(2, duties.size());
        assertEquals(9, duties.get(0).getAmount());
        assertEquals(8, duties.get(1).getAmount());
    }

    @Test
    void givenTwoDebtorTwoPayerThenTwoDuties(){
        Person debtor1 = new Person("Manolo");
        Person payer1 = new Person("Luis");
        Person debtor2 = new Person("Sergio");
        Person payer2 = new Person("Maria");
        debtor1.setBalance(-25);
        payer1.setBalance(17);
        payer2.setBalance(24);
        debtor2.setBalance(-16);
        personList.addAll(List.of(debtor2,payer1,debtor1,payer2));
        List<Duty> duties = dutyBuilder.build(personList);
        assertEquals(3, duties.size());
        assertEquals(24, duties.get(0).getAmount());
        assertEquals(1, duties.get(1).getAmount());
        assertEquals(16, duties.get(2).getAmount());
    }
}
