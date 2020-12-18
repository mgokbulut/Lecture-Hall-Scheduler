package nl.tudelft.unischeduler.rules.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateControllerTest extends ControllerTest {

    @Autowired
    private RulesParser rulesParser;

    private File testFile;

    @AfterEach
    public void tearDown() {
        testFile.delete();
    }

    @Test
    public void addNewRulesTest() throws Exception {
        this.testFile = new File("src/test/resources/integration-test.json");
        rulesParser.setRulesFile(testFile);
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":1,"
                        + "\"attendance\":1,"
                        + "\"startTime\":1608237156424,"
                        + "\"duration\":\"01:00:00\","
                        + "\"room\":{\"id\":1,"
                        + "\"capacity\":1,"
                        + "\"name\":\"nice room\"}}"));
        server.enqueue(standardResponse);
        server.enqueue(standardResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"thresholds\":[[0,10],[200,20]],"
                                + "\"breakTime\":45,"
                                + "\"maxDays\":14}")
        ).andExpect(status().isOk());
    }
}
