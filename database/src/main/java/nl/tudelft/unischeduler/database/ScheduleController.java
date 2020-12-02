package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.Lecture;
import nl.tudelft.unischeduler.database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScheduleController {

    @Autowired
    private transient ScheduleService scheduleService;
    @Autowired
    private transient UserService studentService;
    @Autowired
    private transient LectureService lectureService;

//    @PutMapping(path = "/assignStudentToLecture/{studentId}/{lectureId}")
//    public @ResponseBody
//    void assignStudentToLecture(@PathVariable String studentId, @PathVariable Long lectureId){
//        User u = studentService.getUser(studentId);
//        Lecture l = lectureService.getLecture(lectureId);
//        u.getSchedule().getLectures().add(l);
//    }
}
