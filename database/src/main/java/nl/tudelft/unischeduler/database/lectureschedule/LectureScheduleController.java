package nl.tudelft.unischeduler.database.lectureschedule;

import java.sql.Timestamp;
import java.util.List;
import nl.tudelft.unischeduler.database.lecture.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @DeleteMapping(path = "/lectureSchedules/remove/{netId}/{start}/{end}")
    public @ResponseBody
    ResponseEntity<?> cancelStudentAttendance(@PathVariable String netId,
                                              @PathVariable Timestamp start,
                                              @PathVariable Timestamp end) {
        return lectureScheduleService.cancelStudentAttendance(netId, start, end);
    }

    @GetMapping(path = "/lectureSchedules/student/{netId}")
    public @ResponseBody
    List<Object []> getStudentSchedule(@PathVariable String netId) {
        return lectureScheduleService.getStudentSchedule(netId);
    }

    @GetMapping(path = "/lectureSchedules/teacher/{netId}")
    public @ResponseBody
    List<Object []> getTeacherSchedule(@PathVariable String netId) {
        return lectureScheduleService.getTeacherSchedule(netId);
    }

    //TODO: test again
    @GetMapping(path = "/lectureSchedules/studentsLecture/{lectureId}")
    public @ResponseBody
    List<Object []> getStudentsInLecture(@PathVariable Long lectureId) {
        return lectureScheduleService.getStudentsInLecture(lectureId);
    }

    //TODO: test again
    @GetMapping(path = "/lectureSchedules/course/{courseId}")
    public @ResponseBody
    List<Object []> getAllLecturesInCourse(@PathVariable Long courseId) {
        return lectureScheduleService.getAllLecturesInCourse(courseId);
    }

    @DeleteMapping(path = "/lectureSchedules/remove/{netId}/{lectureId}")
    public @ResponseBody
    ResponseEntity<?> removeStudentFromLecture(@PathVariable String netId,
                                              @PathVariable Long lectureId) {
        return lectureScheduleService.removeStudentFromLecture(netId, lectureId);
    }

}
