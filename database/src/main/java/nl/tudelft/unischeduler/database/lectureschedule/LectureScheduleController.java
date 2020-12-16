package nl.tudelft.unischeduler.database.lectureschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LectureScheduleController {

    @Autowired
    private transient LectureScheduleService lectureScheduleService;

    @PutMapping(path = "/lectureSchedules/{netId}/{lectureId}")
    public @ResponseBody
    ResponseEntity<?> assignStudentToLecture(
            @PathVariable String netId, @PathVariable Long lectureId) {
        return lectureScheduleService.assignLectureToSchedule(netId, lectureId);
    }

    @DeleteMapping(path = "/lectureSchedules/remove/{lectureId}")
    public @ResponseBody
    ResponseEntity<?> removeLectureFromSchedule(@PathVariable Long lectureId) {
        return lectureScheduleService.removeLectureFromSchedule(lectureId);
    }

}
