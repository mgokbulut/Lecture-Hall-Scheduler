package nl.tudelft.unischeduler.viewer.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import nl.tudelft.unischeduler.viewer.entities.Lecture;
import nl.tudelft.unischeduler.viewer.entities.User;
import nl.tudelft.unischeduler.viewer.services.DatabaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Data
@AllArgsConstructor
public class UserController {

    @NonNull DatabaseService dbService;

    @GetMapping("/lectureSchedules/course/{courseId}")
    public Lecture[] getLecturesInCourse(@PathVariable int courseId) {
        return dbService.getLecturesInCourse(courseId);
    }

    @GetMapping("/userCourseService/possibleLectures/{netId}")
    public Lecture[] getPossibleLectures(@PathVariable String netId) {
        return dbService.getPossibleLectures(netId);
    }

    @GetMapping("/lectureSchedules/{netId}")
    public Lecture[] getStudentSchedule(@PathVariable String netId) {
        return dbService.getStudentSchedule(netId);
    }

    @GetMapping("/lectureSchedules/teacher/{netId}")
    public Lecture[] getTeacherSchedule(@PathVariable String netId) {
        return dbService.getTeacherSchedule(netId);
    }

    @GetMapping("/lectureSchedules/studentsLecture/{lectureId}")
    public User[] getStudentsInLecture(@PathVariable int lectureId) {
        return dbService.getStudentsInLecture(lectureId);
    }

}
