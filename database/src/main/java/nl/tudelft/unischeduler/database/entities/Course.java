package nl.tudelft.unischeduler.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "course", schema = "schedulingDB")
public class Course {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "name")
    private String name;

    public Course(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /***
     * <p>This method initialises the course object.</p>
     */
    public Course() {

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name); }
}
