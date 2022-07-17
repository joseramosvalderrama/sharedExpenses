package es.jmrv.model;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class DutyTest {
    private Person debtor;
    private Person payer;

    @BeforeEach
    void setup(){
        this.debtor = new Person("Debtor");
        this.payer = new Person("Payer");
    }

    @Test
    void givenABiggerPayerThenDutyisEqualtoDebtor(){
           debtor.setBalance(-4);
           payer.setBalance(5);
           Duty duty = Duty.build(debtor, payer);
           assertEquals(4, duty.getAmount());
           assertEquals("Debtor", duty.getDebtor());
           assertEquals("Payer", duty.getPayer());
    }

    @Test
    void givenABiggerDebtorThenDutyisEqualtoPayer(){
        debtor.setBalance(-8);
        payer.setBalance(5);
        Duty duty = Duty.build(debtor, payer);
        assertEquals(5, duty.getAmount());
        assertEquals("Debtor", duty.getDebtor());
        assertEquals("Payer", duty.getPayer());
    }
}
