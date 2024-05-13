package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationRequest;

import static io.restassured.RestAssured.given;

public class AuthControllerIT extends BaseConfig {
    private static final String AUTH_URL = baseUrl + "/auth";

    @Test
    @DisplayName("Authenticate_CredentialsCorrect_ReturnToken_Test")
    public void Authenticate_CredentialsCorrect_ReturnOk_Test() {
        loadDataSet("src/test/resources/datasets/usersForAuth.xml");

        AuthenticationRequest request = new AuthenticationRequest("userVerified", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(AUTH_URL + "/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("token");
    }

    @Test
    @DisplayName("Authenticate_CredentialsIncorrect_ReturnUnauthorized_Test")
    public void Authenticate_CredentialsIncorrect_ReturnUnauthorized_Test() {
        loadDataSet("src/test/resources/datasets/usersForAuth.xml");

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
        loadDataSet("src/test/resources/datasets/usersForAuth.xml");

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
        loadDataSet("src/test/resources/datasets/usersForAuth.xml");

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
        loadDataSet("src/test/resources/datasets/usersForAuth.xml");

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
        loadDataSet("src/test/resources/datasets/usersForAuth.xml");

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
        loadDataSet("src/test/resources/datasets/usersForAuth.xml");

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
}
