package nl.tudelft.unischeduler.database.lectureschedule;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
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
}
