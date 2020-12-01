package nl.tudelft.unischeduler.database.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course", schema = "schedulingDB")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "courses")
    //@JsonBackReference//courses works
    @JsonManagedReference //users works
    private Set<User> students;

    @OneToMany(mappedBy = "course")
    @JsonBackReference //courses works
    private Set<Lecture> lectures;

    /**
     * This method initialises the course object.
     */
    public Course() {

    }

    public Course(Long id, String name, Set<User> students, Set<Lecture> lectures) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.lectures = lectures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students, lectures);
    }
}
