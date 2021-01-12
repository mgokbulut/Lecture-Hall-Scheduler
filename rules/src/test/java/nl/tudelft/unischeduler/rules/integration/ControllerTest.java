package nl.tudelft.unischeduler.rules.integration;

import java.io.IOException;
import lombok.Data;
import nl.tudelft.unischeduler.rules.controllers.UpdateController;
import nl.tudelft.unischeduler.rules.services.ClassRoomDatabaseService;
import nl.tudelft.unischeduler.rules.services.LectureDatabaseService;
import nl.tudelft.unischeduler.rules.services.StudentDatabaseService;
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
import org.springframework.web.reactive.function.client.WebClient;

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
    protected ClassRoomDatabaseService classRoomDatabaseService;
    @Autowired
    protected LectureDatabaseService lectureDatabaseService;
    @Autowired
    protected StudentDatabaseService studentDatabaseService;
    @Autowired
    protected WebClient.Builder webClientBuilder;

    protected static final MockResponse standardResponse = new MockResponse().setBody("true");



    @BeforeEach
    void beforeEach() throws IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        server = new MockWebServer();
        server.start();
        WebClient databaseWebClient = webClientBuilder.baseUrl("http://"
                + server.getHostName()
                + ":"
                + server.getPort()
                + "/").build();
        classRoomDatabaseService.setDatabaseWebClient(databaseWebClient);
        lectureDatabaseService.setDatabaseWebClient(databaseWebClient);
        studentDatabaseService.setDatabaseWebClient(databaseWebClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }
}