package nl.tudelft.unischeduler.database.LectureSchedule;

import java.io.Serializable;
import java.util.Objects;

public class LectureScheduleId implements Serializable {
    private Long lectureId;
    private Long scheduleId;

    //the only reason this is here is because of PMD rule violation
    public static final long serialVersionUID = 4328744;


    public LectureScheduleId() {

    }

    /**
     * TODO a.
     *
     * @param lecture_id a
     * @param schedule_id a
     */
    public LectureScheduleId(Long lecture_id, Long schedule_id) {
        this.lectureId = lecture_id;
        this.scheduleId = schedule_id;
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
        if (this == o) return true;
        if (!(o instanceof LectureScheduleId)) return false;
        LectureScheduleId that = (LectureScheduleId) o;
        return lectureId.equals(that.lectureId) &&
                scheduleId.equals(that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectureId, scheduleId);
    }
}
