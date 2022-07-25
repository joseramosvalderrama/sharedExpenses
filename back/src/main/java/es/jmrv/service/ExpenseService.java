package es.jmrv.service;

import es.jmrv.dto.ExpenseDto;
import es.jmrv.model.Expense;
import es.jmrv.model.Person;
import es.jmrv.repository.ExpenseRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class ExpenseService {
    @Inject
    private ExpenseRepository expenseRepository;

    public List<ExpenseDto> findAllDto(){
        List<ExpenseDto> expenseDtos = new ArrayList<>();
        this.findAll().stream()
                .forEach(e -> {
                    expenseDtos.add(new ExpenseDto(e.getId(), e.getCost(), e.getDescription(), e.getDate(), e.getPerson().getName()));
                });
        return expenseDtos;
    }

    public void save(Person person, Expense expense) {
        expense.setPerson(person);
        this.expenseRepository.save(expense);
    }


    public Optional<Expense> findById(Long expenseId) {
        return this.expenseRepository.findById(expenseId);
    }

    public void delete(Expense expense) {
        this.expenseRepository.delete(expense);
    }

    private List<Expense> findAll() {
        return StreamSupport.stream(this.expenseRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
