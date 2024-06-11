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
import pl.lodz.p.it.ssbd2024.mol.dto.SetEndDateRequest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class MeOwnerControllerIT extends BaseConfig{

    private static String ME_URL = baseUrl;
    private static String LOCALS_URL = baseUrl + "/locals";
    private String ownerToken;
    private String adminToken;
    @BeforeEach
    public void setUp() {
        ME_URL = baseUrl + "/me";
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
                Assertions.assertEquals(status1, 200);
                Assertions.assertEquals(status2, 404);
            } else {
                Assertions.assertEquals(status1, 404);
                Assertions.assertEquals(status2, 200);
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

}
