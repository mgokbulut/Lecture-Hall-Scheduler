package nl.tudelft.unischeduler.schedulegenerate.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Course {

  private Long id;
  private String name;

  private Set<Student> students;

  private Set<Lecture> lectures;

  /**
   * This method initialises the course object.
   */
  public Course() {

  }

  /**
   * TODO a.
   *
   * @param id a
   * @param name a
   * @param students a
   * @param lectures a
   */
  public Course(Long id, String name, Set<Student> students, Set<Lecture> lectures) {
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

  public Set<Student> getStudents() {
    return students;
  }

  public void setStudents(Set<Student> students) {
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
    return Objects.hash(id, name, students, lectures);
  }
}
