package nl.tudelft.unischeduler.rules.entities;

public class Room {

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
}
