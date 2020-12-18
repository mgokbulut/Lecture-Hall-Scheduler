package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classroom {
    private Long id;
    private int capacity;
    private String name;
    private String buildingName;
    private int floor;
    private Set<Lecture> lectures;

    /**
     * constructor for classroom in the case that lectures are unavailable.
     *
     * @param id
     * @param capacity
     * @param name
     * @param buildingName
     * @param floor
     */
    public Classroom(Long id, int capacity, String name, String buildingName, int floor) {
        this.id = id;
        this.capacity = capacity;
        this.name = name;
        this.buildingName = buildingName;
        this.floor = floor;
    }
}
