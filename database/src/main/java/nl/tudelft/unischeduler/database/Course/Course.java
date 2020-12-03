package nl.tudelft.unischeduler.database.Course;

import nl.tudelft.unischeduler.database.Lecture.Lecture;
import nl.tudelft.unischeduler.database.User.User;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "course", schema = "schedulingDB")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "year")
    private int year;

//    @ManyToMany(mappedBy = "courses")
//    //@JsonBackReference//courses works
//    @JsonManagedReference //users works
//    private Set<User> students;

//    @OneToMany(mappedBy = "course")
//    @JsonBackReference //courses works
//    private Set<Lecture> lectures;

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
     * @param year a
     */
    public Course(Long id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
        return Objects.hash(id, name, year);
    }

    @Override
    public String toString(){
        return String.format("{\"id\":%d,\"name\":%s,\"year\":%d}", id, name, year);
    }
}
