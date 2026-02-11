package camel.api.tests;

import camel.api.clients.PostsClient;
import org.testng.annotations.Test;
import org.testng.Assert;
import camel.api.model.getPostIdResponse;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThan;


public class PostsApiTest {
    private final PostsClient posts = new PostsClient();

    @Test
    public void getPostById_shouldReturn200_andCorrectId() {
        posts.getPostById(1)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .time(lessThan(10000L))
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(isEmptyOrNullString()))
                .body("body", not(isEmptyOrNullString()));
    }

    @Test
    public void getAllPosts_shouldReturn100Posts() {
        posts.getAllPosts()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .time(lessThan(15000L))
                .body("size()", equalTo(100))
                .body("[0].id", notNullValue())
                .body("[0].userId", notNullValue())
                .body("[0].title", not(isEmptyOrNullString()))
                .body("[0].body", not(isEmptyOrNullString()));
    }

    @Test
    public void getPostsByUserId_shouldReturnOnlyPostsForThatUser() {
        posts.getPostsByUserId(1)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", equalTo(10))
                .body("userId", everyItem(equalTo(1)))
                .body("[0].id", equalTo(1))
                .body("[9].id", equalTo(10));
    }

    @Test
    public void getPostById_shouldMapResponseToPojo() {

        getPostIdResponse post = posts.getPostByIdAsPojo(1);

        Assert.assertEquals(post.getId(), 1);
        Assert.assertTrue(post.getUserId() > 0);
        Assert.assertNotNull(post.getTitle());
        Assert.assertNotNull(post.getBody());
    }

    @Test
    public void getPostById_withNonExistentId_shouldReturn404() {
        posts.getPostById(99999)
                .then()
                .statusCode(404);
    }
}
