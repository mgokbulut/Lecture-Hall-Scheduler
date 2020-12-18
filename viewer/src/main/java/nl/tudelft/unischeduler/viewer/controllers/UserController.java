package nl.tudelft.unischeduler.viewer.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.viewer.entities.Lecture;
import nl.tudelft.unischeduler.viewer.entities.User;
import nl.tudelft.unischeduler.viewer.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@AllArgsConstructor
public class UserController {

    @Autowired
    DatabaseService dbService;

    @GetMapping("/lectureSchedules/course/{courseId}")
    public @ResponseBody
    ResponseEntity<Lecture[]> getLecturesInCourse(@PathVariable int courseId) {
        return dbService.getLecturesInCourse(courseId);
    }

    @GetMapping("/userCourseService/possibleLectures/{netId}")
    public @ResponseBody
    ResponseEntity<Lecture[]> getPossibleLectures(@PathVariable String netId) {
        return dbService.getPossibleLectures(netId);
    }

    @GetMapping(path = "/lectureSchedules/{netId}")
    public @ResponseBody
    ResponseEntity<Lecture[]> getStudentSchedule(@PathVariable String netId) {
        return dbService.getStudentSchedule(netId);
    }

    @GetMapping("/lectureSchedules/teacher/{netId}")
    public @ResponseBody
    ResponseEntity<Lecture[]> getTeacherSchedule(@PathVariable String netId) {
        return dbService.getTeacherSchedule(netId);
    }

    @GetMapping("/lectureSchedules/studentsLecture/{lectureId}")
    public @ResponseBody
    ResponseEntity<User[]> getStudentsInLecture(@PathVariable int lectureId) {
        return dbService.getStudentsInLecture(lectureId);
    }
}
