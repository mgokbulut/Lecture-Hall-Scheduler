package nl.tudelft.unischeduler.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "classroom", schema = "schedulingDB")
public class Classroom {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "full_capacity")
    private int fullCapacity;
    @Column(name = "name")
    private String name;
    @Column(name = "building_name")
    private String buildingName;
    @Column(name = "floor")
    private int floor;

    public Classroom(int id, int fullCapacity, String name, String buildingName, int floor) {
        this.id = id;
        this.fullCapacity = fullCapacity;
        this.name = name;
        this.buildingName = buildingName;
        this.floor = floor;
    }

    /***
     * <p>This method initialises the classroom object.</p>
     */
    public Classroom() {

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getFullCapacity() { return fullCapacity; }

    public void setFullCapacity(int fullCapacity) { this.fullCapacity = fullCapacity; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBuildingName() { return buildingName; }

    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }

    public int getFloor() { return floor; }

    public void setFloor(int floor) { this.floor = floor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return id == classroom.id &&
                fullCapacity == classroom.fullCapacity &&
                floor == classroom.floor &&
                Objects.equals(name, classroom.name) &&
                Objects.equals(buildingName, classroom.buildingName);
    }

    @Override
    public int hashCode() { return Objects.hash(id, fullCapacity, name, buildingName, floor); }
}
