package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.ssbd2024.mok.dto.Verify2FATokenRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.NewPaymentRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.RentForOwnerResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.VariableFeeRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class BalanceIT extends BaseConfig {
    private static String ME_URL = baseUrl;
    private String ownerToken;
    private String tenantToken;

    @BeforeEach
    public void setUp() {
        ME_URL = baseUrl + "/me";
        String AUTH_URL = baseUrl + "/auth";
        loadDataSet("src/test/resources/datasets/userDataForMeOwnerIT.xml");
        loadDataSet("src/test/resources/datasets/molDataForMeOwnerIT.xml");

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("mol8owner", "20099995");
        Verify2FATokenRequest verifyTenantRequest = new Verify2FATokenRequest("mol8tenant", "30099997");

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

        ownerToken = response.path("token");

        Response responseTenant = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyTenantRequest)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();
        tenantToken = responseTenant.path("token");
    }

    @Test
    public void concurrentPaymentAndVariableFee_AdjustBalanceCorrectly() {
        BigDecimal paymentAmount = BigDecimal.valueOf(500.10);
        BigDecimal feeAmount = BigDecimal.valueOf(1000.00);
        NewPaymentRequest paymentRequest = new NewPaymentRequest(paymentAmount);
        VariableFeeRequest variableFeeRequest = new VariableFeeRequest(feeAmount);

        try {
            Response initialResponse = given()
                    .contentType(ContentType.JSON)
                    .auth().oauth2(ownerToken)
                    .when()
                    .get(ME_URL + "/owner/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35")
                    .then()
                    .extract()
                    .response();

            RentForOwnerResponse ownerRentResponse = initialResponse.getBody().as(RentForOwnerResponse.class);
            BigDecimal initialBalance = ownerRentResponse.balance();

            RequestSpecification paymentSpec = given()
                    .contentType(ContentType.JSON)
                    .auth().oauth2(ownerToken)
                    .body(paymentRequest);

            RequestSpecification feeSpec = given()
                    .contentType(ContentType.JSON)
                    .auth().oauth2(tenantToken)
                    .body(variableFeeRequest);

            CompletableFuture<Response> paymentFuture = CompletableFuture.supplyAsync(() ->
                    paymentSpec.post(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/payment"));

            CompletableFuture<Response> feeFuture = CompletableFuture.supplyAsync(() ->
                    feeSpec.post(ME_URL + "/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35/variable-fee"));

            CompletableFuture.allOf(paymentFuture, feeFuture).join();

            Response paymentResponse = paymentFuture.get();
            Response feeResponse = feeFuture.get();

            assertEquals(HttpStatus.OK.value(), paymentResponse.getStatusCode());
            assertEquals(HttpStatus.OK.value(), feeResponse.getStatusCode());

            Response finalResponse = given()
                    .contentType(ContentType.JSON)
                    .auth().oauth2(ownerToken)
                    .when()
                    .get(ME_URL + "/owner/rents/5571842e-ec61-4a03-8715-36ccf3c5aa35")
                    .then()
                    .extract()
                    .response();

            ownerRentResponse = finalResponse.getBody().as(RentForOwnerResponse.class);
            BigDecimal finalBalance = ownerRentResponse.balance();

            BigDecimal expectedBalance = initialBalance
                    .add(paymentAmount)
                    .subtract(feeAmount);

            assertEquals(expectedBalance, finalBalance);

        } catch (InterruptedException | ExecutionException e) {
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).forEach(log::error);
        }
    }
}
