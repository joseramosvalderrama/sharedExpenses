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

    public static List<Duty> build(List<Person> personList){
        List<Duty> duties = new ArrayList<>();
        Person[] payers = personList.stream()
                .filter(p -> p.getBalance() > 0)
                .sorted(Comparator.comparingDouble(Person::getBalance).reversed())
                .toArray(Person[]::new);
        Person[] debtors = personList.stream()
                .filter(p -> p.getBalance() < 0)
                .sorted(Comparator.comparingDouble(Person::getBalance))
                .toArray(Person[]::new);
        int i=0, j=0;
        while(i < payers.length && j < debtors.length){
            duties.add(build(debtors[j], payers[i]));
            if((payers[i].getBalance() - debtors[j].getBalance() * (-1)) <= 0){
                i++;
            }
            else{
                j++;
            }
        }
        return duties;
    }

    protected static Duty build(Person debtor, Person payer){
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
        return new Duty(debtor.getName(), payer.getName(), debt);
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
