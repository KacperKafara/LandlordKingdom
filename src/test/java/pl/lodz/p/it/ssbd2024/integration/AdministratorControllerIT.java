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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AdministratorControllerIT extends BaseConfig {
    private static String ADMINS_URL = baseUrl + "/admins";

    private String adminToken;

    @BeforeEach
    public void setUp() throws MessagingException, IOException, InterruptedException {
        String AUTH_URL = baseUrl + "/auth";
        ADMINS_URL = baseUrl + "/admins";

        loadDataSet("src/test/resources/datasets/usersForAdministratorsIT.xml");

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
                .put(ADMINS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a7" + "/add-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("AddRole_UserDoesNotExist_ReturnNotFound_Test")
    public void AddRole_UserDoesNotExist_ReturnNotFound_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(ADMINS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a8" + "/add-role")
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
                .put(ADMINS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a6" + "/remove-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("RemoveRole_UserDoesNotExist_ReturnNotFound_Test")
    public void RemoveRole_UserDoesNotExist_ReturnNotFound_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(ADMINS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8b9" + "/remove-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("RemoveRole_SelfRoleRemoval_ReturnForbidden_Test")
    public void RemoveRole_SelfRoleRemoval_ReturnForbidden_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(ADMINS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a5" + "/remove-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
