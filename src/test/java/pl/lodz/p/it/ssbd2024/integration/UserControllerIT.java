package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerIT extends BaseConfig {
    private static final String USERS_URL = baseUrl + "/users";
    private static final String AUTH_URL = baseUrl + "/auth";

    private String adminToken;

    @BeforeEach
    public void setUp() throws MessagingException, IOException {
        loadDataSet("src/test/resources/datasets/usersForUsersIT.xml");

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
    @DisplayName("Get_ReturnCorrectAmountOfUsers_Test")
    public void shouldGetAllUsers() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(6));
    }
}
