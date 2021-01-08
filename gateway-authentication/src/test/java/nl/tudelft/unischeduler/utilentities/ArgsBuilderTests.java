package nl.tudelft.unischeduler.utilentities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.Dictionary;
import java.util.Hashtable;
import nl.tudelft.unischeduler.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class ArgsBuilderTests {


    @BeforeEach
    public void setup() {
    }

    @Test
    public void constructorTest() {
        Dictionary testDict = new Hashtable();
        testDict.put("testKey", "testValue");

        ArgsBuilder builder = new ArgsBuilder(testDict);

        assertEquals(testDict, builder.getArgsDict());

    }

    @Test
    public void buildCourseIdTest() {
        Dictionary testDict = new Hashtable();
        long expected = 10L;
        testDict.put("courseId", expected);

        ArgsBuilder builder = new ArgsBuilder(testDict);
        builder.buildCourseId();
        Arguments args = builder.getResult();

        long actual = args.getCourseId();
        assertEquals(expected, actual);


    }

    @Test
    public void buildNetIdTest() {
        Dictionary testDict = new Hashtable();
        String expected = "test";
        testDict.put("netId", expected);

        ArgsBuilder builder = new ArgsBuilder(testDict);
        builder.buildNetId();
        Arguments args = builder.getResult();

        String actual = args.getNetId();
        assertEquals(expected, actual);


    }

    @Test
    public void buildCourseTest() {
        Dictionary testDict = new Hashtable();
        String name = "testName";
        int year = 10;
        int classesPerWeek = 2001;

        testDict.put("courseName", name);
        testDict.put("year", year);
        testDict.put("classesPerWeek", classesPerWeek);

        ArgsBuilder builder = new ArgsBuilder(testDict);
        builder.buildCourse();
        Arguments args = builder.getResult();

        Course expected = new Course(name, year, classesPerWeek);
        Course actual = args.getCourse();
        assertEquals(expected, actual);

    }

    @Test
    public void buildUserTest() {
        Dictionary testDict = new Hashtable();
        String netId = "testNetId";
        String password = "testPassword";
        String type = "testType";

        testDict.put("netId", netId);
        testDict.put("password", password);
        testDict.put("type", type);

        ArgsBuilder builder = new ArgsBuilder(testDict);
        builder.buildUser();
        Arguments args = builder.getResult();

        User expected = new User(netId, password, type);
        User actual = args.getUser();
        assertEquals(expected, actual);
    }

    @Test
    public void buildLectureTest() {
        Dictionary testDict = new Hashtable();
        long courseId = 10L;
        String netId = "testNetId";
        int week = 5;
        Duration duration = Duration.ofHours(1);

        testDict.put("courseId", courseId);
        testDict.put("netId", netId);
        testDict.put("week", week);
        testDict.put("duration", duration);

        ArgsBuilder builder = new ArgsBuilder(testDict);
        builder.buildLecture();
        Arguments args = builder.getResult();

        LecturePlan expected = new LecturePlan(courseId, week, netId, duration);
        LecturePlan actual = args.getLecture();
        assertEquals(expected, actual);
    }

}
