package es.jmrv.model;

import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.StreamSupport;

@Singleton
public class BalanceUpdater {

    public void updateBalance(List<Person> personList){
        double individualPart = this.getTotalCost(personList) / personList.size();
        updateBalance(personList, individualPart);
    }

    private void updateBalance(List<Person> personList, double individualPart){
        for(Person person : personList){
            person.updateBalance(individualPart);
        }
    }

    private double getTotalCost(List<Person> personList){
        double total = 0;
        for(Person person : personList){
            total += person.getMyTotalExpense();
        }
        return total;
    }
}
