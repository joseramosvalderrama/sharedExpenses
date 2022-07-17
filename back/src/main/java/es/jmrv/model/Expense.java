package es.jmrv.model;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private double cost;

    private String description;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Expense(){}

    public Expense(double cost, String description, Date date) {
        this.cost = cost;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
