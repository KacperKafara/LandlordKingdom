package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.EditLocalRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.SetEndDateRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class MeOwnerControllerIT extends BaseConfig{

    private static String ME_URL = baseUrl;
    private static String LOCALS_URL;
    private String ownerToken;
    private String adminToken;
    @BeforeEach
    public void setUp() {
        ME_URL = baseUrl + "/me";
        LOCALS_URL = baseUrl + "/locals";
        String AUTH_URL = baseUrl + "/auth";
        loadDataSet("src/test/resources/datasets/userDataForMeOwnerIT.xml");
        loadDataSet("src/test/resources/datasets/molDataForMeOwnerIT.xml");

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("mol8owner", "20099995");
        Verify2FATokenRequest verifyAdminRequest = new Verify2FATokenRequest("mol8admin", "20099997");

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

        ownerToken = response.path("token");

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

    }

    @Test
    @DisplayName("leaveLocal_ownerLeavesLocal_localIsWithoutOwner")
    public void leaveLocal_ownerLeavesLocal_localIsWithoutOwner() {

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .patch(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0/leave" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true));

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(LOCALS_URL + "/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("state", equalTo("WITHOUT_OWNER"))
                .body("owner", equalTo(null));
    }

    @Test
    public void leveLocal_userNotAuthorized_returnUnauthorized() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .patch(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0/leave" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void leaveLocal_localDoesNotExist_returnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .patch(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4f1/leave" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }



    @Test
    public void leaveLocal_localIsNotInactive_returnBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .patch(ME_URL + "/locals/a51f1e3e-e0e6-4a7d-80e5-16e245554c77/leave" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void leaveLocal_raceCondition_returnOkAndNotFound(){
        try {
            List<Response> response = ConcurrentRequestUtil.runConcurrentRequests(
                    given()
                            .contentType(ContentType.JSON)
                            .auth().oauth2(ownerToken)
                           , 2, Method.PATCH,  ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0/leave");
            int status1 = response.get(0).getStatusCode();
            int status2 = response.get(1).getStatusCode();
            log.info("Status1: %d Status2: %d".formatted(status1, status2));
            if (status1 == 200){
                assertEquals(status1, 200);
                assertEquals(status2, 404);
            } else {
                assertEquals(status1, 404);
                assertEquals(status2, 200);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).forEach(log::error);
        }
    }

    @Test
    public void changeRentEndDate_endDateChanged_returnOk(){
        String newEndDate = "2024-07-21";
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .body(setEndDateRequest)
                .patch(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/end-date")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .get(ME_URL + "/owner/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("endDate", equalTo(newEndDate));

    }

    @Test
    public void changeRentEndDate_userNotAuthorized_returnUnauthorized(){
        String newEndDate = "2024-07-21";
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .body(setEndDateRequest)
                .patch(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/end-date")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void changeRentEndDate_rentDoesNotExist_returnNotFound(){
        String newEndDate = "2024-07-21";
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .body(setEndDateRequest)
                .patch(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa36/end-date")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void changeRentEndDate_newEndDateIsBeforeNow_returnBadRequest(){
        LocalDate now = LocalDate.now();
        String newEndDate = now.minusDays( now.getDayOfWeek().getValue() % 7 ).toString();
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .body(setEndDateRequest)
                .patch(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/end-date")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void changeRentEndDate_newEndDateIsBeforeStartDate_returnBadRequest(){
        String newEndDate = "2024-01-14";
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .body(setEndDateRequest)
                .patch(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/end-date")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void changeRentEndDate_endDateIsNotInCorrectFormat_returnBadRequest(){
        String newEndDate = "07/07/2024";
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .body(setEndDateRequest)
                .patch(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/end-date")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void changeRentEndDate_endDateTheSame_returnBadRequest(){
        String newEndDate = "2024-09-02";
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .body(setEndDateRequest)
                .patch(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/end-date")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void changeRentEndDate_raceCondition_returnOkAndBadRequest(){
        String newEndDate = "2024-07-21";
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);
        try {
            List<Response> response = ConcurrentRequestUtil.runConcurrentRequests(
                    given()
                            .contentType(ContentType.JSON)
                            .auth().oauth2(ownerToken)
                            .body(setEndDateRequest)
                    , 2, Method.PATCH,  ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/end-date");
            int status1 = response.get(0).getStatusCode();
            int status2 = response.get(1).getStatusCode();
            log.info("Status1: %d Status2: %d".formatted(status1, status2));
            if (status1 == 200){
                assertEquals(status1, 200);
                assertEquals(status2, 400);
            } else {
                assertEquals(status1, 400);
                assertEquals(status2, 200);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).forEach(log::error);
        }
    }

    @Test
    public void changeRentEndDate_rentAlreadyEnded_returnBadRequest(){
        String newEndDate = "2024-07-21";
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .body(setEndDateRequest)
                .patch(ME_URL + "/rents/31658859-f2dc-425a-bb98-8f444960bb35/end-date")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void changeRentEndDate_newEndDateIsNotSunday_returnBadRequest(){
        String newEndDate = "2024-07-22";
        SetEndDateRequest setEndDateRequest = new SetEndDateRequest(newEndDate);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .body(setEndDateRequest)
                .patch(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/end-date")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void editLocal_userIsNotOwner_returnForbidden() {
        String etag = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .get(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0")
                .then()
                .extract()
                .header("ETag");

        EditLocalRequest editLocalRequest = new EditLocalRequest(UUID.fromString("3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0"), "newDescription", "nbl", "ACTIVE");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header("If-Match", etag.substring(1, etag.length()-1))
                .body(editLocalRequest)
                .when()
                .put(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void editLocal_localDoesNotExist_returnNotFound() {
        EditLocalRequest editLocalRequest = new EditLocalRequest(UUID.fromString("3db8f4a7-a268-41a8-84f8-5e1a1dc4b4f1"), "newDescription", "nbl", "ACTIVE");

        String etag = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .get(ME_URL + "/locals/a51f1e3e-e0e6-4a7d-80e5-16e245554c77")
                .then()
                .extract()
                .header("ETag");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .header("If-Match", etag.substring(1, etag.length()-1))
                .body(editLocalRequest)
                .when()
                .put(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4f1" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void editLocal_etagIsNotCorrect_returnPreconditionFailed() {
        EditLocalRequest editLocalRequest = new EditLocalRequest(UUID.fromString("3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0"), "newDescription", "nbl", "ACTIVE");

        String etag = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .get(ME_URL + "/locals/a51f1e3e-e0e6-4a7d-80e5-16e245554c77")
                .then()
                .extract()
                .header("ETag");


        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .body(editLocalRequest)
                .header("If-Match", etag.substring(1, etag.length()-1))
                .when()
                .put(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.PRECONDITION_FAILED.value());
    }

    @Test
    public void editLocal_raceCondition_returnOkAndPreconditionFailedOrConflict() throws ExecutionException, InterruptedException, TimeoutException {
        EditLocalRequest editLocalRequest = new EditLocalRequest(UUID.fromString("3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0"), "newDescription", "nbl", "ACTIVE");
        String etag = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .get(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0")
                .then()
                .extract()
                .header("ETag");


        List<Response> response = ConcurrentRequestUtil.runConcurrentRequests(
                given()
                        .contentType(ContentType.JSON)
                        .auth().oauth2(ownerToken)
                        .body(editLocalRequest)
                        .header("If-Match", etag.substring(1, etag.length()-1))
                , 2, Method.PUT,  ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0");
        int status1 = response.get(0).getStatusCode();
        int status2 = response.get(1).getStatusCode();
        log.info("Status1: %d Status2: %d".formatted(status1, status2));
        if (status1 == 200){
            assertEquals(status1, 200);
            assertTrue(status2 == 412 || status2 == 409);
        } else {
            assertTrue(status1 == 412 || status1 == 409);
            assertEquals(status2, 200);
        }
    }

    @Test
    public void editLocal_dataNotValid_returnBadRequest() {
        EditLocalRequest editLocalRequest = new EditLocalRequest(UUID.fromString("3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0"), "afds", "nbl", "ARCHIVED");

        String etag = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .get(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0")
                .then()
                .extract()
                .header("ETag");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .body(editLocalRequest)
                .header("If-Match", etag.substring(1, etag.length()-1))
                .when()
                .put(ME_URL + "/locals/3db8f4a7-a268-41a8-84f8-5e1a1dc4b4d0" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
