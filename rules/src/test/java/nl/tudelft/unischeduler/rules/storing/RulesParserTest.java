package nl.tudelft.unischeduler.rules.storing;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import nl.tudelft.unischeduler.rules.Ruleset;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RulesParserTest {

    @Test
    public void parseFileTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/test-rules.json");
        RulesParser parser =
                new RulesParser("test-rules.json", file, objectMapper);
        Ruleset expected = new Ruleset();
        Ruleset actual = parser.parseRules();
        assertEquals(expected, actual);
    }

}
