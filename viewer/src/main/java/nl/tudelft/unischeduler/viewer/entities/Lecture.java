package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Lecture {
    private Long id;
    Classroom classroom;
    Course course;
    private User teacher;
    private Timestamp start;
    private Time duration;
    private boolean movedOnline;

    public Lecture(Long id, Course course, User teacher,
                   Timestamp start, Time duration, boolean movedOnline) {
        this.id = id;
        this.course = course;
        this.teacher = teacher;
        this.start = start;
        this.duration = duration;
        this.movedOnline = movedOnline;
    }
}
