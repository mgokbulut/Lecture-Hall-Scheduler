package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
public class Classroom {
    private Long id;
    private int capacity;
    private String name;
    private String buildingName;
    private int floor;
    private Set<Lecture> lectures;

    public Classroom(Long id, int capacity, String name, String buildingName, int floor) {
        this.id = id;
        this.capacity = capacity;
        this.name = name;
        this.buildingName = buildingName;
        this.floor = floor;
    }
}
