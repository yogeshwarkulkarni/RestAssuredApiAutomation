package camel.api.clients;

import camel.api.model.getTodoIdResponse;
import io.restassured.response.Response;

public class TodosClient extends BaseClient {

    public Response getTodoById(int id) {
        return req()
                .when()
                .get("/todos/{id}", id);
    }

    public Response getAllTodos() {
        return req()
                .when()
                .get("/todos");
    }

    public Response getTodosByUserId(int userId) {
        return req()
                .when()
                .queryParam("userId", userId)
                .get("/todos");
    }

    public getTodoIdResponse getTodoByIdAsPojo(int id) {
        return req()
                .when()
                .get("/todos/{id}", id)
                .then()
                .statusCode(200)
                .extract()
                .as(getTodoIdResponse.class);
    }
}
