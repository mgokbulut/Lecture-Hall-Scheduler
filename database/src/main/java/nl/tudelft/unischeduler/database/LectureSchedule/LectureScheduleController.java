package nl.tudelft.unischeduler.database.LectureSchedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LectureScheduleController {

    @Autowired
    private transient LectureScheduleService lectureScheduleService;

    @PutMapping(path = "/lectureSchedule/{net_id}/{lectureId}")
    public @ResponseBody
    String assignStudentToLecture(@PathVariable String net_id, @PathVariable Long lectureId){
        return lectureScheduleService.assignLectureToSchedule(lectureId, net_id);
    }

    @DeleteMapping(path = "/lectureSchedule/remove/{lectureId}")
    public @ResponseBody
    String removeLectureFromSchedule(@PathVariable Long lectureId){
        return lectureScheduleService.removeLectureFromSchedule(lectureId);
    }

}
