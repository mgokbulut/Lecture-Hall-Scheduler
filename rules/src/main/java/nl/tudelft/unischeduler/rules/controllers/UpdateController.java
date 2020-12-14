package nl.tudelft.unischeduler.rules.controllers;

import java.io.IOException;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {

    private RulesParser parser;
    private RulesModule module;

    public UpdateController(RulesParser parser, RulesModule module) {
        this.parser = parser;
        this.module = module;
    }

    public RulesParser getParser() {
        return parser;
    }

    public void setParser(RulesParser parser) {
        this.parser = parser;
    }

    public RulesModule getModule() {
        return module;
    }

    public void setModule(RulesModule module) {
        this.module = module;
    }

    /**
     * Updates the current rules file to the newRules passed by the body of the request.
     *
     * @param newRules The updated rules to use.
     * @throws IOException If anything goes wrong with saving the file to disk.
     */
    @PostMapping("/rules")
    public void updateRules(@RequestBody Ruleset newRules) throws IOException {
        parser.writeRules(newRules);
        module.setRules(newRules);
        Lecture[] lect = getLectures();
        module.verifySchedule(lect);
        System.out.println("updated the rules: \n" + newRules);
    }

    public Lecture[] getLectures() {
        Lecture[] ret = new Lecture[1];
        ret[0] = new Lecture(1, 1, null, null);
        return ret;
    }

}
