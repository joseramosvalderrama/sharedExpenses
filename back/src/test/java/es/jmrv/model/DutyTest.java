package es.jmrv.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

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
           Duty duty = new Duty();
           duty.build(debtor, payer);
           assertEquals(4, duty.getAmount());
           assertEquals("Debtor", duty.getDebtor());
           assertEquals("Payer", duty.getPayer());
    }

    @Test
    void givenABiggerDebtorThenDutyisEqualtoPayer(){
        debtor.setBalance(-8);
        payer.setBalance(5);
        Duty duty = new Duty();
        duty.build(debtor, payer);
        assertEquals(5, duty.getAmount());
        assertEquals("Debtor", duty.getDebtor());
        assertEquals("Payer", duty.getPayer());
    }
}
