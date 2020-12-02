package nl.tudelft.unischeduler.database.LectureSchedule;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

public class LectureScheduleId implements Serializable {
    private Long lectureId;
    private Long scheduleId;


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
