package nl.tudelft.unischeduler.database.LectureSchedule;

import javax.persistence.*;
import java.util.Objects;

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

    public LectureSchedule() {

    }

    /**
     * TODO a.
     *
     * @param lecture_id a
     * @param schedule_id a
     */
    public LectureSchedule(Long lecture_id, Long schedule_id) {
        this.lectureId = lecture_id;
        this.scheduleId = schedule_id;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lecture_id) {
        this.lectureId = lecture_id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long schedule_id) {
        this.scheduleId = schedule_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LectureSchedule)) return false;
        LectureSchedule that = (LectureSchedule) o;
        return lectureId.equals(that.lectureId) &&
                scheduleId.equals(that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectureId, scheduleId);
    }
}
