package nl.tudelft.unischeduler.schedulegenerate.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;

//@Entity
public class Student implements Comparable<Student> {

    private String netId;

    private String type;

    private boolean interested;

    private Date lastTimeOnCampus;

    // private Schedule schedule;

    // private Set<Course> courses;

    // private Set<Lecture> lectures;

    /**
     * This method initialises the user object.
     */
    public Student() {
        this.netId = "";
        this.lastTimeOnCampus = new Date(0);
    }

    /**
     * TODO a.
     *
     * @param netId a
     * @param type a
     * @param interested a
     * @param lastTimeOnCampus a
     */
    public Student(String netId, String type,
                   boolean interested, Date lastTimeOnCampus) {
        this.netId = netId;
        this.type = type;
        this.interested = interested;
        this.lastTimeOnCampus = lastTimeOnCampus;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isInterested() {
        return interested;
    }

    public void setInterested(boolean interested) {
        this.interested = interested;
    }

    public Date getLastTimeOnCampus() {
        return lastTimeOnCampus;
    }

    public void setLastTimeOnCampus(Date lastTimeOnCampus) {
        this.lastTimeOnCampus = lastTimeOnCampus;
    }

    //  public Schedule getSchedule() {
    //    return schedule;
    //  }
    //
    //  public void setSchedule(Schedule schedule) {
    //    this.schedule = schedule;
    //  }
    //
    //  public Set<Course> getCourses() {
    //    return courses;
    //  }
    //
    //  public void setCourses(Set<Course> courses) {
    //    this.courses = courses;
    //  }
    //
    //  public Set<Lecture> getLectures() {
    //    return lectures;
    //  }
    //
    //  public void setLectures(Set<Lecture> lectures) {
    //    this.lectures = lectures;
    //  }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student user = (Student) o;
        return  netId.equals(user.netId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netId, type, interested, lastTimeOnCampus);
    }

    // custom behavior where students are compared based on the last time they were on campus
    @Override
    public int compareTo(Student otherStudent) {
        return lastTimeOnCampus.compareTo(otherStudent.getLastTimeOnCampus());
    }
}
