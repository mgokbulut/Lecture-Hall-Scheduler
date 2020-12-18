package nl.tudelft.unischeduler.rules.integration;

import java.io.IOException;
import lombok.Data;
import nl.tudelft.unischeduler.rules.controllers.UpdateController;
import nl.tudelft.unischeduler.rules.services.DatabaseService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration(classes = {UpdateController.class})
@ComponentScan(basePackages = {"nl.tudelft.unischeduler.rules"})
@AutoConfigureMockMvc
@WebMvcTest
@Data
public abstract class ControllerTest {

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;
    public MockWebServer server;
    @Autowired
    protected DatabaseService databaseService;

    protected static final MockResponse standardResponse = new MockResponse().setBody("true");

    @BeforeEach
    void beforeEach() throws IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        server = new MockWebServer();
        server.start();
        databaseService.getWebClientBuilder().baseUrl("http://"
                + server.getHostName()
                + ":"
                + server.getPort()
                + "/");
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }
}