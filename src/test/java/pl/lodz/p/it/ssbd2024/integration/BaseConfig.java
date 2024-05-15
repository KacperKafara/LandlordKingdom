package pl.lodz.p.it.ssbd2024.integration;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.sql.*;

@Testcontainers
public class BaseConfig {
    static final Network network = Network.newNetwork();

    static MountableFile war = MountableFile
            .forHostPath(Paths.get("target/ssbd02.war").toAbsolutePath());

    public static String baseUrl;

    @Container
    static final PostgreSQLContainer<?> postgres;

    @Container
    static final GenericContainer<?> smtp;

    @Container
    static final GenericContainer<?> tomcat;

    public static IDatabaseConnection connection;

    static {
        postgres = new PostgreSQLContainer<>("postgres:16.2")
                .withNetwork(network)
                .withNetworkAliases("testdb")
                .waitingFor(Wait.defaultWaitStrategy())
                .withExposedPorts(5432)
                .withUsername("postgres")
                .withPassword("postgres")
                .withCopyFileToContainer(MountableFile.forClasspathResource("initTest.sql"), "/docker-entrypoint-initdb.d/init.sql")
                .withReuse(true);
        postgres.start();

        smtp = new GenericContainer<>("rnwood/smtp4dev")
                .withNetwork(network)
                .withNetworkAliases("smtp4test")
                .withLogConsumer(outputFrame -> System.out.println(outputFrame.getUtf8String()))
                .waitingFor(Wait.defaultWaitStrategy())
                .withExposedPorts(25, 143, 80)
                .withReuse(true);
        smtp.start();

        tomcat = new GenericContainer<>("tomcat:10.1.20-jdk21")
                .withNetwork(network)
                .withExposedPorts(8080)
                .withLogConsumer(outputFrame -> System.out.println(outputFrame.getUtf8String()))
                .dependsOn(postgres)
                .dependsOn(smtp)
                .withEnv("URL", "jdbc:postgresql://testdb:5432/ssbd02")
                .withEnv("MAIL.PORT", "25")
                .withEnv("MAIL.HOST", "smtp4test")
                .withCopyToContainer(war, "/usr/local/tomcat/webapps/ssbd02.war")
                .waitingFor(Wait.forHttp("/ssbd02/").forPort(8080))
                .withReuse(true);
        tomcat.start();

        baseUrl = "http://" + tomcat.getHost() + ":" + tomcat.getMappedPort(8080) + "/ssbd02";
    }

    @BeforeEach
    public void setup() throws Exception {
        int postgresPort = postgres.getMappedPort(5432);

        String jdbcUrl = String.format("jdbc:postgresql://localhost:%d/ssbd02?loggerLevel=OFF", postgresPort);
        Connection jdbcConnection = DriverManager.getConnection(jdbcUrl, postgres.getUsername(), postgres.getPassword());

        connection = new DatabaseConnection(jdbcConnection);

        DatabaseConfig dbConfig = connection.getConfig();
        dbConfig.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());

        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void teardown() throws Exception {
        tomcat.stop();
        postgres.stop();
        smtp.stop();

        if (connection != null) {
            connection.close();
        }
    }

    protected void loadDataSet(String filePath) {
        try {
            IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(filePath));

            ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
            replacementDataSet.addReplacementObject("[NULL]", null);

            DatabaseOperation.REFRESH.execute(connection, replacementDataSet);
        } catch (Exception e) {
            System.err.println("Error executing DBUnit operation: " + e.getMessage());
        }
    }

    protected ReplacementDataSet createDataSetFromDb() throws Exception {
        IDataSet actualDataSet = connection.createDataSet();

        ReplacementDataSet replacementDataSet = new ReplacementDataSet(actualDataSet);
        replacementDataSet.addReplacementObject(null, "[NULL]");

        return replacementDataSet;
    }
}
