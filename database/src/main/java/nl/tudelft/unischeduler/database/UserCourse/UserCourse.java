package nl.tudelft.unischeduler.database.UserCourse;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_course", schema = "schedulingDB")
@IdClass(UserCourseId.class)
public class UserCourse {

    @Id
    @Column(name = "user_id")
    private String netId;

    @Id
    @Column(name = "course_id")
    private Long courseId;

    public UserCourse() {

    }

    /**
     * TODO a.
     *
     * @param netId a
     * @param courseId a
     */
    public UserCourse(String netId, Long courseId) {
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
        if (this == o) return true;
        if (!(o instanceof UserCourse)) return false;
        UserCourse that = (UserCourse) o;
        return netId.equals(that.netId) &&
                courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netId, courseId);
    }
}
