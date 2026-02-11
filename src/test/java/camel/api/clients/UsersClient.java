package camel.api.clients;

import camel.api.model.getUserIdResponse;
import io.restassured.response.Response;

public class UsersClient extends BaseClient {

    public Response getUserById(int id) {
        return req()
                .when()
                .get("/users/{id}", id);
    }

    public Response getAllUsers() {
        return req()
                .when()
                .get("/users");
    }

    public getUserIdResponse getUserByIdAsPojo(int id) {
        return req()
                .when()
                .get("/users/{id}", id)
                .then()
                .statusCode(200)
                .extract()
                .as(getUserIdResponse.class);
    }
}
