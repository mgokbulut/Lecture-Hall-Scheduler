package nl.tudelft.unischeduler.database.usercourse;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@Table(name = "user_course", schema = "schedulingDB")
@IdClass(UserCourseId.class)
public class UserCourse {

    @Id
    @Column(name = "user_id")
    private String netId;

    @Id
    @Column(name = "course_id")
    private Long courseId;

    /**
     * This method initialises the UserCourse object.
     */
    public UserCourse() {

    }
}
