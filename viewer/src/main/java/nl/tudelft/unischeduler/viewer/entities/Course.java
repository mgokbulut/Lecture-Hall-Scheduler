package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
public class Course {
    private Long id;
    private String name;
    private Set<User> students;
    private Set<User> teachers;
    private Set<Lecture> lectures;


    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
