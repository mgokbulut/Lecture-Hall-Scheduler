package nl.tudelft.unischeduler.database.classroom;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "classroom", schema = "schedulingDB")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_capacity", nullable = false)
    private int fullCapacity;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "building_name", nullable = false)
    private String buildingName;
    @Column(name = "floor", nullable = false)
    private int floor;

    /**
     * This method initialises the classroom object.
     */
    public Classroom() {

    }

    /**
     * This method initialises the userCourse object with specified parameters.
     *
     * @param fullCapacity a
     * @param name a
     * @param buildingName a
     * @param floor a
     */
    public Classroom(int fullCapacity,
                     String name, String buildingName,
                     int floor) {
        this.fullCapacity = fullCapacity;
        this.name = name;
        this.buildingName = buildingName;
        this.floor = floor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFullCapacity() {
        return fullCapacity;
    }

    public void setFullCapacity(int fullCapacity) {
        this.fullCapacity = fullCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classroom)) {
            return false;
        }
        Classroom classroom = (Classroom) o;
        return id.equals(classroom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullCapacity, name, buildingName, floor);
    }
}
