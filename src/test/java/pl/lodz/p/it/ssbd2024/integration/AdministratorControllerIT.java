package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdministratorControllerIT extends BaseConfig {
    private static final String ADMINS_URL = baseUrl + "/admins";
//
//    @BeforeEach
//    public void getToken() {
//        loadDataSet("src/test/resources/datasets/usersForTests.xml");
//
//        AuthenticationRequest signinRequest = new AuthenticationRequest("userVerified", "password", "en");
//
//
//        given()
//                .contentType(ContentType.JSON)
//                .when()
//                .body(signinRequest)
//                .post(AUTH_URL + "/signin-2fa")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK.value());
//
//        String otp = EmailReader.readOtpFromEmail("userVerified@test.com");
//
//        assertNotNull(otp);
//
//        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("userVerified", otp);
//
//        Response response = given()
//                .contentType(ContentType.JSON)
//                .when()
//                .body(verifyRequest)
//                .post(AUTH_URL + "/verify-2fa")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK.value())
//                .extract()
//                .response();
//    }
}
