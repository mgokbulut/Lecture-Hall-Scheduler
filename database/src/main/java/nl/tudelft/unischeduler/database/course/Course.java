package nl.tudelft.unischeduler.database.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table(name = "course", schema = "schedulingDB")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "year")
    private int year;

    /**
     * This method initialises the course object.
     */
    public Course() {

    }

    /**
     * This method initialises the userCourse object with specified parameters, Id is autogenerated.
     *
     * @param name name of the course
     * @param year year this course teaches
     */
    public Course(String name, int year) {
        this.name = name;
        this.year = year;
    }
}