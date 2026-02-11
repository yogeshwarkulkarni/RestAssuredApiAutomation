package camel.api.tests;

import camel.api.clients.CommentsClient;
import camel.api.model.getCommentIdResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThan;

public class CommentsApiTest {
    private final CommentsClient comments = new CommentsClient();

    @Test
    public void getCommentById_shouldReturn200_andCorrectFields() {
        comments.getCommentById(1)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .time(lessThan(10000L))
                .body("id", equalTo(1))
                .body("postId", notNullValue())
                .body("name", not(isEmptyOrNullString()))
                .body("email", not(isEmptyOrNullString()))
                .body("email", containsString("@"))
                .body("body", not(isEmptyOrNullString()));
    }

    @Test
    public void getAllComments_shouldReturn500Comments() {
        comments.getAllComments()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", equalTo(500))
                .body("[0].id", equalTo(1))
                .body("postId", everyItem(notNullValue()));
    }

    @Test
    public void getCommentById_shouldMapResponseToPojo() {
        getCommentIdResponse comment = comments.getCommentByIdAsPojo(1);
        Assert.assertEquals(comment.getId(), 1);
        Assert.assertTrue(comment.getPostId() > 0);
        Assert.assertNotNull(comment.getName());
        Assert.assertNotNull(comment.getEmail());
        Assert.assertNotNull(comment.getBody());
    }

    @Test
    public void getCommentsByPostId_shouldReturnOnlyCommentsForThatPost() {
        comments.getCommentsByPostId(1)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", equalTo(5))
                .body("postId", everyItem(equalTo(1)));
    }

    @Test
    public void getCommentById_withNonExistentId_shouldReturn404() {
        comments.getCommentById(99999)
                .then()
                .statusCode(404);
    }
}
