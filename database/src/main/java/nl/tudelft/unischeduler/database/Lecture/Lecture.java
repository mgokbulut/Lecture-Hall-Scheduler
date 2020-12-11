package nl.tudelft.unischeduler.database.Lecture;


import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "lecture", schema = "schedulingDB")
public class Lecture {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
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
     * This method initialises the lecture object.
     */
    public Lecture() {

    }

    /**
     * TODO a.
     *
     * @param id a
     * @param classroom a
     * @param course a
     * @param teacher a
     * @param startTimeDate a
     * @param duration a
     * @param movedOnline a
     */
    public Lecture(Long id, Long classroom, Long course, String teacher, Timestamp startTimeDate, Time duration, boolean movedOnline) {
        this.id = id;
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
