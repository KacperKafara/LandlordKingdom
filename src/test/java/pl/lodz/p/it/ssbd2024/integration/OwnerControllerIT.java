package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class OwnerControllerIT extends BaseConfig {
    private static String OWNERS_URL = baseUrl + "/owners";
    private static String USERS_URL = baseUrl + "/users";

    private String adminToken;

    @BeforeEach
    public void setUp() {
        String AUTH_URL = baseUrl + "/auth";
        OWNERS_URL = baseUrl + "/owners";
        USERS_URL = baseUrl + "/users";

        loadDataSet("src/test/resources/datasets/usersForOwnersIT.xml");

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("adminUser", "20099984");

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
