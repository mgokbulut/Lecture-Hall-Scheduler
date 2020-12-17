package nl.tudelft.unischeduler.rules.controllers;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import nl.tudelft.unischeduler.rules.services.DatabaseService;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Data
@AllArgsConstructor
@RestController
public class UpdateController {

    @NonNull private RulesParser parser;
    @NonNull private RulesModule module;
    @NonNull private DatabaseService databaseService;

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

        verifyLectures();

        System.out.println("updated the rules: \n" + newRules);
    }

    public boolean verifyLectures() {

        Lecture[] lectures = databaseService.getLectures();
        Lecture[] toRemove = module.verifyLectures(lectures);

        for(int i = 0; i < toRemove.length; i++) {
            databaseService.removeLectureFromSchedule(toRemove[i].getId());
            databaseService.removeRoomFromLecture(toRemove[i].getId());
        }
        return toRemove.length == 0;
    }
}

