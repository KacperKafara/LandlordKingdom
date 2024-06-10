package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("mol8user", "20099995");
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
    @DisplayName("leaveLocalByOwner_ownerLeavesLocal_localIsWithoutOwner")
    public void leaveLocalByOwner_ownerLeavesLocal_localIsWithoutOwner() {

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
                .body("state", equalTo("WITHOUT_OWNER"));
    }
}
