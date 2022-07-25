package es.jmrv.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Duty {
    private String debtor;
    private String payer;
    private double amount;

    public Duty(){}

    private Duty(String debtor, String payer, double amount) {
        this.debtor = debtor;
        this.payer = payer;
        this.amount = amount;
    }

    public void build(Person debtor, Person payer){
        this.debtor = debtor.getName();
        this.payer = payer.getName();
        double rest = payer.getBalance() - debtor.getBalance() * (-1);
        double debt = 0;
        if(rest < 0){
            debt = payer.getBalance();
            debtor.setBalance(rest);
            payer.setBalance(0);
        }
        else{
            debt = debtor.getBalance() * (-1);
            payer.setBalance(rest);
            debtor.setBalance(0);
        }
        this.amount = debt;
    }

    public String getDebtor() {
        return debtor;
    }

    public String getPayer() {
        return payer;
    }

    public double getAmount() {
        return amount;
    }
}
