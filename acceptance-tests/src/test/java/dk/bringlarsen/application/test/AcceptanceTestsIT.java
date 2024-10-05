package dk.bringlarsen.application.test;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

@Suite
@SelectClasspathResource("dk/bringlarsen/application/test")
public class AcceptanceTestsIT {

    static Logger LOG = LoggerFactory.getLogger(AcceptanceTestsIT.class);

    static ComposeContainer composeContainer = new ComposeContainer(new File("src/test/resources/docker-compose.yml"))
        .withLogConsumer("app", new Slf4jLogConsumer(LOG)) // Adjust the loglevel in logback-test.xml if logs needs to be inspected
        .withExposedService("app", 8080, Wait.forHttp("/actuator").forStatusCode(200).withStartupTimeout(Duration.ofMinutes(2)));

    @BeforeAll
    public static void setup() {
        composeContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        composeContainer.stop();
    }
}
