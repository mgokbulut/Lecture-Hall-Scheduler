package nl.tudelft.unischeduler.rules.controllers;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import nl.tudelft.unischeduler.rules.services.DatabaseService;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Data
@AllArgsConstructor
@RestController
public class UpdateController {

    @Autowired private RulesParser parser;
    @Autowired private RulesModule module;
    @Autowired private DatabaseService databaseService;

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

        module.verifyLectures();

        System.out.println("updated the rules: \n" + newRules);
    }
}

