package es.jmrv.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String name;

    private double balance;

    public Person(){

    }

    public Person(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void updateBalance(double balance){
        this.balance += balance;
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
}
