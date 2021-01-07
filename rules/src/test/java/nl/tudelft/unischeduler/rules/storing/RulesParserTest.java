package nl.tudelft.unischeduler.rules.storing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import org.junit.Test;


public class RulesParserTest {

    @Test
    public void parseFileTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/read-test-rules.json");
        RulesParser parser = new RulesParser(file, objectMapper);
        Ruleset expected = new Ruleset(new int[][]{{100, 10}, {200, 20}}, 45, 14);
        Ruleset actual = parser.parseRules();
        assertEquals(expected, actual);
    }

    @Test
    public void writeFileTest() throws IOException {
        File file = new File("src/test/resources/write-test-rules.json");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RulesParser parser =
                    new RulesParser(file, objectMapper);
            Ruleset expected = new Ruleset();
            parser.writeRules(expected);
            Ruleset actual = parser.parseRules();
            assertEquals(expected, actual);
        } finally {
            try (Writer writer = new FileWriter(file)) {
                writer.write("");
            }
        }
    }

    @Test
    public void deleteNonExistentTest() throws IOException {
        File file = new File("src/test/resources/non-existing.json");
        file.deleteOnExit();
        ObjectMapper objectMapper = new ObjectMapper();
        RulesParser parser = new RulesParser(file, objectMapper);
        assertThat(parser.delete()).isFalse();
        file.createNewFile();
        assertThat(parser.delete()).isTrue();
        assertThat(file.exists()).isFalse();
    }
}
