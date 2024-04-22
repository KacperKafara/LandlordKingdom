package pl.lodz.p.it.ssbd2024.integration;

import org.junit.jupiter.api.AfterAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Paths;

@Testcontainers
public class BaseConfig {
    static final Network network = Network.newNetwork();

    static MountableFile war = MountableFile
            .forHostPath(Paths.get("target/ssbd02.war").toAbsolutePath());

    public static String baseUrl;

    @Container
    static final PostgreSQLContainer<?> postgres;

    @Container
    static final GenericContainer<?> tomcat;

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

        tomcat = new GenericContainer<>("tomcat:10.1.20-jdk21")
                .withNetwork(network)
                .withExposedPorts(8080)
                .withLogConsumer(outputFrame -> System.out.println(outputFrame.getUtf8String()))
                .dependsOn(postgres)
                .withEnv("URL", "jdbc:postgresql://testdb:5432/ssbd02")
                .withCopyToContainer(war, "/usr/local/tomcat/webapps/ssbd02.war")
                .waitingFor(Wait.forHttp("/ssbd02/").forPort(8080))
                .withReuse(true);
        tomcat.start();

        baseUrl = "http://" + tomcat.getHost() + ":" + tomcat.getMappedPort(8080) + "/ssbd02";
    }

    @AfterAll
    public static void teardown() {
        tomcat.stop();
        postgres.stop();
    }
}
