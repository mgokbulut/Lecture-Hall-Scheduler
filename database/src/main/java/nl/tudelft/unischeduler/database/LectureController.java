package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.Classroom;
import nl.tudelft.unischeduler.database.entities.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class LectureController {
    @Autowired
    private LectureService lectureService;

    @PutMapping (path = "/lectures")
    public @ResponseBody
    void assignRoomToLecture (int lectureId, int classroomId){
        lectureService.assignRoomToLecture(lectureId, classroomId);
    }

    @PutMapping (path = "/lectures")
    public @ResponseBody
    void setLectureTime(int lectureId, Timestamp t){
        lectureService.setLectureTime(lectureId, t);
    }

    @GetMapping (path = "/lectures")
    public @ResponseBody
    Iterable<Lecture> getLecturesInRoomOnDay(Classroom c, Timestamp t){
        return lectureService.getLecturesInRoomOnDay(c, t);
    }
}
