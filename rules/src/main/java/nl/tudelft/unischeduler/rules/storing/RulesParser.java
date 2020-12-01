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
    private String fileName = "test/resources/rules.json";
    private File rulesFile;
    private final ObjectMapper mapper;

    /**
     * Only used for testing the class make sure that fileName points to the same file as rulesFile.
     *
     * @param fileName The name of the file where the rules are stored.
     * @param rulesFile The file located at the rulesFile location.
     */
    public RulesParser(String fileName, File rulesFile, ObjectMapper mapper) {
        this.fileName = fileName;
        this.rulesFile = rulesFile;
        this.mapper = mapper;
    }

    /**
     * Used to use a different file as the rules file.
     *
     * @param rulesFileName The name of the file to use.
     */
    public RulesParser(String rulesFileName, ObjectMapper objectMapper) {
        this.fileName = rulesFileName;
        this.rulesFile = new File(rulesFileName);
        this.mapper =  objectMapper;
    }

    /**
     * Standard constructor which uses the standard file location and name.
     * <p>Uses the standard fileName which is "rules.json"</p>
     */
    public RulesParser(ObjectMapper objectMapper) {
        this.rulesFile = new File(this.fileName);
        this.mapper = objectMapper;
    }

    public String getFileName() {
        return fileName;
    }

    public File getRulesFile() {
        return rulesFile;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setRulesFile(File rulesFile) {
        this.rulesFile = rulesFile;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public Ruleset parseRules() throws IOException {
        JsonNode jsonNode = mapper.readTree(rulesFile);
        return mapper.treeToValue(jsonNode, Ruleset.class);
    }
}
