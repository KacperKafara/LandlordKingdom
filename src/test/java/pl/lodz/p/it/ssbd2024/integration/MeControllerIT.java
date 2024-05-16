package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticatedChangePasswordRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MeControllerIT extends BaseConfig {
    private static String ME_URL = baseUrl;
    private static String AUTH_URL = baseUrl;

    private String adminToken;

    @BeforeEach
    public void setUp() throws MessagingException, IOException {
        baseUrl = "http://" + tomcat.getHost() + ":" + tomcat.getMappedPort(8080) + "/ssbd02";
        ME_URL = baseUrl + "/me";
        AUTH_URL = baseUrl + "/auth";
        loadDataSet("src/test/resources/datasets/userForMeIT.xml");

        AuthenticationRequest signinRequest = new AuthenticationRequest("adminUser", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(signinRequest)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        String otp = EmailReader.readOtpFromEmail("adminUser@test.com");

        assertNotNull(otp);

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("adminUser", otp);

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(verifyRequest)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        adminToken = response.path("token");
    }


    @Test
    @DisplayName("getUserData_userExists_returnOK")
    public void getUserData_userExists_returnOK() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("login", equalTo("adminUser"));
    }

    @Test
    @DisplayName("updateUserData_IfMatchCorrect_returnOK")
    public void updateUserData_IfMatchCorrect_returnOK() {
        Response response = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("en"))
                .extract()
                .response();

        String etag = response.getHeader(HttpHeaders.ETAG);
        etag = etag.substring(1, etag.length() - 1);
        UpdateUserDataRequest updateRequest = new UpdateUserDataRequest("admin", "user", "pl");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header(HttpHeaders.IF_MATCH, etag)
                .when()
                .body(updateRequest)
                .put(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("pl"));
    }

    @Test
    @DisplayName("updateUserData_IfMatchIncorrect_returnPreconditionFailed")
    public void updateUserData_IfMatchIncorrect_returnPreconditionFailed() {
        Response response = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("en"))
                .extract()
                .response();

        String etag = response.getHeader(HttpHeaders.ETAG);
        etag = etag.substring(1, etag.length() - 1);
        UpdateUserDataRequest updateRequest = new UpdateUserDataRequest("admin", "user", "pl");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header(HttpHeaders.IF_MATCH, etag)
                .when()
                .body(updateRequest)
                .put(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("pl"));

        UpdateUserDataRequest updateRequest2 = new UpdateUserDataRequest("admin", "user", "en");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header(HttpHeaders.IF_MATCH, etag)
                .when()
                .body(updateRequest2)
                .put(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.PRECONDITION_FAILED.value());
    }

    @Test
    @DisplayName("changePassword_oldPasswordValid_returnOK")
    public void changePassword_userExists_returnOK() {
        AuthenticatedChangePasswordRequest changePasswordRequest = new AuthenticatedChangePasswordRequest("password", "newPassword");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .body(changePasswordRequest)
                .post(ME_URL + "/change-password")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("changePasswordWithToken_oldPasswordNotValid_returnPRECONDITIONFAILED")
    public void changePasswordWithToken_oldPasswordNotValid_returnPRECONDITIONFAILED() {
        AuthenticatedChangePasswordRequest changePasswordRequest = new AuthenticatedChangePasswordRequest("oldPassword", "newPassword");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .body(changePasswordRequest)
                .post(ME_URL + "/change-password")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
