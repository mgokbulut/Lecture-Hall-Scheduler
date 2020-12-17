package nl.tudelft.unischeduler.database.classroom;

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
}
