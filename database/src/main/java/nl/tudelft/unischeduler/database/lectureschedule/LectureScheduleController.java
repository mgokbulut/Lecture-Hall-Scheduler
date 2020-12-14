package nl.tudelft.unischeduler.database.lectureschedule;

import nl.tudelft.unischeduler.database.lecture.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

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

    @DeleteMapping(path = "/lectureSchedules/remove/{netId}/{start}/{end}")
    public @ResponseBody
    ResponseEntity<?> cancelStudentAttendance(@PathVariable String netId,
                                              @PathVariable Timestamp start, @PathVariable Timestamp end) {
        return lectureScheduleService.cancelStudentAttendance(netId, start, end);
    }

    @GetMapping(path = "/lectureSchedules/{netId}")
    public @ResponseBody
    List<Lecture> getStudentSchedule(@PathVariable String netId){
        return lectureScheduleService.getStudentSchedule(netId);
    }

    @GetMapping(path = "/lectureSchedules/students/{lectureId}")
    public @ResponseBody
    List<Object []> getStudentsInLecture(@PathVariable Long lectureId){
        return lectureScheduleService.getStudentsInLecture(lectureId);
    }

    @DeleteMapping(path = "/lectureSchedules/remove/{netId}/{lectureId}")
    public @ResponseBody
    ResponseEntity<?> removeStudentFromLecture(@PathVariable String netId,
                                              @PathVariable Long lectureId) {
        return lectureScheduleService.removeStudentFromLecture(netId, lectureId);
    }

}
