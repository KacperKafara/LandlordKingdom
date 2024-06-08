package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TenantControllerIT extends BaseConfig {
    private static String TENANTS_URL = baseUrl;
    private static String USERS_URL = baseUrl + "/users";

    private String adminToken;

    @BeforeEach
    public void setUp() {
        String AUTH_URL = baseUrl + "/auth";
        TENANTS_URL = baseUrl + "/tenants";
        USERS_URL = baseUrl + "/users";

        loadDataSet("src/test/resources/datasets/usersForTenantsIT.xml");

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
    @DisplayName("AddRole_UserWithoutRole_ReturnOk_Test")
    public void AddRole_UserWithoutRole_ReturnOk_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a7")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("roles", not(hasItem("TENANT")));

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(TENANTS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a7" + "/add-role")
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
                .body("roles", hasItem("TENANT"));
    }

    @Test
    @DisplayName("AddRole_UserDoesNotExist_ReturnNotFound_Test")
    public void AddRole_UserDoesNotExist_ReturnNotFound_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(TENANTS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a8" + "/add-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("RemoveRole_UserWithRole_ReturnOkAndRoleRemoved_Test")
    public void RemoveRole_UserWithRole_ReturnOkAndRoleRemoved_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(USERS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a6")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("roles", hasItem("TENANT"));

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(TENANTS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a6" + "/remove-role")
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
                .body("roles", not(hasItem("TENANT")));
    }

    @Test
    @DisplayName("RemoveRole_UserDoesNotExist_ReturnNotFound_Test")
    public void RemoveRole_UserDoesNotExist_ReturnNotFound_Test() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .put(TENANTS_URL + "/ba537227-d54f-42b3-aa58-10492cddf8a8" + "/remove-role")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
