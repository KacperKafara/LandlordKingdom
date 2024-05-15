package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.RefreshTokenRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerIT extends BaseConfig {
    private static final String AUTH_URL = baseUrl + "/auth";

    @BeforeEach
    public void loadDataSet() {
        loadDataSet("src/test/resources/datasets/usersForAuthIT.xml");
    }

    @Test
    @DisplayName("Authenticate_CredentialsCorrect_ReturnTOk_Test")
    public void Authenticate_CredentialsCorrect_ReturnOk_Test() {

        AuthenticationRequest request = new AuthenticationRequest("userVerified", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Authenticate_CredentialsIncorrect_ReturnUnauthorized_Test")
    public void Authenticate_CredentialsIncorrect_ReturnUnauthorized_Test() {

        AuthenticationRequest request = new AuthenticationRequest("userVerified", "wrongPassword", "en");


        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("Authenticate_UserNotFound_ReturnNotFund_Test")
    public void Authenticate_UserNotFound_ReturnNotFund_Test() {

        AuthenticationRequest request = new AuthenticationRequest("userNotFound", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Authenticate_UserNotVerified_ReturnForbidden_Test")
    public void Authenticate_UserNotVerified_ReturnForbidden_Test() {

        AuthenticationRequest request = new AuthenticationRequest("userNotVerified", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Authenticate_UserBlocked_ReturnForbidden_Test")
    public void Authenticate_UserBlocked_ReturnForbidden_Test() {

        AuthenticationRequest request = new AuthenticationRequest("userBlocked", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Authenticate_AfterUserFailsToLoginThreeTimes_ReturnForbidden_Test")
    public void Authenticate_AfterUserFailsToLoginThreeTimes_ReturnForbidden_Test() throws InterruptedException {

        AuthenticationRequest invalidCredentialsRequest = new AuthenticationRequest("userVerified", "wrongPassword", "en");
        AuthenticationRequest validCredentialsRequest = new AuthenticationRequest("userVerified", "password", "en");

        for (int i = 0; i < 3; i++) {
            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .body(invalidCredentialsRequest)
                    .post(AUTH_URL + "/signin-2fa")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(validCredentialsRequest)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Authenticate_UserWithThreeLoginAttemptsAfterTwoDays_ReturnOk_Test")
    public void Authenticate_UserWithThreeLoginAttemptsAfterTwoDays_ReturnOk_Test() {

        AuthenticationRequest request = new AuthenticationRequest("userWithAttempts2DaysAgo", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Verify2fa_OtpCorrect_ReturnToken_Test")
    public void Verify2fa_OtpCorrect_ReturnToken_Test() throws Exception {

        AuthenticationRequest signinRequest = new AuthenticationRequest("userVerified", "password", "en");


        given()
                .contentType(ContentType.JSON)
                .when()
                .body(signinRequest)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        String otp = EmailReader.readOtpFromEmail("userVerified@test.com");

        assertNotNull(otp);

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("userVerified", otp);

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

        String token = response.path("token");
        String refreshToken = response.path("refreshToken");

        assertNotNull(token);
        assertNotNull(refreshToken);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .get(baseUrl + "/me")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("RefreshToken_TokenCorrect_ReturnNewToken_Test")
    public void RefreshToken_TokenCorrect_ReturnNewToken_Test() throws Exception {

        AuthenticationRequest signinRequest = new AuthenticationRequest("userVerified", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(signinRequest)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        String otp = EmailReader.readOtpFromEmail("userVerified@test.com");

        assertNotNull(otp);

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("userVerified", otp);

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

        String token = response.path("token");
        String refreshToken = response.path("refreshToken");

        System.out.println("Token: " + token);
        System.out.println("RefreshToken: " + refreshToken);

        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);

        String newToken = given()
                .contentType(ContentType.JSON)
                .when()
                .body(refreshTokenRequest)
                .post(AUTH_URL + "/refresh")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("token");

        assertNotNull(newToken);

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(newToken)
                .when()
                .get(baseUrl + "/me")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }


    @Test
    @DisplayName("Verify2fa_OtpIncorrect_ReturnBadRequest_Test")
    public void Verify2fa_OtpIncorrect_ReturnBadRequest_Test() throws Exception {

        AuthenticationRequest signinRequest = new AuthenticationRequest("userVerified", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(signinRequest)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("userVerified", "wrongOtp");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(verifyRequest)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Verify2fa_IncorrectUser_ReturnNotFound_Test")
    public void Verify2fa_IncorrectUser_ReturnNotFound_Test() {

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("userNotFound", "otp");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(verifyRequest)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Authorization_TokenExpired_ReturnUnauthorized_Test")
    public void Authorization_TokenExpired_ReturnUnauthorized_Test() {

        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ0ZWFtLTIucHJvai1zdW0uaXQucC5sb2R6LnBsIiwic3ViIjoiMmQxYzkxZTgtY2MwYy00ZjMzLWFlNmUtMjBhOTk0OGE2ZjJkIiwiZXhwIjoxNzE1ODAwOTA2LCJpYXQiOjE3MTU3OTczMDYsImF1dGhvcml0aWVzIjpbIlRFTkFOVCJdfQ.rPX6doHqC0HdRGdSpGe5lT5Du5Y8Q1TfgKUSrVC0kS2B9tF52xjLXvFiQuMDMEjVYAtwHSZMrN8SjpqH2Echzg";

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(expiredToken)
                .when()
                .post(baseUrl + "/me")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("RefreshToken_ExpiredToken_ReturnBadRequest_Test")
    public void RefreshToken_ExpiredToken_ReturnBadRequest_Test() {

        String expiredRefreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ0ZWFtLTIucHJvai1zdW0uaXQucC5sb2R6LnBsIiwic3ViIjoiMmQxYzkxZTgtY2MwYy00ZjMzLWFlNmUtMjBhOTk0OGE2ZjJkIiwiZXhwIjoxNzE1ODgzNzA2LCJpYXQiOjE3MTU3OTczMDZ9.WAHNJqtDUUJ5XM1lavoEOJSfk3ZZGUpcvJ9GiDNvNLSjy0N0_UosYmFJfHAiPsi6-ZKihQZoHMIw1FSqE_SyOw";

        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(expiredRefreshToken);

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(refreshTokenRequest)
                .post(AUTH_URL + "/refresh")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
