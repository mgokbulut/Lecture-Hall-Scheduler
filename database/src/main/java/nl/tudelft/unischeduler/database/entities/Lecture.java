package nl.tudelft.unischeduler.database.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lecture", schema = "schedulingDB")
public class Lecture {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToMany(mappedBy = "lectures")
    //@JsonBackReference//
    private Set<Schedule> schedules;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    //@JsonManagedReference//
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonManagedReference//courses works
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher", nullable = false)
    @JsonManagedReference//
    private User teacher;

    @Column(name = "start_time_date", nullable = false)
    private Timestamp startTimeDate;

    @Column(name = "duration", nullable = false)
    private Time duration;

    @Column(name = "moved_online", nullable = false)
    private boolean movedOnline;

    /**
     * This method initialises the lecture object.
     */
    public Lecture() {

    }

    public Lecture(Long id, Set<Schedule> schedules, Classroom classroom, Course course, User teacher, Timestamp startTimeDate, Time duration, boolean movedOnline) {
        this.id = id;
        this.schedules = schedules;
        this.classroom = classroom;
        this.course = course;
        this.teacher = teacher;
        this.startTimeDate = startTimeDate;
        this.duration = duration;
        this.movedOnline = movedOnline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Timestamp getStartTimeDate() {
        return startTimeDate;
    }

    public void setStartTimeDate(Timestamp startTimeDate) {
        this.startTimeDate = startTimeDate;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public boolean isMovedOnline() {
        return movedOnline;
    }

    public void setMovedOnline(boolean movedOnline) {
        this.movedOnline = movedOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lecture)) return false;
        Lecture lecture = (Lecture) o;
        return id.equals(lecture.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, schedules, classroom, course, teacher, startTimeDate, duration, movedOnline);
    }
}
