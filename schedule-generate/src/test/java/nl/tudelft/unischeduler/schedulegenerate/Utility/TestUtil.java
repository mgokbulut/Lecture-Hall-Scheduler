package nl.tudelft.unischeduler.schedulegenerate.Utility;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator;
import nl.tudelft.unischeduler.schedulegenerate.entities.Course;
import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Room;
import nl.tudelft.unischeduler.schedulegenerate.entities.Student;
import nl.tudelft.unischeduler.schedulegenerate.generator.Generator;

// a class that creates basic entities, useful for unit testing
public class TestUtil {
    static String roomName = "testRoom";

    public static Room makeRoom() {
        return new Room(1, 200, roomName);
    }

    /**
     * Creates a basic start time.
     *
     * @return a basic start time
     */
    public static Timestamp makeBasicStartTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2020);
        c.set(Calendar.MONTH, Calendar.DECEMBER); // december
        c.set(Calendar.DAY_OF_MONTH, 14); // monday 14th
        c.set(Calendar.HOUR_OF_DAY, 9); // 9am
        c.set(Calendar.MINUTE, 45); // 9:45am
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        // full date value is 2020-12-14T09:45:10.430
        return new Timestamp(c.getTimeInMillis());
    }

    /**
     * Creates a timestamp of n hours.
     *
     * @param n the length in hours of the timestamp
     * @return a timestamp of n hours
     */
    public static Time makeTimeLength(int n) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        c.add(Calendar.HOUR_OF_DAY, n);
        return new Time(c.getTimeInMillis());
    }

    public static Generator makeGenerator() {
        Generator gen = new Generator(new ApiCommunicator());
        return gen;
    }

    /**
     * Creates a basic list of lectures.
     *
     * @return a basic list of lectures
     */
    public static ArrayList<Lecture> createListLectures() {
        Room r = makeRoom();
        Lecture l1 = new Lecture(324324, 0, null,
                makeTimeLength(2), false, 1, r);
        Lecture l2 = new Lecture(436546, 0, null,
                makeTimeLength(2), false, 1, r);
        Lecture l3 = new Lecture(4314645, 0, null,
                makeTimeLength(2), false, 2, r);
        ArrayList<Lecture> a = new ArrayList<>();
        a.add(l1);
        a.add(l2);
        a.add(l3);
        return a;
    }

    /**
     * Creates empty time table.
     *
     * @param n how many days it should contain
     * @return empty time table
     */
    public static List<List<Lecture>> createEmptyTimeTable(int n) {
        List<List<Lecture>> li = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            li.add(new ArrayList<>());
        }
        return li;
    }

    /**
     * creates basic list of scheduled lectures.
     *
     * @return a basic list of scheduled lectures
     */
    public static ArrayList<Lecture> createListLecturesScheduled() {
        Room r = makeRoom();
        Timestamp t1 = makeBasicStartTime();
        Timestamp t2 = new Timestamp(t1.getTime() + makeTimeLength(24).getTime());
        Timestamp t3 = new Timestamp(t2.getTime() + makeTimeLength(24).getTime());
        Lecture l1 = new Lecture(543643, 0, t1,
                makeTimeLength(2), false, 1, r);
        Lecture l2 = new Lecture(432543645, 0, t2,
                makeTimeLength(2), false, 1, r);
        Lecture l3 = new Lecture(87698, 0, t3,
                makeTimeLength(2), false, 2, r);
        ArrayList<Lecture> a = new ArrayList<>();
        a.add(l1);
        a.add(l2);
        a.add(l3);
        return a;
    }

    /**
     * Creates a basic list of courses.
     *
     * @return a basic list of courses
     */
    public static ArrayList<Course> createListCourses() {
        Student g = new Student("georgeclooney", "STUDENT",
                true, new Date(makeBasicStartTime().getTime()));
        Student r = new Student("ronaldmcdonald", "STUDENT",
                true, new Date(makeBasicStartTime().getTime()));
        Set<Student> sets = new HashSet<>();
        sets.add(g);
        sets.add(r);
        Set<Lecture> setl = new HashSet<>();
        setl.addAll(createListLecturesScheduled());
        ArrayList<Course> cs = new ArrayList<>();
        cs.add(new Course(4324L, "CG", sets, setl, 1));
        return cs;
    }
}
