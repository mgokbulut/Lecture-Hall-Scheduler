package nl.tudelft.unischeduler.rules.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {
    private RulesParser parser;
    private RulesModule module;

    /**
     * Constructs a VerificationController from a single ruleParser.
     *
     * @param parser the parser from which to get the rules.
     */
    public VerificationController(RulesParser parser, RulesModule module) {
        this.parser = parser;
        this.module = module;
        try {
            this.module.setRules(parser.parseRules());
        } catch (FileNotFoundException e) {
            System.err.println("no rulesConfiguration file found,"
                    + " please create one with the appropriate api call");
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: stop server
        }
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

    @GetMapping("/rules")
    public Ruleset getRules() {
        return module.getRules();
    }

    /**
     * Returns the available capacity according to the rules and the capacity the room has.
     *
     * @param roomId The id of the room for which the capacity is not known.
     * @return The capacity if all the corona rules are followed.
     */
    @GetMapping("/room/capacity")
    public int getCapacityOfRoom(int roomId) {
        //TODO: add a call to the database module to retrieve the correct room.
        Room room = new Room(roomId, 100, "temp");
        return module.getCapacity(room.getCapacity());
    }

    @PostMapping("/lecture/possible")
    public boolean checkLectureSlotAvailable(Lecture lecture) {
        return module.overlap(lecture);
    }
}
