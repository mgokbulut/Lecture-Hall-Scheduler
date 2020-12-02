package nl.tudelft.unischeduler.rules.storing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import nl.tudelft.unischeduler.rules.entities.Ruleset;

/**
 * Reads the rules from file and returns a rules entity.
 */
public class RulesParser {
    private static final String STANDARD_FILE_NAME = "test/resources/rules.json";
    private File rulesFile;
    private ObjectMapper mapper;

    /**
     * Only used for testing the class make sure that fileName points to the same file as rulesFile.
     *
     * @param rulesFile The file located at the rulesFile location.
     * @param mapper The ObjectMapper to be used for parsing and creating the json text.
     *               Can be the same for all parsing needs.
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

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Reads and parses the rulesFile and returns the RuleSet read from disc.
     *
     * @return A Ruleset object containing all the rules read from disc.
     * @throws IOException If something goes wrong with accessing the files.
     */
    public Ruleset parseRules() throws IOException {
        JsonNode jsonNode = mapper.readTree(rulesFile);
        return mapper.treeToValue(jsonNode, Ruleset.class);
    }

    /**
     * Creates a new file or replaces the content with the newRuleset.
     *
     * @param newRuleset The new set of rules that should be stored on disc.
     * @throws IOException If something goes wrong with accessing the files.
     */
    public void writeRules(Ruleset newRuleset) throws IOException {
        rulesFile.createNewFile();
        mapper.writeValue(rulesFile, newRuleset);
    }

    /**
     * deletes the file corresponding to this rulesParser from the server.
     *
     * @return True iff the file was successfully deleted.
     * @throws IOException If something goes wrong with accessing the files.
     */
    public boolean delete() throws IOException {
        if (rulesFile.isFile()) {
            return rulesFile.delete();
        } else if (!rulesFile.exists()) {
            return false;
        } else {
            throw new IOException("File was not pointing to a file");
        }
    }
}
