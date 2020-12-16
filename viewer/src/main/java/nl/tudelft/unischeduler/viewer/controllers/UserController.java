package nl.tudelft.unischeduler.viewer.controllers;

import nl.tudelft.unischeduler.viewer.entities.Lecture;
import nl.tudelft.unischeduler.viewer.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class UserController {

    public UserController() {

    }

    @GetMapping()
    public Lecture getLecture(int lectureId) {
        //TODO: add a call to the database module to retrieve the correct lecture.

        return null;
    }

    @GetMapping()
    public Lecture[] getLecturesInCourse(int courseId) {
        //TODO: add a call to the database module to retrieve the correct lectures.

        return null;
    }

    @GetMapping()
    public Lecture[] getPossibleLectures(String netId) {
        //TODO: add a call to the database module to retrieve the correct lectures.

        return null;
    }

    @GetMapping("/lectureSchedules/{netId}")
    public Lecture[] getStudentSchedule(@PathVariable String netId) {
        //TODO: add a call to the database module to retrieve the correct lectures.

        return null;
    }

    @GetMapping()
    public Lecture getTeacherSchedule(String netId) {
        //TODO: add a call to the database module to retrieve the correct lectures.

        return null;
    }

    @GetMapping()
    public User[] getStudentsForLecture(int lectureId) {
        //TODO: add a call to the database module to retrieve the correct students.

        return null;
    }

}
