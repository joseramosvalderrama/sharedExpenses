package es.jmrv.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    private Person sut;

    @BeforeEach
    void setup(){
        sut = new Person("Person");
    }

    @Test
    void givenNoExpensesWhenUpdateBalanceThenNegativeBalance(){
        sut.updateBalance(5);
        assertEquals(-5, sut.getBalance());
    }

    @Test
    void givenOneExpenseWhenUpdateBalanceThenNegativeBalance(){
        sut.addExpense(new Expense(5, "Cine", new Date()));
        sut.updateBalance(6);
        assertEquals(-1, sut.getBalance());
    }

    @Test
    void givenOneExpenseWhenUpdateBalanceThenPositiveBalance(){
        sut.addExpense(new Expense(5, "Cine", new Date()));
        sut.updateBalance(4);
        assertEquals(1, sut.getBalance());
    }

    @Test
    void givenMultipleExpensesWhenUpdateBalanceThenNegativeBalance(){
        sut.addExpense(new Expense(5, "Cine", new Date()));
        sut.addExpense(new Expense(4, "Patatas", new Date()));
        sut.updateBalance(12);
        assertEquals(-3, sut.getBalance());
    }

    @Test
    void givenMultipleExpensesWhenUpdateBalanceThenPositiveBalance(){
        sut.addExpense(new Expense(5, "Cine", new Date()));
        sut.addExpense(new Expense(4, "Patatas", new Date()));
        sut.updateBalance(6);
        assertEquals(3, sut.getBalance());
    }
}
