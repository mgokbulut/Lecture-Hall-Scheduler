package nl.tudelft.unischeduler.scheduleedit.integration;

import java.io.IOException;
import lombok.Data;
import nl.tudelft.unischeduler.scheduleedit.services.CourseService;
import nl.tudelft.unischeduler.scheduleedit.services.StudentService;
import nl.tudelft.unischeduler.scheduleedit.services.TeacherService;
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

@ContextConfiguration
@ComponentScan(basePackages = {"nl.tudelft.unischeduler.rules"})
@AutoConfigureMockMvc
@WebMvcTest
@Data
public abstract class ControllerTest {

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;
    public MockWebServer server;

    protected static final MockResponse standardResponse = new MockResponse().setBody("true");

    @Autowired protected CourseService courseService;
    @Autowired protected StudentService studentService;
    @Autowired protected TeacherService teacherService;

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
        courseService.getWebClientBuilder().baseUrl(basUrl);
        studentService.getWebClientBuilder().baseUrl(basUrl);
        teacherService.getWebClientBuilder().baseUrl(basUrl);
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }
}
