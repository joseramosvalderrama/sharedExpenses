package es.jmrv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.jmrv.dto.PersonDto;
import es.jmrv.model.Expense;
import es.jmrv.model.Person;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.Date;

@MicronautTest
class RestAssuredTest {

    @Inject
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/shared";

    private static final double COST = 10;

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
        Person person = new Person("Assured_Person");
        spec
                .request()
                .body(objectMapper.writeValueAsString(person))
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL  + "/person")
                .then().statusCode(201);

        PersonDto[] people = spec
                .when()
                .get(BASE_URL  + "/person")
                .as(PersonDto[].class);

        Expense expense = new Expense(COST, "Example", new Date());

        spec
                .given()
                .request()
                .body(objectMapper.writeValueAsString(expense))
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/person/" + people[0].getId() + "/expense")
                .then()
                .assertThat().statusCode(201);
    }

    @Test
    public void testGetPerson(RequestSpecification spec){
        spec
                .when()
                .get(BASE_URL + "/person")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testPostPerson(RequestSpecification spec) throws JsonProcessingException {
        Person person = new Person("Assured_Person_2");
        spec
                .request()
                .body(objectMapper.writeValueAsString(person))
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL  + "/person")
                .then().statusCode(201);
    }

    @Test
    public void testGetDuty(RequestSpecification spec){
        spec
                .when()
                .get(BASE_URL + "/duty")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

}
