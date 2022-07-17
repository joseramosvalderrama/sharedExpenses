package es.jmrv.repository;

import es.jmrv.model.Expense;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
}
