package es.jmrv.controller;

import es.jmrv.model.Duty;
import es.jmrv.dto.ExpenseDto;
import es.jmrv.dto.PersonDto;
import es.jmrv.model.Expense;
import es.jmrv.model.Person;
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

    @Get("/expense")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ExpenseDto> getExpenses(){
        return this.expenseService.getExpensesDto();
    }

    @Post("/person/{personId}/expense")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse createExpense(@PathVariable(name = "personId") Long personId, @Body @Valid Expense expense){
        Optional<Person> personOp = this.expenseService.findPersonById(personId);
        if(personOp.isEmpty()){
            return HttpResponse.notFound();
        }
        Person person = personOp.get();
        this.expenseService.saveExpense(personId, expense);
        return HttpResponse.created("Created");
    }

    @Post("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse createPerson(@Body @Valid Person person){
        this.expenseService.savePerson(person);
        return HttpResponse.created("Created");
    }

    @Get("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDto> getPersonBalances(){
        return this.expenseService.findPeopleDtos();
    }

    @Get("/duty")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Duty> getDuties(){
        return this.expenseService.calculateDuties();
    }
}
