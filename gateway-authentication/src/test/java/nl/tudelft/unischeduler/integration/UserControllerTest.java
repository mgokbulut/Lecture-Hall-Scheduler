package nl.tudelft.unischeduler.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Data;
import nl.tudelft.unischeduler.sysinteract.SysInteractController;
import nl.tudelft.unischeduler.user.UserService;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.QueueDispatcher;
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

@ContextConfiguration(classes = SysInteractController.class)
@ComponentScan(basePackages = {"nl.tudelft.unischeduler.user"})
@AutoConfigureMockMvc
@WebMvcTest
@Data
public abstract class UserControllerTest {

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;
    public MockWebServer server;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebClient.Builder webClientBuilder;

    @Autowired
    protected UserService userService;


    @BeforeEach
    void beforeEach() throws IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        server = new MockWebServer();
        server.start();
        //        String basUrl = "http://"
        //            + server.getHostName()
        //            + ":"
        //            + server.getPort()
        //            + "/";
        QueueDispatcher queueDispatcher = new QueueDispatcher();
        queueDispatcher.setFailFast(true);
        server.setDispatcher(queueDispatcher);

        // WebClient webClient = webClientBuilder.baseUrl(basUrl).build();
        // UserService.setWebClient(webClient);

    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }
}
