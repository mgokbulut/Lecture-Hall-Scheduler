package nl.tudelft.unischeduler.database.LectureSchedule;

import nl.tudelft.unischeduler.database.Lecture.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class LectureScheduleController {

    @Autowired
    private transient LectureScheduleService lectureScheduleService;

    @PutMapping(path = "/lectureSchedule/{net_id}/{lectureId}")
    public @ResponseBody
    String assignStudentToLecture(@PathVariable String net_id, @PathVariable Long lectureId){
        return lectureScheduleService.assignLectureToSchedule(lectureId, net_id);
    }
}
