package nl.tudelft.unischeduler.database;

import java.sql.Timestamp;
import nl.tudelft.unischeduler.database.entities.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LectureController {
    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient LectureService lectureService;

    //    @PutMapping (path = "/assignRoomToLecture/{lectureId}/{classroomId}")
    //    public @ResponseBody
    //    void assignRoomToLecture (@PathVariable Long lectureId, Long classroomId){
    //        lectureService.assignRoomToLecture(lectureId, classroomId);
    //    }
    //
    //    @PutMapping (path = "/setLectureTime/{lectureId}/{t}")
    //    public @ResponseBody
    //    void setLectureTime (@PathVariable Long lectureId, Timestamp t){
    //        lectureService.setLectureTime(lectureId, t);
    //    }

    @GetMapping(path = "/assignRoomToLecture/{classroomId}/{t}")
    public @ResponseBody
    Iterable<Lecture> getLecturesInRoomOnDay(
            @PathVariable Long classroomId, @PathVariable Timestamp t) {
        return lectureService.getLecturesInRoomOnDay(classroomId, t);
    }
}
