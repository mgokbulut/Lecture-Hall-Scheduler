package nl.tudelft.unischeduler.database.lectureschedule;

import java.io.Serializable;
import java.util.Objects;

public class LectureScheduleId implements Serializable {
    private Long lectureId;
    private Long scheduleId;

    //the only reason this is here is because of PMD rule violation
    public static final long serialVersionUID = 4328744;

    /**
     * This method initialises the LectureScheduleId object.
     */
    public LectureScheduleId() {

    }

    /**
     * This method initialises the LectureScheduleId object with specified parameters.
     *
     * @param lectureId lecture ID
     * @param scheduleId schedule ID
     */
    public LectureScheduleId(Long lectureId, Long scheduleId) {
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
        if (!(o instanceof LectureScheduleId)) {
            return false;
        }
        LectureScheduleId that = (LectureScheduleId) o;
        return lectureId.equals(that.lectureId)
                && scheduleId.equals(that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectureId, scheduleId);
    }
}
