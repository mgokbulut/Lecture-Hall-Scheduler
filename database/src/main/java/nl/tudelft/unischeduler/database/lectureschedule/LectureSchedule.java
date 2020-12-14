package nl.tudelft.unischeduler.database.lectureschedule;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name = "lecture_schedule", schema = "schedulingDB")
@IdClass(LectureScheduleId.class)
public class LectureSchedule {

    @Id
    @Column(name = "lecture_id")
    private Long lectureId;

    @Id
    @Column(name = "schedule_id")
    private Long scheduleId;

    /**
     * This method initialises the LectureSchedule object.
     */
    public LectureSchedule() {

    }

    /**
     * This method initialises the LectureSchedule object with specified parameters.
     *
     * @param lectureId lecture ID
     * @param scheduleId a schedule ID
     */
    public LectureSchedule(Long lectureId, Long scheduleId) {
        this.lectureId = lectureId;
        this.scheduleId = scheduleId;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LectureSchedule)) {
            return false;
        }
        LectureSchedule that = (LectureSchedule) o;
        return lectureId.equals(that.lectureId)
                && scheduleId.equals(that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectureId, scheduleId);
    }
}
