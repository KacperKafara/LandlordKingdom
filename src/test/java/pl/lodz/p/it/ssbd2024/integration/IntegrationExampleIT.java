package pl.lodz.p.it.ssbd2024.integration;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class IntegrationExampleIT extends BaseConfig {

    @Test
    public void test() {
        given()
                .when()
                .get(baseUrl)
                .then()
                .statusCode(200);
    }

}
