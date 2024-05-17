package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerIT extends BaseConfig {
    private static String USERS_URL = baseUrl;

    private String adminToken;

    @BeforeEach
    public void setUp() throws MessagingException, IOException, InterruptedException {
        USERS_URL = baseUrl + "/users";
        String AUTH_URL = baseUrl + "/auth";

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
    @DisplayName("GetAll_ReturnCorrectAmountOfUsers_Test")
    public void GetAll_ReturnCorrectAmountOfUsers_Test() {
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

    @Test
    @DisplayName("Get_ReturnCorrectUser_Test")
    public void Get_ReturnCorrectUser_Test() {

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/2d1c91e8-cc0c-4f33-ae6e-20a9948a6f2d")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo("2d1c91e8-cc0c-4f33-ae6e-20a9948a6f2d"))
                .body("firstName", equalTo("User"))
                .body("lastName", equalTo("User"))
                .body("login", equalTo("user"))
                .body("email", equalTo("user@test.com"))
                .body("language", equalTo("en"))
                .body("lastSuccessfulLogin", equalTo("2024-05-04 14:33:01"))
                .body("lastFailedLogin", equalTo("2024-05-04 14:33:01"))
                .body("verified", equalTo(true))
                .body("blocked", equalTo(false));
    }

    @Test
    @DisplayName("BlockUser_BlockUser_Test")
    public void BlockUser_BlockUser_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .post(USERS_URL + "/2d1c91e8-cc0c-4f33-ae6e-20a9948a6f2d" + "/block")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/2d1c91e8-cc0c-4f33-ae6e-20a9948a6f2d")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("blocked", equalTo(true));
    }

    @Test
    @DisplayName("BlockUser_UserDoesNotExist_NotFound_Test")
    public void BlockUser_UserDoesNotExist_NotFound_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .post(USERS_URL + "/3a9a32e2-0f9e-4edf-b1ed-0e01386d6e50" + "/block")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("UnblockUser_UserDoesNotExist_NotFound_Test")
    public void UnblockUser_UserDoesNotExist_NotFound_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .post(USERS_URL + "/3a9a32e2-0f9e-4edf-b1ed-0e01386d6e50" + "/unblock")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("UnblockUser_UnblockBlockedUser_UserUnblocked_Test")
    public void UnblockUser_UnblockBlockedUser_UserUnblocked_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .post(USERS_URL + "/3a9a32e2-0f9e-4edf-b1ed-0e01386d6e49" + "/unblock")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/3a9a32e2-0f9e-4edf-b1ed-0e01386d6e49")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("blocked", equalTo(false));
    }

    @Test
    @DisplayName("updateUserData_IfMatchCorrect_returnOK")
    public void updateUserData_IfMatchCorrect_returnOK() {
        Response response = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/2d1c91e8-cc0c-4f33-ae6e-20a9948a6f2d")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("en"))
                .extract()
                .response();

        String etag = response.getHeader("ETag");
        etag = etag.substring(1, etag.length() - 1);

        UpdateUserDataRequest updateRequest = new UpdateUserDataRequest("admin", "user", "pl");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header("If-Match", etag)
                .body(updateRequest)
                .when()
                .put(USERS_URL + "/2d1c91e8-cc0c-4f33-ae6e-20a9948a6f2d")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("pl"));
    }

    @Test
    @DisplayName("updateUserData_IfMatchNotCorrect_returnOK")
    public void updateUserData_IfMatchNotCorrect_returnOK() {
        Response response = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/2d1c91e8-cc0c-4f33-ae6e-20a9948a6f2d")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("en"))
                .extract()
                .response();

        String etag = response.getHeader("ETag");
        etag = etag.substring(1, etag.length() - 1);

        UpdateUserDataRequest updateRequest = new UpdateUserDataRequest("admin", "user", "pl");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header("If-Match", etag)
                .body(updateRequest)
                .when()
                .put(USERS_URL + "/2d1c91e8-cc0c-4f33-ae6e-20a9948a6f2d")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("pl"));

        UpdateUserDataRequest updateRequest2 = new UpdateUserDataRequest("admin", "user", "en");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header("If-Match", etag)
                .when()
                .body(updateRequest2)
                .put(USERS_URL + "/2d1c91e8-cc0c-4f33-ae6e-20a9948a6f2d")
                .then()
                .assertThat()
                .statusCode(HttpStatus.PRECONDITION_FAILED.value());
    }
}
