package nl.tudelft.unischeduler.rules.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class VerificationControllerTest extends ControllerTest {

    private static final String RULES_STRING = "{\"thresholds\":[[0,10],[100,20],[150,25]],"
            + "\"breakTime\":45,\"maxDays\":14}";

    private static final File TEST_FILE =
            new File("src/test/resources/integration-verification-test.json");

    @BeforeAll
    static void setUpTestFile(@Autowired RulesParser rulesParser,
                              @Autowired RulesModule module) throws IOException {
        int[][] thresholds = {{0, 10}, {100, 20}, {150, 25}};
        Ruleset savedRules = new Ruleset(thresholds, 45, 14);
        rulesParser.setRulesFile(TEST_FILE);
        rulesParser.writeRules(savedRules);
        module.setRules(savedRules);
    }

    @AfterAll
    static void deleteFile() {
        TEST_FILE.delete();
    }

    @Test
    public void getRulesTest() throws Exception {
        String actual = mockMvc.perform(
                MockMvcRequestBuilders.get("/rules")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(RULES_STRING, actual);
    }

    @Test
    public void getCapacityTest() throws Exception {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":1,\"capacity\":90,\"name\":\"testroom\"}")
        );
        String expected = "9";

        String actual = mockMvc.perform(
                MockMvcRequestBuilders.get("/room/" + 1 + "/capacity")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    @Test
    public void canBeScheduledTest() throws Exception {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("{\"netid\":\"testNetId\",\"interested\":true,\"recovered\":false}")
        );
        String expected = "false";

        String actual = mockMvc.perform(
                MockMvcRequestBuilders.get("/student/" + 1 + "/can-be-scheduled")
        ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    @Test
    public void checkLectureSlotAvailableTest() throws Exception {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("")
        );
        String expected = "true";

        String actual = mockMvc.perform(
                MockMvcRequestBuilders.post("/lecture/possible")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\n"
                                + "\"attendance\":150,\n"
                                + "\"startTime\":\"2000-01-01T12:00:00Z\",\n"
                                + "\"duration\":\"1:00:00\",\n"
                                + "\"room\":{\"id\":1,\"capacity\":90,\"name\":\"testroom\"}}")
        ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expected, actual);
    }
}
