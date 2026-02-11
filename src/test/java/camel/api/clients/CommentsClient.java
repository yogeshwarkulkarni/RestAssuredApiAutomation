package camel.api.clients;

import camel.api.model.getCommentIdResponse;
import io.restassured.response.Response;

public class CommentsClient extends BaseClient {

    public Response getCommentById(int id) {
        return req()
                .when()
                .get("/comments/{id}", id);
    }

    public Response getAllComments() {
        return req()
                .when()
                .get("/comments");
    }

    public Response getCommentsByPostId(int postId) {
        return req()
                .when()
                .queryParam("postId", postId)
                .get("/comments");
    }

    public getCommentIdResponse getCommentByIdAsPojo(int id) {
        return req()
                .when()
                .get("/comments/{id}", id)
                .then()
                .statusCode(200)
                .extract()
                .as(getCommentIdResponse.class);
    }
}
