package nl.tudelft.unischeduler.viewer.entities;

import java.util.Objects;
import java.util.Set;

public class Classroom {
    private Long id;
    private int capacity;
    private String name;
    private String buildingName;
    private int floor;
    private Set<Lecture> lectures;

    public Classroom(Long id, int capacity, String name, String buildingName, int floor,
                     Set<Lecture> lectures) {
        this.id = id;
        this.capacity = capacity;
        this.name = name;
        this.buildingName = buildingName;
        this.floor = floor;
        this.lectures = lectures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
        Classroom classroom = (Classroom) o;
        return capacity == classroom.capacity &&
                floor == classroom.floor &&
                id.equals(classroom.id) &&
                Objects.equals(name, classroom.name) &&
                Objects.equals(buildingName, classroom.buildingName) &&
                Objects.equals(lectures, classroom.lectures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, name, buildingName, floor, lectures);
    }
}
