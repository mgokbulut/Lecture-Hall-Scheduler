package nl.tudelft.unischeduler.viewer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Data;
import nl.tudelft.unischeduler.viewer.controllers.UserController;
import nl.tudelft.unischeduler.viewer.services.DatabaseService;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.QueueDispatcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

@ContextConfiguration(classes = UserController.class)
@ComponentScan(basePackages = {"nl/tudelft/unischeduler/viewer"})
@AutoConfigureMockMvc
@WebMvcTest
public abstract class ControllerTest {

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected MockMvc mockMvc;
    public MockWebServer server;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebClient.Builder webClientBuilder;

    @Autowired
    @MockBean
    protected DatabaseService databaseService;

    @BeforeEach
    void beforeEach() throws IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        server = new MockWebServer();
        server.start();
        String basUrl = "http://"
                + server.getHostName()
                + ":"
                + server.getPort()
                + "/";
        QueueDispatcher queueDispatcher = new QueueDispatcher();
        queueDispatcher.setFailFast(true);
        server.setDispatcher(queueDispatcher);

        WebClient webClient = webClientBuilder.baseUrl(basUrl).build();
        databaseService.setWebClient(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }
}
