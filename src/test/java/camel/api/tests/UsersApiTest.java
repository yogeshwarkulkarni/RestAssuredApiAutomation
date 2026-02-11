package camel.api.tests;

import camel.api.clients.UsersClient;
import camel.api.model.getUserIdResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThan;

public class UsersApiTest {
    private final UsersClient users = new UsersClient();

    @Test
    public void getUserById_shouldReturn200_andCorrectFields() {
        users.getUserById(1)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .time(lessThan(10000L))
                .body("id", equalTo(1))
                .body("name", not(isEmptyOrNullString()))
                .body("username", not(isEmptyOrNullString()))
                .body("email", not(isEmptyOrNullString()))
                .body("email", containsString("@"))
                .body("address.city", not(isEmptyOrNullString()));
    }

    @Test
    public void getAllUsers_shouldReturn10Users() {
        users.getAllUsers()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .time(lessThan(15000L))
                .body("size()", equalTo(10))
                .body("[0].id", equalTo(1))
                .body("[9].id", equalTo(10))
                .body("name", everyItem(not(isEmptyOrNullString())))
                .body("email", everyItem(containsString("@")));
    }

    @Test
    public void getUserById_shouldMapResponseToPojo() {
        getUserIdResponse user = users.getUserByIdAsPojo(1);
        Assert.assertEquals(user.getId(), 1);
        Assert.assertNotNull(user.getName());
        Assert.assertNotNull(user.getUsername());
        Assert.assertNotNull(user.getEmail());
    }

    @Test
    public void getUserById_withNonExistentId_shouldReturn404() {
        users.getUserById(99999)
                .then()
                .statusCode(404);
    }
}
