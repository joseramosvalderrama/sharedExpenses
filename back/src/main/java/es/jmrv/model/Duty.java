package es.jmrv.model;

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

    public static Duty build(Person debtor, Person payer){
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
