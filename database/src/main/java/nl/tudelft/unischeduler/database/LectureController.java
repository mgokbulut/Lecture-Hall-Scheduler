package nl.tudelft.unischeduler.database;

import java.sql.Timestamp;

import nl.tudelft.unischeduler.database.entities.Classroom;
import nl.tudelft.unischeduler.database.entities.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LectureController {
    //not sure if should be transient but checkstyle complaints without it...
    @Autowired
    private transient LectureService lectureService;
    @Autowired
    private transient ClassroomService classroomService;

//    @GetMapping(path = "/assignRoomToLecture/{classroomId}/{t}")
//    public @ResponseBody
//    Iterable<Lecture> getLecturesInRoomOnDay( @PathVariable Long classroomId, @PathVariable Timestamp t) {
//        return lectureService.getLecturesInRoomOnDay(classroomId, t);
//    }

    @PutMapping(path = "/setLectureTime/{lectureId}/{t}")
    public @ResponseBody
    void setLectureTime(@PathVariable Long lectureId, @PathVariable Timestamp t){
        lectureService.getLecture(lectureId).setStartTimeDate(t);
    }

//    @PutMapping(path = "/assignRoomToLecture/{lectureId}/{classroomId}")
//    public @ResponseBody
//    void assignRoomToLecture(@PathVariable Long lectureId, @PathVariable Long classroomId){
//        Classroom c = classroomService.getClassroom(classroomId);
//        lectureService.getLecture(lectureId).setClassroom(c);
//    }
}
