package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.RefreshTokenRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserCreateRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerIT extends BaseConfig {
    private static  String AUTH_URL = baseUrl + "/auth";

    @BeforeEach
    public void loadDataSet() {
        loadDataSet("src/test/resources/datasets/usersForAuthIT.xml");
        AUTH_URL = baseUrl + "/auth";
    }

    @Test
    @DisplayName("Authenticate_CredentialsCorrect_ReturnTOk_Test")
    public void Authenticate_CredentialsCorrect_ReturnOk_Test() {

        AuthenticationRequest request = new AuthenticationRequest("userVerified", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
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
                    .header("X-Forwarded-For", "203.0.113.195")
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
    @DisplayName("RefreshToken_TokenCorrect_ReturnNewToken_Test")
    public void RefreshToken_TokenCorrect_ReturnNewToken_Test() {
        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("userVerified2", "20099984");

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

        String refreshToken = response.path("refreshToken");

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
    public void Verify2fa_OtpIncorrect_ReturnBadRequest_Test() {

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
                .header("X-Forwarded-For", "203.0.113.195")
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

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("userNotFound", "12345678");

        given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
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

        String expiredToken = "eyJraWQiOiIxYTEzODRlMC0wMWMzLTQ4NzAtYWMzZC1kN2UwZGJlODg4ODEiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0ZWFtLTIucHJvai1zdW0uaXQucC5sb2R6LnBsIiwic3ViIjoiMDU4NTQxMzItOGI3Yy00NDBlLTllZjItOGZlNDZhNzk2MmRjIiwiZXhwIjoxNzE1ODA3NjM1LCJpYXQiOjE3MTU4MDc2MzQsImF1dGhvcml0aWVzIjpbIlRFTkFOVCJdfQ.a3LRBzOUjOkv5YHvj4KqGZg2SG_r22x2RWkMa7pOgN-sN4xVh6Xj9yEgNVQQ9B8NDBRl0tz5DqWlHUJnrEJTCrdFvt9hMIhyfWOYiRwIOBss9bI7wOg1kdJEjXfJMck2umwAtEDMg7mRD5TaulosCz-3HRKhU4q7R69NtRttPAMqHg3a7eEq7fEuLy7XQJy5Ikz5V3NEy9Qyn5Gndr4fMdPJRryAV1VOZt_IfOV0ItBOy3UOTDcKosH3fv6QvcOY6JVARNG2lAANH_q3BtbJKw3IACZ8f0b0xLoVjGNQRPacTKyvVxn4m_sxW-37kaHK6BNSv8vX328TeQJ5qGdHoQ";

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

        String expiredRefreshToken = "eyJraWQiOiI0ZGViMjA1YS1hMTEyLTRlZGUtYjNlNS04OGZmMTRkMThmYTkiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0ZWFtLTIucHJvai1zdW0uaXQucC5sb2R6LnBsIiwic3ViIjoiMDU4NTQxMzItOGI3Yy00NDBlLTllZjItOGZlNDZhNzk2MmRjIiwiZXhwIjoxNzE1ODA3NjM1LCJpYXQiOjE3MTU4MDc2MzR9.k9fmo5KI2RXoF_U78-NnhC7009tUkE7TrP6T8xAUlGG2pRGIMdKV80qTMHYnelE-moMC5nlrDqO8ryeDLc7jgWMQp-mJCWGlFgKcNGUzvGU0q70WOXHpQyJSpkjmFm8EphidOchX6C5MvhLqmVY5AqXWlpIJowaEpHBVxo82NlaiI2Y8lunTrqlfsVY3UcPyHEV-ko7VG6JVia3d2peK4BWjzQJ00tcyGs9VF4Zw5xxa0yNyNTgeM_mveGBnJ1UKz3XJZAKQb6InXGdNuqIL0IVTZIRf2NRKa2rO6z2LZtYK-ZzGAJKgaR0AOSHaOmJU9qS0o8u2xbAfWS5Q1hYxLw";

        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(expiredRefreshToken);

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(refreshTokenRequest)
                .post(AUTH_URL + "/refresh")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("RegisterUser_CorrectData_ReturnCreated_Test")
    public void RegisterUser_CorrectData_ReturnCreated_Test() {
        UserCreateRequest request = new UserCreateRequest("login", "email@test.com", "FirstName", "LastName", "password", "en");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("RegisterUser_ExistingLogin_ReturnUnprocessableEntity_Test")
    public void RegisterUser_ExistingLogin_ReturnUnprocessableEntity_Test() {
        UserCreateRequest request = new UserCreateRequest("userVerified", "email@test.com", "FirstName", "LastName", "password", "en");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    @DisplayName("RegisterUser_ExistingEmail_ReturnUnprocessableEntity_Test")
    public void RegisterUser_ExistingEmail_ReturnUnprocessableEntity_Test() {
        UserCreateRequest request = new UserCreateRequest("login", "userVerified@test.com", "FirstName", "LastName", "password", "en");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    @DisplayName("RegisterUser_PasswordIncorrect_ReturnBadRequest_Test")
    public void RegisterUser_PasswordIncorrect_ReturnBadRequest_Test() {
        UserCreateRequest request = new UserCreateRequest("login", "email@test.com", "FirstName", "LastName", "pass", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        request = new UserCreateRequest("login", "email@test.com", "FirstName", "LastName", "", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());


        request = new UserCreateRequest("login", "email@test.com", "FirstName", "LastName", "yVBZAc5fy5hTzJB4QjhiIzvTtT0NDfYqalqKCGvONpr6GIZ6elC", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("RegisterUser_LanguageIncorrect_ReturnBadRequest_Test")
    public void RegisterUser_LanguageIncorrect_ReturnBadRequest_Test() {
        UserCreateRequest request = new UserCreateRequest("login", "email@test.com", "FirstName", "LastName", "password", "");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        request = new UserCreateRequest("login", "email@test.com", "FirstName", "LastName", "password", "abc");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("RegisterUser_EmailIncorrect_ReturnBadRequest_Test")
    public void RegisterUser_EmailIncorrect_ReturnBadRequest_Test() {
        UserCreateRequest request = new UserCreateRequest("login", "", "FirstName", "LastName", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        request = new UserCreateRequest("login", "em", "FirstName", "LastName", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        request = new UserCreateRequest("login", "email", "FirstName", "LastName", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signup")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
