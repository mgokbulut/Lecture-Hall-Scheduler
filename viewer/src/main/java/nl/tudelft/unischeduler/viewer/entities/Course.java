package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Long id;
    private String name;
    private Set<User> students;
    private Set<User> teachers;
    private Set<Lecture> lectures;

    /**
     * constructor for course in the case that students, teachers
     * and lectures are unavailable.
     *
     * @param id
     * @param name
     */
    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
