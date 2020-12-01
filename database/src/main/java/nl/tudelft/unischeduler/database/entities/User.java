package nl.tudelft.unischeduler.database.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "app_user", schema = "schedulingDB")
public class User {
    @Id
    @Column(name = "net_id", nullable = false, unique = true)
    private String netId;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "interested", nullable = false)
    private boolean interested;
    @Column(name = "last_time_on_campus", nullable = false)
    private Date lastTimeOnCampus;

    @OneToOne(mappedBy = "user")
    @JsonBackReference//
    private Schedule schedule;

    @ManyToMany
    @JoinTable(name = "user_course",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    //@JsonManagedReference//courses works
    @JsonBackReference //users works
    private Set<Course> courses;

    @OneToMany(mappedBy = "teacher")
    @JsonBackReference//
    private Set<Lecture> lectures;

    /**
     * This method initialises the user object.
     */
    public User() {

    }

    public User(String netId, String type, boolean interested, Date lastTimeOnCampus, Schedule schedule, Set<Course> courses, Set<Lecture> lectures) {
        this.netId = netId;
        this.type = type;
        this.interested = interested;
        this.lastTimeOnCampus = lastTimeOnCampus;
        this.schedule = schedule;
        this.courses = courses;
        this.lectures = lectures;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return  netId.equals(user.netId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netId, type, interested, lastTimeOnCampus, schedule, courses, lectures);
    }
}