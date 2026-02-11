package camel.api.clients;

import camel.api.model.getPostIdResponse;
import io.restassured.response.Response;

public class PostsClient  extends BaseClient {

    public Response getPostById(int id) {
        return req()
                .when()
                .get("/posts/{id}", id);
    }

    public Response getAllPosts() {
        return req()
                .when()
                .get("/posts");
    }

    public Response getPostsByUserId(int userId) {
        return req()
                .when()
                .queryParam("userId", userId)
                .get("/posts");
    }

    public getPostIdResponse getPostByIdAsPojo(int id) {
        return req()
                .when()
                .get("/posts/{id}", id)
                .then()
                .statusCode(200)
                .extract()
                .as(getPostIdResponse.class);
    }
}
