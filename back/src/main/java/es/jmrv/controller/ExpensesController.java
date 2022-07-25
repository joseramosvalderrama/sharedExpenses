package es.jmrv.controller;

import es.jmrv.model.Duty;
import es.jmrv.dto.ExpenseDto;
import es.jmrv.dto.PersonDto;
import es.jmrv.model.Expense;
import es.jmrv.model.Person;
import es.jmrv.service.ExpenseService;
import es.jmrv.service.PersonService;
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

    @Inject
    private PersonService personService;

    @Get("/expense")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ExpenseDto> getExpenses(){
        return this.expenseService.findAllDto();
    }

    @Post("/person/{personId}/expense")
    public HttpResponse createExpense(@PathVariable(name = "personId") Long personId, @Body @Valid Expense expense){
        Optional<Person> personOp = this.personService.findById(personId);
        if(personOp.isEmpty()){
            return HttpResponse.notFound();
        }
        this.expenseService.save(personOp.get(), expense);
        this.personService.updateBalances();
        return HttpResponse.created("Created");
    }

    @Delete("/expense/{expenseId}")
    public HttpResponse deleteExpense(@PathVariable(name = "expenseId") Long expenseId){
        Optional<Expense> expenseOp = this.expenseService.findById(expenseId);
        if(expenseOp.isEmpty()){
            return HttpResponse.notFound();
        }
        this.expenseService.delete(expenseOp.get());
        this.personService.updateBalances();
        return HttpResponse.created("Deleted");
    }

    @Post("/person")
    public HttpResponse createPerson(@Body @Valid Person person){
        this.personService.save(person);
        return HttpResponse.created("Created");
    }

    @Get("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDto> getPeople(){
        return this.personService.findAllDto();
    }

    @Delete("/person/{personId}")
    public HttpResponse deletePerson(@PathVariable(name = "personId") Long personId){
        Optional<Person> personOp = this.personService.findById(personId);
        if(personOp.isEmpty()){
            return HttpResponse.notFound();
        }
        this.personService.delete(personOp.get());
        this.personService.updateBalances();
        return HttpResponse.created("Deleted");
    }

    @Get("/duty")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Duty> getDuties(){
        return this.personService.calculateDuties();
    }
}
