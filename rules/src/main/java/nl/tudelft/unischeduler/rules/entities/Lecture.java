package nl.tudelft.unischeduler.rules.entities;

import java.sql.Timestamp;
import java.sql.Time;

public class Lecture {

    private int id;
    private int attendance;
    private Timestamp startTime;
    private Time duration;
    private Room room;

    public Lecture(int id, int attendance, Timestamp startTime, Time duration) {
        this.id = id;
        this.attendance = attendance;
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
