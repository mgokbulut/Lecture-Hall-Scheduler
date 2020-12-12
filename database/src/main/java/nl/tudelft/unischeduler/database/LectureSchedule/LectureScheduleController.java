package nl.tudelft.unischeduler.database.LectureSchedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LectureScheduleController {

    @Autowired
    private transient LectureScheduleService lectureScheduleService;

    @PutMapping(path = "/lectureSchedules/{net_id}/{lectureId}")
    public @ResponseBody
    ResponseEntity<?> assignStudentToLecture(@PathVariable String net_id, @PathVariable Long lectureId){
        return lectureScheduleService.assignLectureToSchedule(net_id, lectureId);
    }

    @DeleteMapping(path = "/lectureSchedules/remove/{lectureId}")
    public @ResponseBody
    ResponseEntity<?> removeLectureFromSchedule(@PathVariable Long lectureId){
        return lectureScheduleService.removeLectureFromSchedule(lectureId);
    }

}
