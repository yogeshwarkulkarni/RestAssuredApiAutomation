package camel.api.tests;

import camel.api.clients.TodosClient;
import camel.api.model.getTodoIdResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThan;

public class TodosApiTest {
    private final TodosClient todos = new TodosClient();

    @Test
    public void getTodoById_shouldReturn200_andCorrectFields() {
        todos.getTodoById(1)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .time(lessThan(10000L))
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(isEmptyOrNullString()))
                .body("completed", notNullValue());
    }

    @Test
    public void getAllTodos_shouldReturn200Todos() {
        todos.getAllTodos()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", equalTo(200))
                .body("[0].id", equalTo(1))
                .body("userId", everyItem(notNullValue()))
                .body("completed", everyItem(notNullValue()));
    }

    @Test
    public void getTodoById_shouldMapResponseToPojo() {
        getTodoIdResponse todo = todos.getTodoByIdAsPojo(1);
        Assert.assertEquals(todo.getId(), 1);
        Assert.assertTrue(todo.getUserId() > 0);
        Assert.assertNotNull(todo.getTitle());
        // completed can be true or false
    }

    @Test
    public void getTodosByUserId_shouldReturnOnlyTodosForThatUser() {
        todos.getTodosByUserId(1)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", equalTo(20))
                .body("userId", everyItem(equalTo(1)))
                .body("[0].id", equalTo(1));
    }

    @Test
    public void getTodoById_withNonExistentId_shouldReturn404() {
        todos.getTodoById(99999)
                .then()
                .statusCode(404);
    }
}
