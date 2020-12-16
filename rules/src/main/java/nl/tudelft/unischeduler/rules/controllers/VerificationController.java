package nl.tudelft.unischeduler.rules.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import nl.tudelft.unischeduler.rules.entities.Student;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@AllArgsConstructor
@RestController
public class VerificationController {
    @NonNull private RulesParser parser;
    @NonNull private RulesModule module;


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

    //TODO: add a mapping to this method
    public boolean canBeScheduled(String studentId) {
        //TODO: add a call to the database module to retrieve the correct student.
        Student student = new Student("1", true, true);
        return module.canBeScheduled(student);
    }

    @PostMapping("/lecture/possible")
    public boolean checkLectureSlotAvailable(Lecture lecture) {
        return module.overlap(lecture);
    }



}
