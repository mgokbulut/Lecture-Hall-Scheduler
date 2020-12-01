package nl.tudelft.unischeduler.rules.core;

import java.io.File;

/**
 * Reads the rules from file and returns a rules entity.
 */
public class RulesParser {
    private String fileName = "rules.json";
    private File rulesFile;

    /**
     * Only used for testing the class make sure that fileName points to the same file as rulesFile.
     *
     * @param fileName The name of the file where the rules are stored.
     * @param rulesFile The file located at the rulesFile location.
     */
    public RulesParser(String fileName, File rulesFile) {
        this.fileName = fileName;
        this.rulesFile = rulesFile;
    }

    /**
     * Used to use a different file as the rules file.
     *
     * @param rulesFileName The name of the file to use.
     */
    public RulesParser(String rulesFileName) {
        this.fileName = rulesFileName;
        this.rulesFile = new File(rulesFileName);
    }

    /**
     * Standard constructor which uses the standard file location and name.
     * <p>Uses the standard fileName which is "rules.json"</p>
     */
    public RulesParser() {
        this.rulesFile = new File(this.fileName);
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
}
