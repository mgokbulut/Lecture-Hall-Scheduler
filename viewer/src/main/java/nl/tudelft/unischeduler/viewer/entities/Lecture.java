package nl.tudelft.unischeduler.viewer.entities;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Lecture {
    private Long id;
    Classroom classroom;
    Course course;
    private User teacher;
    private Timestamp start;
    private Time duration;
    private boolean movedOnline;

    public Lecture(Long id, Classroom classroom, Course course, User teacher,
                   Timestamp start, Time duration, boolean movedOnline) {
        this.id = id;
        this.classroom = classroom;
        this.course = course;
        this.teacher = teacher;
        this.start = start;
        this.duration = duration;
        this.movedOnline = movedOnline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public boolean isMovedOnline() {
        return movedOnline;
    }

    public void setMovedOnline(boolean movedOnline) {
        this.movedOnline = movedOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return movedOnline == lecture.movedOnline &&
                id.equals(lecture.id) &&
                Objects.equals(classroom, lecture.classroom) &&
                Objects.equals(course, lecture.course) &&
                Objects.equals(teacher, lecture.teacher) &&
                Objects.equals(start, lecture.start) &&
                Objects.equals(duration, lecture.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classroom, course, teacher, start, duration, movedOnline);
    }
}
