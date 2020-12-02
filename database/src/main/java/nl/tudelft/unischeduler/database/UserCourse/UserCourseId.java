package nl.tudelft.unischeduler.database.UserCourse;

import java.io.Serializable;
import java.util.Objects;

public class UserCourseId implements Serializable {
    private String netId;
    private Long courseId;

    public UserCourseId(){

    }

    /**
     * TODO a.
     *
     * @param netId a
     * @param courseId a
     */
    public UserCourseId(String netId, Long courseId) {
        this.netId = netId;
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCourseId)) return false;
        UserCourseId that = (UserCourseId) o;
        return netId.equals(that.netId) &&
                courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netId, courseId);
    }
}
