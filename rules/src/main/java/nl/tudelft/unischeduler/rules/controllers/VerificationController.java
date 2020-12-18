package nl.tudelft.unischeduler.rules.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Data
@AllArgsConstructor
@RestController
public class VerificationController {
    @Autowired private RulesParser parser;
    @Autowired private RulesModule module;


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
    @GetMapping("/room/{roomId}/capacity")
    public int getCapacityOfRoom(@PathVariable int roomId) {
        return module.getCapacityOfRoom(roomId);
    }

    /**
     * Checks whether the student with the netId should be scheduled.
     *
     * @param studentNetId The netId of the student to check.
     * @return true iff the student is interested and the student does not have corona.
     */
    @GetMapping("/student/{studentNetId}/can-be-scheduled")
    public boolean canBeScheduled(@PathVariable String studentNetId) {
        return module.canBeScheduled(studentNetId);
    }

    @PostMapping("/lecture/possible")
    public boolean checkLectureSlotAvailable(@RequestBody Lecture lecture) {
        return module.overlap(lecture);
    }



}
