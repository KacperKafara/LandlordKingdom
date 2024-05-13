package pl.lodz.p.it.ssbd2024.integration;


import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;

import static io.restassured.RestAssured.given;
import static org.dbunit.Assertion.assertEquals;
import static org.hamcrest.Matchers.equalTo;

public class IntegrationExampleIT extends BaseConfig {


    @Test
    public void test() {
        given()
                .when()
                .get(baseUrl)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void dbunitTest() throws Exception {
        loadDataSet("src/test/resources/datasets/users.xml");

        ReplacementDataSet dataSetFromDb = createDataSetFromDb();

        IDataSet resultDataset = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/datasets/usersResult.xml"));

        assertEquals(resultDataset, dataSetFromDb);
    }
}
