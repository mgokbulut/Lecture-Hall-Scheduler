package nl.tudelft.unischeduler.schedulegenerate.entities;

import javax.persistence.Entity;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class Lecture {

  private int id;
  private int attendance;
  private Timestamp startTime;
  private Time duration;
  private Room room;

  /**
   * constructor for Lecture.
   *
   * @param id the unique identifier representing this lecture
   * @param attendance the number of students currently assigned to this lecture
   * @param startTime the startTime of the lecture, day is always specified,
   *                  time of day can be set later
   * @param duration the duration of the lecture
   */
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

//  @Override
  public int compareTo(Object o) {
    return this.startTime.compareTo(((Lecture) o).startTime);
  }
}
