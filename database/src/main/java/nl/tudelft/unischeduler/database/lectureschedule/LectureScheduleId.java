package nl.tudelft.unischeduler.database.lectureschedule;

import java.io.Serializable;
import java.util.Objects;
import lombok.Data;

@Data
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
}
