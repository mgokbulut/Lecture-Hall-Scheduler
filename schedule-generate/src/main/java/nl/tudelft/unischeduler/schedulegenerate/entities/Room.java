package nl.tudelft.unischeduler.schedulegenerate.entities;

import java.util.Objects;
import javax.persistence.Entity;

@Entity
public class Room implements Comparable<Room> {
    private int id;
    private int capacity;
    private String name;


    /**
     * constructor for Room.
     *
     * @param id the unique identifier representing this room
     * @param capacity the maximum capacity of the room,
     *                 before social distancing guidelines are enforced
     * @param name the name of the room
     */
    public Room(int id, int capacity, String name) {
        this.id = id;
        this.capacity = capacity;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return getId() == room.getId()
            && getCapacity() == room.getCapacity()
            && Objects.equals(getName(), room.getName());
    }

    @Override // we want them ordered by capacity
    public int compareTo(Room otherRoom) {
        return Integer.compare(getCapacity(), otherRoom.getCapacity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCapacity(), getName());
    }
}
