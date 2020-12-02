package nl.tudelft.unischeduler.rules.storing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import nl.tudelft.unischeduler.rules.Ruleset;

/**
 * Reads the rules from file and returns a rules entity.
 */
public class RulesParser {
    private static final String STANDARD_FILE_NAME = "test/resources/rules.json";
    private File rulesFile;
    private final ObjectMapper mapper;

    /**
     * Only used for testing the class make sure that fileName points to the same file as rulesFile.
     *
     * @param rulesFile The file located at the rulesFile location.
     */
    public RulesParser(File rulesFile, ObjectMapper mapper) {
        this.rulesFile = rulesFile;
        this.mapper = mapper;
    }

    /**
     * Standard constructor which uses the standard file location and name.
     * <p>Uses the standard fileName which is "rules.json"</p>
     */
    public RulesParser(ObjectMapper objectMapper) {
        this.rulesFile = new File(STANDARD_FILE_NAME);
        this.mapper = objectMapper;
    }

    public File getRulesFile() {
        return rulesFile;
    }

    public void setRulesFile(File rulesFile) {
        this.rulesFile = rulesFile;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public synchronized Ruleset parseRules() throws IOException {
        JsonNode jsonNode = mapper.readTree(rulesFile);
        return mapper.treeToValue(jsonNode, Ruleset.class);
    }

    public synchronized void writeRules(Ruleset newRuleset) throws IOException {
        mapper.writeValue(rulesFile, newRuleset);
    }
}
