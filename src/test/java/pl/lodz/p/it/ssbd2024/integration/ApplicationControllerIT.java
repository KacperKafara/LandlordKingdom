package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.AcceptApplicationRequest;

import static io.restassured.RestAssured.given;

public class ApplicationControllerIT extends BaseConfig {
    private String BASE_URL;
    private String ownerToken;
    private String tenantToken;

    @BeforeEach
    public void setUp() {
        String AUTH_URL = baseUrl + "/auth";
        BASE_URL = baseUrl + "/locals";
        loadDataSet("src/test/resources/datasets/userDataForMeOwnerIT.xml");
        loadDataSet("src/test/resources/datasets/userDataForMeTenantControllerIT.xml");
        loadDataSet("src/test/resources/datasets/molDataForApplicationControllerIT.xml");

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("tenantMeTenant", "30099997");
        Verify2FATokenRequest verifyRequest2 = new Verify2FATokenRequest("ownerMeTenant", "20099995");

        tenantToken = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyRequest)
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
                .body(verifyRequest2)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .path("token");
    }

    @Test
    @DisplayName("Accept request with end date from past")
    void acceptRequest_endDateFromPast_WrongEndDate() {

    }
}
