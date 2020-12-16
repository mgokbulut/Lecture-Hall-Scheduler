package nl.tudelft.unischeduler.rules.controllers;

import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.entities.Student;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.web.bind.annotation.GetMapping;

public class DatabaseController {

    private RulesParser parser;
    private RulesModule module;

    public DatabaseController(RulesParser parser, RulesModule module) {
        this.parser = parser;
        this.module = module;
    }

    public RulesParser getParser() {
        return parser;
    }

    public void setParser(RulesParser parser) {
        this.parser = parser;
    }

    public RulesModule getModule() {
        return module;
    }

    public void setModule(RulesModule module) {
        this.module = module;
    }

    @GetMapping("/users/{netId}")
    public Student getStudent(String netId) {
        //TODO: add a call to the database module to retrieve the correct student.
        Student stu = new Student(netId, true, true);
        return stu;
    }

    @GetMapping("/lectureSchedules/students/{lectureId}")
    public Student[] getStudentsInLecture(int lectureId) {
        //TODO: add a call to the database module to retrieve the correct students.
        Student[] stus = new Student[1];
        return stus;
    }

    @GetMapping("/lectureSchedules/remove/{netId}/{lectureId}")
    public boolean removeStudentFromLecture(String netId, int lectureId) {
        //TODO: add a call to the database module to remove the given student from the given lecture
        return true;
    }

    @GetMapping("/classrooms/{classroomId}")
    public Room getClassroom(int classroomId) {
        //TODO: add a call to the database module to remove the given student from the given lecture
        return null;
    }

    @GetMapping("/lectures/courses")
    public Lecture[] getLectures() {
        //TODO: add a call to the database module to return an array of all lectures
        return new Lecture[1];

    }

    @GetMapping("/lectures/setClassroomToEmpty/{lectureId}")
    public boolean removeRoomFromLecture(int lectureId) {
        //TODO: add a call to the database module to set the room
        // of the given lecture to the placeholder "unscheduled" room
        return true;
    }

    @GetMapping("/lectureSchedules/remove/{lectureId}")
    public boolean removeLectureFromSchedule(int lectureId) {
        //TODO: add a call to the database to remove all occurrences of the given lecture
        // from the lecture_schedule table
        return true;
    }



}
