package nl.tudelft.unischeduler.authentication.utilentities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import nl.tudelft.unischeduler.authentication.user.User;

@Entity
public class Course {

    private Long id;
    private String name;

    private Set<User> students;

    private int year;

    private int classesPerWeek;

    /**
     * This method initialises the course object.
     */
    public Course() {

    }

    /**
     * Constructors for the Course class.
     *
     * @param id a
     * @param name a
     * @param students a
     */
    public Course(Long id, String name, Set<User> students, int year, int classesPerWeek) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.year = year;
        this.classesPerWeek = classesPerWeek;
    }

    /**
     * Constructor with less parameters for when
     * we are adding a new course.
     *
     * @param name the name of the course
     * @param year the year of the course in the study programme
     * @param classesPerWeek how many lectures per week
     */
    public Course(String name, int year, int classesPerWeek) {
        this.name = name;
        this.year = year;
        this.students = new HashSet<User>();
        this.classesPerWeek = classesPerWeek;
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

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return this.year;
    }

    public int getClassesPerWeek() {
        return this.classesPerWeek;
    }

    public void setClassesPerWeek(int classesPerWeek) {
        this.classesPerWeek = classesPerWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students);
    }
}
