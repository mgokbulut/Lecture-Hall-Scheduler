package nl.tudelft.unischeduler.utilentities;

import java.sql.Time;
import java.sql.Timestamp;

public class Lecture implements Comparable<Lecture> {

    private int id;
    private int attendance;
    private Timestamp startTime;
    private Time duration;
    private Room room;
    private boolean isOnline;
    private int year;

    /**
     * constructor for Lecture.
     *
     * @param id the unique identifier representing this lecture
     * @param attendance the number of students currently assigned to this lecture
     * @param startTime the startTime of the lecture, day is always specified,
     *                  time of day can be set later
     * @param duration the duration of the lecture
     */
    public Lecture(int id, int attendance, Timestamp startTime,
                   Time duration, boolean isOnline, int year) {
        this.id = id;
        this.attendance = attendance;
        this.startTime = startTime;
        this.duration = duration;
        this.isOnline = isOnline;
        this.year = year;
    }

    // custom constructor for when a user is querying for a lecture
    public Lecture(int id) {
        this.id = id;
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

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Override method that compares based on the end time of the lecture;
    // this is needed for the generator algorithm.
    @Override
    public int compareTo(Lecture otherLecture) {
        Timestamp t1 = new Timestamp(this.startTime.getTime() + duration.getTime());
        Timestamp t2 = new Timestamp(otherLecture.getStartTime().getTime()
            + otherLecture.getDuration().getTime());
        return t1.compareTo(t2);
    }
}
