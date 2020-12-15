package nl.tudelft.unischeduler.database.lecture;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "lecture", schema = "schedulingDB")
public class Lecture {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "classroom_id")
    private Long classroom;

    @Column(name = "course_id")
    private Long course;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "start_time_date", nullable = false)
    private Timestamp startTimeDate;

    @Column(name = "duration", nullable = false)
    private Time duration;

    @Column(name = "moved_online", nullable = false)
    private boolean movedOnline;

    /**
     * This method initialises the Lecture object.
     */
    public Lecture() {

    }

    /**
     * This method initialises the Lecture object with specified parameters.
     *
     * @param classroom classroom ID
     * @param course course ID
     * @param teacher user ID of the teacher
     * @param startTimeDate start time of the lecture
     * @param duration duration of the lecture
     * @param movedOnline is the lecture moved online
     */
    public Lecture(Long classroom, Long course, String teacher,
                   Timestamp startTimeDate, Time duration, boolean movedOnline) {
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

    public Long getClassroom() {
        return classroom;
    }

    public void setClassroom(Long classroom) {
        this.classroom = classroom;
    }

    public Long getCourse() {
        return course;
    }

    public void setCourse(Long course) {
        this.course = course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lecture)) {
            return false;
        }
        Lecture lecture = (Lecture) o;
        return id.equals(lecture.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classroom,
                course, teacher, startTimeDate,
                duration, movedOnline);
    }
}
