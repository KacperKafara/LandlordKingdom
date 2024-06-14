package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.AcceptApplicationRequest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationControllerIT extends BaseConfig {
    private String BASE_URL;
    private String ownerToken;
    private String ownerToken2;
    private String tenantToken;

    @BeforeEach
    public void setUp() {
        String AUTH_URL = baseUrl + "/auth";
        BASE_URL = baseUrl + "/locals";
        loadDataSet("src/test/resources/datasets/mokDataForApplicationControllerIT.xml");
        loadDataSet("src/test/resources/datasets/molDataForApplicationControllerIT.xml");

        Verify2FATokenRequest verifyRequestTenant = new Verify2FATokenRequest("testTenant", "30099997");
        Verify2FATokenRequest verifyRequestOwner = new Verify2FATokenRequest("testOwner", "20099995");
        Verify2FATokenRequest verifyRequestOwner2 = new Verify2FATokenRequest("testOwner2", "30099990");

        tenantToken = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyRequestTenant)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .path("token");

        ownerToken = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyRequestOwner)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .path("token");

        ownerToken2 = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyRequestOwner2)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .path("token");
    }

    private String toEndDateString(LocalDate date) {
        return date.getYear() + "-" + String.format("%02d", date.getMonthValue()) + "-" + String.format("%02d", date.getDayOfMonth());
    }

    @Test
    void acceptRequest_endDateFromPast_returnBadRequest() {
        LocalDate date = LocalDate.now().minusDays(1);
        String endDate = toEndDateString(date);

        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(ownerToken)
                .when()
                .body(new AcceptApplicationRequest(endDate))
                .post(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void acceptRequest_endDateFromFutureNotSunday_returnBadRequest() {
        LocalDate date = LocalDate.now().plusDays(1);
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        String endDate = toEndDateString(date);

        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(ownerToken)
                .when()
                .body(new AcceptApplicationRequest(endDate))
                .post(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void acceptRequest_returnCreated() {
        LocalDate date = LocalDate.now().plusDays(1);
        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        String endDate = toEndDateString(date);

        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(ownerToken)
                .when()
                .body(new AcceptApplicationRequest(endDate))
                .post(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("startDate", equalTo(toEndDateString(LocalDate.now(ZoneId.of("UTC")))))
                .body("endDate", equalTo(endDate))
                .body("tenant.login", equalTo("testTenant"))
                .body("local.id", equalTo("a51f1e3e-e0e6-4a7d-80e5-16e245554c77"));
    }

    @Test
    void acceptRequest_userIsNotOwner_returnForbidden() {
        LocalDate date = LocalDate.now().plusDays(1);
        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        String endDate = toEndDateString(date);

        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(tenantToken)
                .when()
                .body(new AcceptApplicationRequest(endDate))
                .post(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void acceptRequest_ownerIsNotLocalOwner_returnNotFound() {
        LocalDate date = LocalDate.now().plusDays(1);
        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        String endDate = toEndDateString(date);

        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(ownerToken2)
                .when()
                .body(new AcceptApplicationRequest(endDate))
                .post(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void acceptRequest_applicationDoesNotExists_returnNotFound() {
        LocalDate date = LocalDate.now().plusDays(1);
        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        String endDate = toEndDateString(date);

        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(ownerToken)
                .when()
                .body(new AcceptApplicationRequest(endDate))
                .post(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232532")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void acceptRequest_invalidEndDateFormat_returnInternalServerError() {
        String endDate = "1990-99-99";

        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(ownerToken)
                .when()
                .body(new AcceptApplicationRequest(endDate))
                .post(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    void acceptRequest_raceCondition_returnOkAndNotFound() throws ExecutionException, InterruptedException, TimeoutException {
        LocalDate date = LocalDate.now().plusDays(1);
        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        String endDate = toEndDateString(date);

        List<Response> response = ConcurrentRequestUtil.runConcurrentRequests(
                given()
                        .contentType(ContentType.JSON)
                        .header("X-Forwarded-For", "203.0.113.195")
                        .auth().oauth2(ownerToken)
                        .when()
                        .body(new AcceptApplicationRequest(endDate)),
                2, Method.POST,BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3");

        int status1 = response.get(0).getStatusCode();
        int status2 = response.get(1).getStatusCode();
        if(status1 == HttpStatus.CREATED.value()) {
            assertEquals(status1, HttpStatus.CREATED.value());
            assertEquals(status2, HttpStatus.NOT_FOUND.value());
        } else {
            assertEquals(status2, HttpStatus.CREATED.value());
            assertEquals(status1, HttpStatus.NOT_FOUND.value());
        }
    }

    @Test
    void rejectRequest_returnOk() {
        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(ownerToken)
                .when()
                .delete(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void rejectRequest_applicationDoesNotExists_returnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(ownerToken)
                .when()
                .delete(BASE_URL + "/applications/6bc94516-c901-431d-8627-bffe4232c0d7")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void rejectRequest_userIsNotOwner_returnForbidden() {
        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(tenantToken)
                .when()
                .delete(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void rejectRequest_ownerIsNotLocalOwner_returnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .auth().oauth2(ownerToken2)
                .when()
                .delete(BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void rejectRequest_raceCondition_returnOkAndNotFound() throws ExecutionException, InterruptedException, TimeoutException {
        LocalDate date = LocalDate.now().plusDays(1);
        while (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        String endDate = toEndDateString(date);

        List<Response> response = ConcurrentRequestUtil.runConcurrentRequests(
                given()
                        .contentType(ContentType.JSON)
                        .header("X-Forwarded-For", "203.0.113.195")
                        .auth().oauth2(ownerToken),
                2, Method.DELETE,BASE_URL + "/applications/6bc94516-c901-4ac1-8627-bffe4232c0d3");

        int status1 = response.get(0).getStatusCode();
        int status2 = response.get(1).getStatusCode();
        if(status1 == HttpStatus.OK.value()) {
            assertEquals(status1, HttpStatus.OK.value());
            assertEquals(status2, HttpStatus.NOT_FOUND.value());
        } else {
            assertEquals(status2, HttpStatus.OK.value());
            assertEquals(status1, HttpStatus.NOT_FOUND.value());
        }
    }
}
