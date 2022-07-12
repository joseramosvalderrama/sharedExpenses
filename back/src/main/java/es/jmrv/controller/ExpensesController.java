package es.jmrv.controller;

import es.jmrv.entity.Expense;
import es.jmrv.entity.Person;
import es.jmrv.service.ExpenseService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller("/api/shared")
public class ExpensesController {
    @Inject
    private ExpenseService expenseService;

    @Get("/expenses")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Expense> getExpenses(){
        return this.expenseService.getExpenses();
    }

    @Post("/{personId}/expenses")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Expense> createExpense(@PathVariable(name = "personId") Long personId, @Body @Valid Expense expense){
        Optional<Person> person = this.expenseService.findPersonById(personId);
        if(person.isEmpty()){
            return HttpResponse.notFound();
        }
        this.expenseService.updateBalances(person.get(), expense.getCost());
        return HttpResponse.created(this.expenseService.saveExpense(personId, expense));
    }

    @Post("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Person> createPerson(@Body @Valid Person person){
        return HttpResponse.created(this.expenseService.savePerson(person));
    }

    @Get("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersonBalances(){
        return this.expenseService.findPersonBalances();
    }
}
