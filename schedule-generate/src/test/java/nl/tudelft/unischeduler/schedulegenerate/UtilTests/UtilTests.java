package nl.tudelft.unischeduler.schedulegenerate.UtilTests;

import nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator;
import nl.tudelft.unischeduler.schedulegenerate.entities.*;
import nl.tudelft.unischeduler.schedulegenerate.generator.Generator;
import org.junit.jupiter.api.Test;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.createListLecturesScheduled;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.createListLectures;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeBasicStartTime;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeGenerator;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeTimeLength;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTests {

    @Test
    void testDistanceTwoTimestamps1() {
        Timestamp t1 = makeBasicStartTime();
        Timestamp t2 = new Timestamp(t1.getTime() + makeTimeLength(24).getTime());
        //Generator test = makeGenerator();
        int n1 = Util.calDistance(t1, t2);
        assertEquals(1, n1);
    }

    @Test
    void testDistanceTwoTimestamps2() {
        Timestamp t1 = makeBasicStartTime();
        Timestamp t2 = new Timestamp(t1.getTime() + makeTimeLength(24).getTime());
        Timestamp t3 = new Timestamp(t2.getTime() + makeTimeLength(24).getTime());
        //Generator test = makeGenerator();
        int n2 = Util.calDistance(t1, t3);
        assertEquals(2, n2);
    }

    @Test
    void testDistanceTwoTimestampsWeekend() {
        Timestamp t1 = makeBasicStartTime(); // 2020-12-14T09:45:10.430
        Timestamp timeFriday = new Timestamp(t1.getTime()
                + (4 * makeTimeLength(24).getTime())); // 2020-12-18T05:45:10.430
        Timestamp timeMonday = new Timestamp(timeFriday.getTime()
                + (3 * makeTimeLength(24).getTime())); // 2020-12-21T02:45:10.430
        //Generator test = makeGenerator();
        int n1 = Util.calDistance(timeFriday, timeMonday);
        assertEquals(1, n1);
    }

    @Test
    void testAssignRoomToLecture_timeIsNull() {
        Timestamp time = null;
        Lecture lecture = new Lecture(0, 0, null, false, 0);
        List<List<Lecture>> timeTable = new ArrayList<>();
        Room room = new Room(0, 0, "room");
        Timestamp currentTime = new Timestamp(0);

        Room expected = null;
        Room actual = Util.assignRoomToLecture(time, lecture, timeTable, room, currentTime);

        assertEquals(expected, actual);

    }

    @Test
    void testAssignRoomToLectureTest() {
        Timestamp time = new Timestamp(2021, 1, 5, 0, 0, 0, 0);
        Lecture lecture = new Lecture(0, 0, null, false, 0);
        List<List<Lecture>> timeTable = new ArrayList<>();
        timeTable.add(new ArrayList<>());
        timeTable.add(new ArrayList<>());
        Room room = new Room(0, 0, "room");
        Timestamp currentTime = new Timestamp(2021, 1, 4, 0, 0, 0, 0);

        Room expected = room;
        Room actual = Util.assignRoomToLecture(time, lecture, timeTable, room, currentTime);
        assertEquals(expected, actual);

        assertEquals(time, lecture.getStartTime());
        assertEquals(room, lecture.getRoom());
        assertEquals(1, timeTable.get(1).size());

    }

    @Test
    void testComputeStudentsList() {

        Student stu1 = new Student("stu1", "Student", true, new Date(1, Calendar.FEBRUARY, 5));
        stu1.setLastTimeOnCampus(new Date(1));
        Student stu2 = new Student("stu2", "Student", true, new Date(1, Calendar.MARCH, 5));
        stu2.setLastTimeOnCampus(new Date(2));
        Student stu3 = new Student("stu3", "Student", true, new Date(1, Calendar.APRIL, 5));
        stu3.setLastTimeOnCampus(new Date(3));
        int capacity = 2;
        int maxIterations = 3;
        PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
        Lecture l = new Lecture(1, 0, new Timestamp(2, 1, 5, 1, 1, 1, 1),
                new Time(100), false, 2, null);

        studentsQueue.add(stu1);
        studentsQueue.add(stu2);
        studentsQueue.add(stu3);
        studentsQueue.add(stu1);

        List<Object> res = Util.computeStudentsList(
                capacity, maxIterations, studentsQueue, false, l);

        Set<Student> studentsToAdd = ((Set<Student>) res.get(0));
        List<Student> notSelected = ((List<Student>) res.get(1));

        assertEquals(capacity, studentsToAdd.size());
        assertEquals(0, notSelected.size());
    }

    @Test
    void testAddClassDurationAndTime() {

        Lecture lecture = new Lecture(1, 0,
                new Timestamp(5), new Time(200), false, 2, null);
        Timestamp time = new Timestamp(300);

        Timestamp expected = new Timestamp(500);
        Timestamp actual = Util.addClassDurationAndTime(lecture, time);

        assertEquals(expected, actual);

    }


    @Test
    void testGetEndOfDay() {

        Timestamp time = new Timestamp(1, 1, 1, 4, 45, 0, 0);

        Timestamp expected = new Timestamp(1, 1, 1, 17, 45, 0, 0);
        Timestamp actual = Util.getEndOfDay(time);

        assertEquals(expected, actual);

    }

    @Test
    void testGetStartOfDay() {

        Timestamp time = new Timestamp(1, 1, 1, 4, 45, 0, 0);

        Timestamp expected = new Timestamp(1, 1, 1, 9, 45, 0, 0);
        Timestamp actual = Util.getStartOfDay(time);

        assertEquals(expected, actual);

    }

    @Test
    void testPopulateCourses() {

        List<Course> courses = new ArrayList<>();
        courses.add(new Course());
        int numOfDays = 7;
        ApiCommunicator apiComm = new ApiCommunicator();
        Timestamp currentTime = new Timestamp(200);

        ArrayList<Lecture> expected = new ArrayList<>();
        ArrayList<Lecture> actual = Util.populateLectures(courses, numOfDays, apiComm, currentTime);

        assertEquals(expected, actual);

    }

    @Test
    void testPopulateCoursesPerYear() {

        List<Course> courses = new ArrayList<>();
        List<List<Course>> coursesPerYear = new ArrayList<>();
        int maxNumberOfYears = 2;

        Course c1 = new Course(1L, "course1", null, null, 1);
        Course c2 = new Course(2L, "course1", null, null, 2);
        Course c3 = new Course(3L, "course1", null, null, 2);

        courses.addAll(List.of(c1, c2, c3));

        Util.populateCoursesPerYear(courses, coursesPerYear, maxNumberOfYears);

        assertEquals(1, coursesPerYear.get(0).size());
        assertEquals(2, coursesPerYear.get(1).size());

    }

    @Test
    void testAreLecturesConflicting_NoConflict() {

        Room r1 = new Room(1, 1000, "");
        Room r2 = new Room(2, 1000, "");

        Lecture l1 = new Lecture(1, 200, new Time(1, 0, 0), true, 1);
        Lecture l2 = new Lecture(2, 200,
                new Timestamp(2020, 2, 1, 10, 45, 0, 0),
                new Time(1, 0, 0),
                false, 2, r2);

        Timestamp time = new Timestamp(2021, 2, 1, 10, 45, 0, 0);
        long interval = 1000L;

        boolean expected = true;
        boolean actual = Util.areLecturesConflicting(l1, l2, time, r1, interval);

        assertEquals(expected, actual);

    }

    @Test
    void testAreLecturesConflicting_SameTime() {

        Room r1 = new Room(1, 1000, "");
        Room r2 = new Room(2, 1000, "");

        Lecture l1 = new Lecture(1, 200, new Time(1, 0, 0), true, 1);
        Lecture l2 = new Lecture(2, 200,
                new Timestamp(2020, 2, 1, 10, 45, 0, 0),
                new Time(1, 0, 0),
                false, 2, r2);

        Timestamp time = new Timestamp(2020, 2, 1, 10, 45, 0, 0);
        long interval = 1000L;

        boolean expected = true;
        boolean actual = Util.areLecturesConflicting(l1, l2, time, r1, interval);

        assertEquals(expected, actual);

    }

    @Test
    void testAreLecturesConflicting_SameTimeAndYear() {

        Room r1 = new Room(1, 1000, "");
        Room r2 = new Room(2, 1000, "");

        Lecture l1 = new Lecture(1, 200, new Time(1, 0, 0), true, 1);
        Lecture l2 = new Lecture(2, 200,
                new Timestamp(2020, 2, 1, 10, 45, 0, 0),
                new Time(1, 0, 0),
                false, 1, r2);

        Timestamp time = new Timestamp(2020, 2, 1, 10, 45, 0, 0);
        long interval = 1000L;

        boolean expected = true;
        boolean actual = Util.areLecturesConflicting(l1, l2, time, r1, interval);

        assertEquals(expected, true);

    }

    @Test
    void testAreLecturesConflicting_SameTimeAndRoom() {

        Room r1 = new Room(1, 1000, "");

        Lecture l1 = new Lecture(1, 200, new Time(1, 0, 0), true, 1);
        Lecture l2 = new Lecture(2, 200,
                new Timestamp(2020, 2, 1, 10, 45, 0, 0),
                new Time(1, 0, 0),
                false, 2, r1);

        Timestamp time = new Timestamp(2020, 2, 1, 10, 45, 0, 0);
        long interval = 1000L;

        boolean expected = true;
        boolean actual = Util.areLecturesConflicting(l1, l2, time, r1, interval);

        assertEquals(expected, true);

    }

    @Test
    void testAddIfAllowed() {
        PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
        Set<Student> courseStudents = new HashSet<>();
        ApiCommunicator apiCom = new ApiCommunicator();
        int expected = 5;
        for (int i = 0; i < expected; i++) {
            Student stu = new Student();
            stu.setNetId(String.valueOf(i));
            courseStudents.add(stu);
        }
        Util.addIfAllowed(studentsQueue, courseStudents, apiCom);
        int actual = studentsQueue.size();

        assertEquals(expected, actual);
    }

    @Test
    void testNextDay() {
        Timestamp timeMonday = makeBasicStartTime();
        Timestamp timeTuesday = new Timestamp(timeMonday.getTime()
                + makeTimeLength(24).getTime());
        //Generator test = makeGenerator();
        Timestamp supposedTuesday = Util.nextDay(timeMonday);
        assertEquals(timeTuesday.getTime(), supposedTuesday.getTime());
    }

    @Test
    void testNextDayWeekend() {
        Timestamp t1 = makeBasicStartTime();
        Timestamp timeFriday = new Timestamp(t1.getTime()
                + (4 * makeTimeLength(24).getTime()));
        Timestamp timeMonday = new Timestamp(timeFriday.getTime()
                + (3 * makeTimeLength(24).getTime()));
        //Generator test = makeGenerator();
        Timestamp supposedMonday = Util.nextDay(timeFriday);
        assertEquals(timeMonday.getTime(), supposedMonday.getTime());
    }

    @Test
    void testOverlapSameLecture() {
        List<Lecture> lsc = createListLecturesScheduled();
        Lecture l = lsc.get(0);
        Generator test = makeGenerator();
        assertTrue(Util.overlap(l, test.getIntervalBetweenLectures(), l.getStartTime(), l));
    }

    @Test
    void testOverlapBasicFalse() {
        List<Lecture> lsc = createListLecturesScheduled();
        Lecture l = lsc.get(0);
        Lecture ll = lsc.get(1);
        Generator test = makeGenerator();
        assertFalse(Util.overlap(l, test.getIntervalBetweenLectures(), l.getStartTime(), ll));
    }

    @Test
    void testOverlapBasicTrue() {
        List<Lecture> ls = createListLectures();
        List<Lecture> lsc = createListLecturesScheduled();
        Lecture l = ls.get(0);
        Lecture ll = lsc.get(0);
        Generator test = makeGenerator();
        Timestamp inMiddleOfLec = new Timestamp(makeBasicStartTime().getTime()
                + makeTimeLength(1).getTime());
        assertTrue(Util.overlap(l, test.getIntervalBetweenLectures(), inMiddleOfLec, ll));
    }

    @Test
    void testOverlap_EnvelopsOtherLecture() {
        Lecture l1 = new Lecture(1, 12, new Timestamp(10), new Time(10), false, 1, null);
        Lecture l2 = new Lecture(2, 12, new Time(100000), false, 2);
        Timestamp potentialStartTime = new Timestamp(0);

        assertTrue(Util.overlap(l2, 0, potentialStartTime, l1));
    }

}
