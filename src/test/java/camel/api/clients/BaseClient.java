package camel.api.clients;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static camel.api.config.TestConfig.BASE_URL;

public class BaseClient {

    protected RequestSpecification req() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .accept("application/json");
    }
}
