package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.EditLocalAddressRequest;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class LocalControllerIT extends BaseConfig {
    private static String LOCALS_URL;
    private String adminToken;
    private String userToken;

    @BeforeEach
    public void setUp() {
        String AUTH_URL = baseUrl + "/auth";
        LOCALS_URL = baseUrl + "/locals";
        loadDataSet("src/test/resources/datasets/userDataForMeOwnerIT.xml");
        loadDataSet("src/test/resources/datasets/molDataForLocalControllerIT.xml");

        Verify2FATokenRequest verifyAdminRequest = new Verify2FATokenRequest("mol8admin", "20099997");
        Verify2FATokenRequest verifyUserRequest = new Verify2FATokenRequest("mol8owner", "20099995");

        Response responseAdmin = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyAdminRequest)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();
        adminToken = responseAdmin.path("token");

        Response userResponse = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.22")
                .when()
                .body(verifyUserRequest)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();
        userToken = userResponse.path("token");
    }

    @Test
    public void archiveLocal_localIsInCorrectState_localArchived() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5/archive")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("state", equalTo("ARCHIVED"));
    }

    @Test
    public void archiveLocal_localIsInIncorrectState_localNotArchived() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac6/archive")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CONFLICT.value());

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac6")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("state", not(equalTo("ARCHIVED")));
    }

    @Test
    public void archiveLocal_localDoesNotExist_returnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac7/archive")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void archiveLocal_userIsNotAdmin_localNotArchived() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + userToken)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5/archive")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void archiveLocal_raceCondition_returnOKAndConflict() throws ExecutionException, InterruptedException, TimeoutException {
        List<Response> response = ConcurrentRequestUtil.runConcurrentRequests(
                given()
                        .contentType(ContentType.JSON)
                        .auth().oauth2(adminToken),
                2, Method.PATCH, LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5/archive");
        int status1 = response.get(0).getStatusCode();
        int status2 = response.get(1).getStatusCode();
        log.info("Status1: %d Status2: %d".formatted(status1, status2));
        if(status1 == HttpStatus.OK.value()) {
            assertEquals(status1, HttpStatus.OK.value());
            assertEquals(status2, HttpStatus.CONFLICT.value());
        } else {
            assertEquals(status2, HttpStatus.OK.value());
            assertEquals(status1, HttpStatus.CONFLICT.value());
        }
    }

    @Test
    public void changeLocalAddress_userIsNotAdmin_returnForbidden() {
        EditLocalAddressRequest editLocalAddressRequest = new EditLocalAddressRequest("Polska", "Lodz", "Piotrkowska", "Lodz", "90-000");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + userToken)
                .body(editLocalAddressRequest)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5/address")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void changeLocalAddress_dataNotValid_returnBadRequest() {
        EditLocalAddressRequest editLocalAddressRequest = new EditLocalAddressRequest("Polska", "Lodz", "Piotrkowska", "Lodz", "90000");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(editLocalAddressRequest)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5/address")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void changeLocalAddress_localNotExists_returnNotFound() {
        EditLocalAddressRequest editLocalAddressRequest = new EditLocalAddressRequest("Polska", "Lodz", "Piotrkowska", "Lodz", "90-000");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(editLocalAddressRequest)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ceabcd/address")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void changeLocalAddress_localExists_returnOk() {
        EditLocalAddressRequest editLocalAddressRequest = new EditLocalAddressRequest("Polska", "Lodz", "Piotrkowska", "Lodz", "90-000");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(editLocalAddressRequest)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5/address")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("address.country", equalTo("Polska"))
                .body("address.city", equalTo("Lodz"))
                .body("address.street", equalTo("Piotrkowska"))
                .body("address.number", equalTo("Lodz"))
                .body("address.zipCode", equalTo("90-000"));
    }

    @Test
    public void changeLocalAddress_addressExistsLocalNotArchived_returnConflict() {
        EditLocalAddressRequest editLocalAddressRequest = new EditLocalAddressRequest("inactiveLocalCountry", "inactiveLocalCity", "inactiveLocalStreet", "1", "12-312");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(editLocalAddressRequest)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5/address")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void changeLocalAddress_addressExistsLocalArchived_returnOk() {
        EditLocalAddressRequest editLocalAddressRequest = new EditLocalAddressRequest("archivedLocalCountry", "archivedLocalCity", "archivedLocalStreet", "1", "12-123");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(editLocalAddressRequest)
                .when()
                .patch(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac6/address")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get(LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac6")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("address.country", equalTo("archivedLocalCountry"))
                .body("address.city", equalTo("archivedLocalCity"))
                .body("address.street", equalTo("archivedLocalStreet"))
                .body("address.number", equalTo("1"))
                .body("address.zipCode", equalTo("12-123"));
    }

    @Test
    public void changeLocalAddress_raceCondition_returnOKAndConflict() throws ExecutionException, InterruptedException, TimeoutException {
        EditLocalAddressRequest editLocalAddressRequest = new EditLocalAddressRequest("Polska", "Lodz", "Piotrkowska", "Lodz", "90-000");

        List<Response> response = ConcurrentRequestUtil.runConcurrentRequests(
                given()
                        .contentType(ContentType.JSON)
                        .auth().oauth2(adminToken)
                        .body(editLocalAddressRequest),
                2, Method.PATCH, LOCALS_URL + "/64d715a3-0dd5-4520-9716-965db9ce1ac5/address");
        int status1 = response.get(0).getStatusCode();
        int status2 = response.get(1).getStatusCode();
        log.info("Status1: %d Status2: %d".formatted(status1, status2));
        if(status1 == HttpStatus.OK.value()) {
            assertEquals(status1, HttpStatus.OK.value());
            assertEquals(status2, HttpStatus.CONFLICT.value());
        } else {
            assertEquals(status2, HttpStatus.OK.value());
            assertEquals(status1, HttpStatus.CONFLICT.value());
        }
    }
}
