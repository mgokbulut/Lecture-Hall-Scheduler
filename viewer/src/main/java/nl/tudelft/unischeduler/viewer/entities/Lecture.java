package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {
    private Long id;
    Classroom classroom;
    Course course;
    private String teacher;
    private Timestamp start;
    private Time duration;
    private boolean movedOnline;

}
