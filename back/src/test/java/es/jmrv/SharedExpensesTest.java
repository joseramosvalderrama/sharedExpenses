package es.jmrv;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.jmrv.entity.Expense;
import es.jmrv.entity.Person;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import java.util.Date;

@MicronautTest
class SharedExpensesTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/shared";

    private static final double COST = 10;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    public void testGetExpenses(RequestSpecification spec){
        spec
                .when()
                    .get(BASE_URL + "/expense")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);
    }

    @Test
    public void testPostExpense(RequestSpecification spec) throws Exception{
        Person person = new Person("Pepito");
        Person createdPerson = spec
                .request()
                        .body(objectMapper.writeValueAsString(person))
                                .contentType(ContentType.JSON)
                .when()
                        .post(BASE_URL  + "/person")
                .then().extract().as(Person.class);

        Expense expense = new Expense();
        expense.setCost(COST);
        expense.setDescription("Example");
        expense.setDate(new Date());

        Expense createdExpense = spec
                .given()
                    .request()
                        .body(objectMapper.writeValueAsString(expense))
                        .contentType(ContentType.JSON)
                .when()
                    .post(BASE_URL + "/person/" + createdPerson.getId() + "/expense")
                .then()
                    .assertThat().statusCode(200)
                    .extract().as(Expense.class);

        Assertions.assertEquals(COST, createdExpense.getCost());
    }

}
