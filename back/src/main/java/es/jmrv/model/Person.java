package es.jmrv.model;


import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String name;

    private double balance;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "person")
    private List<Expense> expenses;

    public Person(){
        this.balance = 0;
        this.expenses = new ArrayList<>();
    }

    public Person(String name) {
        this.name = name;
        this.balance = 0;
        this.expenses = new ArrayList<>();
    }

    public void updateBalance(double individualCost){
        this.balance = getMyTotalExpense() - individualCost;
    }

    private double getMyTotalExpense(){
        double totalExpense = 0;
        for(Expense e: this.expenses){
            totalExpense += e.getCost();
        }
        return totalExpense;
    }

    public void addExpense(Expense expense){
        this.expenses.add(expense);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
