package es.jmrv.dto;

import java.util.Date;

public class ExpenseDto {
    private Long id;
    private double cost;
    private String description;
    private Date date;
    private String person;

    public ExpenseDto(Long id, double cost, String description, Date date, String person) {
        this.id = id;
        this.cost = cost;
        this.description = description;
        this.date = date;
        this.person = person;
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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
