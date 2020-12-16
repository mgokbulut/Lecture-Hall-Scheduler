package nl.tudelft.unischeduler.viewer.entities;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

public class User {
    private String netId;
    private String type;
    private Date lastTimeOnCampus;
    private Schedule schedule;
    private Set<Lecture> lectures;
    private Set<Course> courses;

    public User(String netId, String type, Date lastTimeOnCampus, Schedule schedule) {
        this.netId = netId;
        this.type = type;
        this.lastTimeOnCampus = lastTimeOnCampus;
        this.schedule = schedule;
    }

    public User(String netId, String type, Date lastTimeOnCampus, Schedule schedule,
                Set<Lecture> lectures, Set<Course> courses) {
        this.netId = netId;
        this.type = type;
        this.lastTimeOnCampus = lastTimeOnCampus;
        this.schedule = schedule;
        this.lectures = lectures;
        this.courses = courses;
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

    public Date getLastTimeOnCampus() {
        return lastTimeOnCampus;
    }

    public void setLastTimeOnCampus(Date lastTimeOnCampus) {
        this.lastTimeOnCampus = lastTimeOnCampus;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return netId.equals(user.netId) &&
                Objects.equals(type, user.type) &&
                Objects.equals(lastTimeOnCampus, user.lastTimeOnCampus) &&
                Objects.equals(schedule, user.schedule) &&
                Objects.equals(lectures, user.lectures) &&
                Objects.equals(courses, user.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netId, type, lastTimeOnCampus, schedule, lectures, courses);
    }
}
