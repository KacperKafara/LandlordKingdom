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
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class OwnerControllerIT extends BaseConfig {
    private static String OWNERS_URL = baseUrl + "/owners";
    private static String USERS_URL = baseUrl + "/users";

    private String adminToken;

    @BeforeEach
    public void setUp() throws MessagingException, IOException, InterruptedException {
        String AUTH_URL = baseUrl + "/auth";
        OWNERS_URL = baseUrl + "/owners";
        USERS_URL = baseUrl + "/users";

        loadDataSet("src/test/resources/datasets/usersForOwnersIT.xml");

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
    @DisplayName("AddRole_UserWithoutRole_ReturnOkAndRoleAdded_Test")
    public void AddRole_UserWithoutRole_ReturnOkAndRoleAdded_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a7")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("roles", not(hasItem("OWNER")));

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(OWNERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a7" + "/add-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a7")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("roles", hasItem("OWNER"));
    }

    @Test
    @DisplayName("AddRole_UserDoesNotExist_ReturnNotFound_Test")
    public void AddRole_UserDoesNotExist_ReturnNotFound_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(OWNERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a8" + "/add-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("RemoveRole_UserWithRole_ReturnOk_Test")
    public void RemoveRole_UserWithRole_ReturnOk_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a6")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("roles", hasItem("OWNER"));

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(OWNERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a6" + "/remove-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a6")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("roles", not(hasItem("OWNER")));
    }

    @Test
    @DisplayName("RemoveRole_UserDoesNotExist_ReturnNotFound_Test")
    public void RemoveRole_UserDoesNotExist_ReturnNotFound_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(OWNERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a8" + "/remove-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
