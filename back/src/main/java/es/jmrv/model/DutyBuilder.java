package es.jmrv.model;

import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Singleton
public class DutyBuilder {

    public List<Duty> build(List<Person> personList){
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
            Duty duty = new Duty();
            duty.build(debtors[j], payers[i]);
            duties.add(duty);
            if((payers[i].getBalance() - debtors[j].getBalance() * (-1)) <= 0){
                i++;
            }
            else{
                j++;
            }
        }
        return duties;
    }
}
