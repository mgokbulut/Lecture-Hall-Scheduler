package nl.tudelft.unischeduler.database.usercourse;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserCourseId implements Serializable {
    private String netId;
    private Long courseId;

    //the only reason this is here is because of PMD rule violation
    public static final long serialVersionUID = 4328743;

    /**
     * This method initialises the UserCourseId object.
     */
    public UserCourseId() {

    }

    /**
     * This method initialises the UserCourseId object with specified parameters.
     *
     * @param netId user ID
     * @param courseId  course ID
     */
    public UserCourseId(String netId, Long courseId) {
        this.netId = netId;
        this.courseId = courseId;
    }

}
