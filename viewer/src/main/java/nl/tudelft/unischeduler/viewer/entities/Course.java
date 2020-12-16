package nl.tudelft.unischeduler.viewer.entities;

import java.util.Objects;
import java.util.Set;

public class Course {
    private Long id;
    private String name;
    private Set<User> students;
    private Set<User> teachers;
    private Set<Lecture> lectures;

    public Course(Long id, String name, Set<User> students, Set<User> teachers, Set<Lecture> lectures) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.teachers = teachers;
        this.lectures = lectures;
    }

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Set<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<User> teachers) {
        this.teachers = teachers;
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
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id) &&
                Objects.equals(name, course.name) &&
                Objects.equals(students, course.students) &&
                Objects.equals(teachers, course.teachers) &&
                Objects.equals(lectures, course.lectures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students, teachers, lectures);
    }
}
