package nl.tudelft.unischeduler.database.usercourse;

import java.io.Serializable;
import java.util.Objects;

public class UserCourseId implements Serializable {
    private String netId;
    private Long courseId;

    //the only reason this is here is because of PMD rule violation
    public static final long serialVersionUID = 4328743;

    /**
     * This method initialises the UserCourseId object.
     */
    public UserCourseId(){

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

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCourseId)) {
            return false;
        }
        UserCourseId that = (UserCourseId) o;
        return netId.equals(that.netId)
                && courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netId, courseId);
    }
}
