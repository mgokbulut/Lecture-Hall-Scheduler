package nl.tudelft.unischeduler.rules.servicestests;

import java.io.IOException;
import lombok.Data;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@WebMvcTest
@ComponentScan(basePackages = {"nl.tudelft.unischeduler.rules"})
public abstract class  ServiceTest {

    protected MockMvc mockMvc;
    public MockWebServer server;

    private WebClient.Builder webClientBuilder = WebClient.builder();

    protected WebClient databaseWebClient;

    protected static final MockResponse standardResponse = new MockResponse().setBody("true");


    @BeforeEach
    void beforeEach() throws IOException {
        server = new MockWebServer();
        server.start();
        databaseWebClient = webClientBuilder.baseUrl("http://"
                + server.getHostName()
                + ":"
                + server.getPort()
                + "/").build();
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }
}
