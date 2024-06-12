package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.VariableFeeRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;

@Slf4j
class MeTenantControllerIT extends BaseConfig {
    private static String ME_URL;
    private String tenantToken;
    private String tenant2Token;
    private String ownerToken;

    @BeforeEach
    public void setUp() {
        ME_URL = baseUrl + "/me";
        String authUrl = baseUrl + "/auth";
        loadDataSet("src/test/resources/datasets/userDataForMeTenantControllerIT.xml");
        loadDataSet("src/test/resources/datasets/molDataForMeTenantControllerIT.xml");

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("tenantMeTenant", "30099997");
        Verify2FATokenRequest verifyRequest2 = new Verify2FATokenRequest("ownerMeTenant", "20099995");
        Verify2FATokenRequest verifyRequest3 = new Verify2FATokenRequest("tenant2MeTenant", "40099997");

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyRequest)
                .post(authUrl + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        tenantToken = response.path("token");
        ownerToken = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyRequest2)
                .post(authUrl + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .path("token");

        tenant2Token = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyRequest3)
                .post(authUrl + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .path("token");
    }

    @Test
    @DisplayName("Should request role")
    void requestRole_tenantRequestsRole() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(tenantToken)
                .when()
                .post(ME_URL + "/role-request")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Role request already exists for tenant")
    void requestRole_tenantRequestsRole_requestAlreadyExists() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(tenant2Token)
                .when()
                .post(ME_URL + "/role-request")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(tenant2Token)
                .when()
                .post(ME_URL + "/role-request")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void requestRole_tenantRequestsRole_tenantIsAlreadyOwner() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(ownerToken)
                .when()
                .post(ME_URL + "/role-request")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void enterVariableFee_tenantEntersVariableFee() {
        VariableFeeRequest variableFeeRequest = new VariableFeeRequest(new BigDecimal("1000.00"));
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(tenantToken)
                .when()
                .body(variableFeeRequest)
                .post(ME_URL + "/rents/31658859-f2dc-425a-bb98-8f444960bb35/variable-fee")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void enterVariableFee_tenantEntersVariableFee_rentNotFound() {
        VariableFeeRequest variableFeeRequest = new VariableFeeRequest(new BigDecimal("1000.00"));
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(tenantToken)
                .when()
                .body(variableFeeRequest)
                .post(ME_URL + "/rents/31658859-f2dc-425a-bb98-8f444960bb36/variable-fee")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void enterVariableFee_tenantEntersVariableFee_tenantIsNotAssignedToRent() {
        VariableFeeRequest variableFeeRequest = new VariableFeeRequest(new BigDecimal("1000.00"));
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(tenant2Token)
                .when()
                .body(variableFeeRequest)
                .post(ME_URL + "/rents/31658859-f2dc-425a-bb98-8f444960bb35/variable-fee")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void enterVariableFee_tenantEntersVariableFee_variableFeeAlreadyCreatedForThisWeek() {
        VariableFeeRequest variableFeeRequest = new VariableFeeRequest(new BigDecimal("1000.00"));
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(tenantToken)
                .when()
                .body(variableFeeRequest)
                .post(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/variable-fee")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(tenantToken)
                .when()
                .body(variableFeeRequest)
                .post(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/variable-fee")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void enterVariableFee_raceCondition() {
        try {
            RequestSpecification specification = given()
                    .contentType(ContentType.JSON)
                    .auth().oauth2(tenantToken)
                    .body(new VariableFeeRequest(new BigDecimal("1000.00")));

            List<Response> responses = ConcurrentRequestUtil.runConcurrentRequests(
                    specification,
                    2,
                    Method.POST, ME_URL + "/rents/2fde7510-8d4c-49cd-aa1d-5d848b510e23/variable-fee"
            );
            int status1 = responses.get(0).getStatusCode();
            int status2 = responses.get(1).getStatusCode();
            if (status1 == 200) {
                Assertions.assertEquals(200, status1);
                Assertions.assertEquals(400, status2);
            } else {
                Assertions.assertEquals(400, status1);
                Assertions.assertEquals(200, status2);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).forEach(log::error);
        }
    }
}
