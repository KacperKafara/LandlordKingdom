package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class AcceptRoleControllerIT extends BaseConfig {
    private String adminToken;
    private String ROLE_URL;
    private String USERS_URL;
    private String userToken;

    @BeforeEach
    public void setUp() {
        String AUTH_URL = baseUrl + "/auth";
        ROLE_URL = baseUrl + "/roles";
        USERS_URL = baseUrl + "/users";
        loadDataSet("src/test/resources/datasets/usersForRoleControllerIT.xml");

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("testAdmin", "20099984");
        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyRequest)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        adminToken = response.path("token");

        Verify2FATokenRequest verifyRequestOwner = new Verify2FATokenRequest("testOwner", "20099985");
        Response userResponse = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.22")
                .when()
                .body(verifyRequestOwner)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();
        userToken = userResponse.path("token");
    }

    @Test
    public void acceptRoleRequest_roleRequestExists_returnOk() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .post(ROLE_URL + "/b24db498-3048-4282-b33c-241b7cab81de")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .post(ROLE_URL + "/b24db498-3048-4282-b33c-241b7cab81de")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/093cdb69-4a4f-4932-a575-ffe76f40efce")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("roles", hasItem("OWNER"));
    }

    @Test
    public void acceptRoleRequest_roleRequestDoesNotExist_returnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .post(ROLE_URL + "/51fce47e-f621-4f1f-91eb-a2f0dd8703f6")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void acceptRoleRequest_userIsNotAdmin_returnForbidden() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(userToken)
                .when()
                .post(ROLE_URL + "/b24db498-3048-4282-b33c-241b7cab81de")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void acceptRoleRequest_raceCondition_returnOkAndNotFound() throws ExecutionException, InterruptedException, TimeoutException {
        List<Response> response = ConcurrentRequestUtil.runConcurrentRequests(
                given()
                        .contentType(ContentType.JSON)
                        .auth().oauth2(adminToken),
                2, Method.POST, ROLE_URL + "/b24db498-3048-4282-b33c-241b7cab81df");
        int status1 = response.get(0).getStatusCode();
        int status2 = response.get(1).getStatusCode();
        log.info("Status1: %d Status2: %d".formatted(status1, status2));
        if(status1 == HttpStatus.OK.value()) {
            assertEquals(status1, HttpStatus.OK.value());
            assertEquals(status2, HttpStatus.NOT_FOUND.value());
        } else {
            assertEquals(status2, HttpStatus.OK.value());
            assertEquals(status1, HttpStatus.NOT_FOUND.value());
        }

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/b0194a97-9665-46e4-92bd-acf7538d4fa2")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("roles", hasItem("OWNER"));
    }
}
