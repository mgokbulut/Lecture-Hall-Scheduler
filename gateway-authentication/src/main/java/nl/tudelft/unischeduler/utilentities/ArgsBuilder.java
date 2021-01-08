package nl.tudelft.unischeduler.utilentities;

import java.time.Duration;
import java.util.Dictionary;
import lombok.Data;
import nl.tudelft.unischeduler.user.User;


@Data
public class ArgsBuilder implements Builder {

    private String courseName;
    private String netId;
    private String password;
    private String type;
    private long courseId;
    private int year;
    private int classesPerWeek;
    private int week;
    private Duration duration;

    private Dictionary argsDict;
    private Arguments args;

    public ArgsBuilder(Dictionary argsDict) {
        this.argsDict = argsDict;
        this.args = new Arguments();
    }

    public void reset() {
        this.args = new Arguments();
    }

    public void buildCourseId() {
        this.courseId = ((Long) argsDict.get("courseId"));
        args.setCourseId(courseId);
    }

    public void buildNetId() {
        this.netId = ((String) argsDict.get("netId"));
        args.setNetId(netId);
    }

    /**
     * retrieves the relevant information from the argument dictionary,
     * and creates a course object which is then applied to the arguments attribute.
     */
    public void buildCourse() {
        this.courseName = ((String) argsDict.get("courseName"));
        this.year = ((Integer) argsDict.get("year"));
        this.classesPerWeek = ((Integer) argsDict.get("classesPerWeek"));
        Course course = new Course(courseName, year, classesPerWeek);
        args.setCourse(course);
    }

    /**
     * retrieves the relevant information from the argument dictionary,
     * and creates a user object which is then applied to the arguments attribute.
     */
    public void buildUser() {
        buildNetId();
        this.password = ((String) argsDict.get("password"));
        this.type = ((String) argsDict.get("type"));
        User user = new User(netId, password, type);
        args.setUser(user);
    }

    /**
     * retrieves the relevant information from the argument dictionary,
     * and creates a lecture object which is then applied to the arguments attribute.
     */
    public void buildLecture() {
        buildCourseId();
        buildNetId();
        this.week = ((Integer) argsDict.get("week"));
        this.duration = ((Duration) argsDict.get("duration"));
        LecturePlan lecture = new LecturePlan(courseId, week, netId, duration);
        args.setLecture(lecture);
    }

    public Arguments getResult() {
        return args;
    }


}
