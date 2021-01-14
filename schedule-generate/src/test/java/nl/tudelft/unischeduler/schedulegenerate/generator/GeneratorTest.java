package nl.tudelft.unischeduler.schedulegenerate.generator;

import nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator;
import nl.tudelft.unischeduler.schedulegenerate.entities.Course;
import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Room;
import nl.tudelft.unischeduler.schedulegenerate.entities.Student;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GeneratorTest {

    @Mock
    private transient ApiCommunicator mockApiCommunicator;

    private transient Generator generatorUnderTest;

    private static String basicName = "name";
    private static String basicNetId = "netId";
    private static String basicType = "type";


    @Before
    public void setUp() {
        initMocks(this);
        int NUMBER_OF_DAYS = 10;
        generatorUnderTest = new Generator(mockApiCommunicator);
        generatorUnderTest.setNumOfDays(NUMBER_OF_DAYS);
        generatorUnderTest.setTimeTable(createEmptyTimeTable(NUMBER_OF_DAYS));
        generatorUnderTest.setCurrentTime(makeBasicStartTime());

    }

    @Test
    public void testScheduleGenerateAuto() {
        // Setup
        final Timestamp currentTime = makeBasicStartTime();

        // Configure ApiCommunicator.getCourses(...).
        final ArrayList<Course> courses = new ArrayList<>(List.of(
                new Course(0L, basicName,
                        Set.of(new Student(basicNetId, basicType,
                                false,
                                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime())),
                        Set.of(new Lecture(0, 0, new Time(0L),
                                false, 3)), 3)));
        when(mockApiCommunicator.getCourses()).thenReturn(courses);
        when(mockApiCommunicator.allowedOnCampus(any(Student.class))).thenReturn(true);
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);

        // Configure ApiCommunicator.getRooms(...).
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 10, basicName)));
        when(mockApiCommunicator.getRooms()).thenReturn(rooms);

        // Run the test
        generatorUnderTest.scheduleGenerate(currentTime);

        // Verify the results
        verify(mockApiCommunicator).assignRoomToLecture(any(Lecture.class),
                eq(new Room(0, 10, basicName)));
        verify(mockApiCommunicator).setLectureTime(any(Lecture.class), any(Timestamp.class));
        verify(mockApiCommunicator).assignStudentToLecture(eq(
                new Student(basicNetId, basicType, false,
                        new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime())),
                any(Lecture.class));
    }

    @Test
    public void testCreateTimeTable() {
        // Setup
//        public Lecture(int id, int attendance, Timestamp startTime,
//                Time duration, boolean isOnline, int year, Room room) {
        Lecture l = new Lecture(0, 0,
                new Timestamp(makeBasicStartTime().getTime() + makeTimeLength(48).getTime()),
                new Time(makeTimeLength(2).getTime()),
                false, 3,
                null);
        final ArrayList<Lecture> lectures = new ArrayList<>(List.of(l));
        final Timestamp currentTime = makeBasicStartTime();
        List<List<Lecture>> expectedResult = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            expectedResult.add(new ArrayList<>());
        }
        expectedResult.get(2).add(l);

        // Run the test
        final List<List<Lecture>> result = generatorUnderTest
                .createTimeTable(lectures, currentTime, 10);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSchedulingAuto() {
        // Setup
        final ArrayList<Lecture> lectures = new ArrayList<>(List.of(
                new Lecture(43425, 0, new Time(0L), false, 1)));
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(32542, 1, basicName)));
        final ArrayList<Course> courses = new ArrayList<>(
                List.of(new Course(5435L, basicName,
                        Set.of(new Student(basicNetId, basicType, false,
                                new GregorianCalendar(1, Calendar.JANUARY, 1).getTime())),
                        Set.of(new Lecture(5435, 1, new Time(
                                new GregorianCalendar(1, Calendar.FEBRUARY, 3)
                                        .getTimeInMillis()),
                                false, 1)), 1)));
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);
        when(mockApiCommunicator.allowedOnCampus(any(Student.class))).thenReturn(true);

        // Run the test
        generatorUnderTest.scheduling(lectures, rooms, courses);

        // Verify the results
        verify(mockApiCommunicator).assignRoomToLecture(any(Lecture.class),
                eq(new Room(32542, 1, basicName)));
        verify(mockApiCommunicator).setLectureTime(any(Lecture.class), any(Timestamp.class));
        verify(mockApiCommunicator).assignStudentToLecture(eq(
                new Student(basicNetId, basicType, false,
                        new GregorianCalendar(1, Calendar.JANUARY, 1).getTime())),
                any(Lecture.class));
    }

    @Test
    public void testSchedulingYears() {
        // Setup
        final List<List<Course>> coursesPerYear = List.of(
                List.of(
                        new Course(0L, basicName,
                                Set.of(new Student(basicNetId, basicType, false,
                                        new GregorianCalendar(2019, Calendar.JANUARY, 1)
                                                .getTime())),
                                Set.of(new Lecture(0, 0, new Time(1000L),
                                        false, 3)),
                                3)),
                List.of(),
                List.of());
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 10, basicName)));
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);
        when(mockApiCommunicator.allowedOnCampus(any(Student.class))).thenReturn(true);

        // Run the test
        final boolean result = generatorUnderTest.schedulingYears(3, coursesPerYear, rooms);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockApiCommunicator).assignRoomToLecture(any(Lecture.class),
                eq(new Room(0, 10, basicName)));
        verify(mockApiCommunicator).setLectureTime(any(Lecture.class), any(Timestamp.class));
        verify(mockApiCommunicator).assignStudentToLecture(eq(
                new Student(basicNetId, basicType, false,
                        new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime())),
                any(Lecture.class));
    }

    @Test
    public void testSchedulingLectures() {
        // Setup
        final ArrayList<Lecture> lectures = new ArrayList<>(List.of(
                new Lecture(0, 0, new Time(0L), false, 2021)));
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 10, basicName)));
        final Set<Student> courseStudents = Set.of(
                new Student(basicNetId, basicType, false,
                        new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
        final PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
        studentsQueue.addAll(courseStudents);
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);

        // Run the test
        final boolean result = generatorUnderTest.schedulingLectures(lectures, rooms, courseStudents, studentsQueue);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockApiCommunicator).assignRoomToLecture(any(Lecture.class), eq(new Room(0, 10, basicName)));
        verify(mockApiCommunicator).setLectureTime(any(Lecture.class), any(Timestamp.class));
        verify(mockApiCommunicator).assignStudentToLecture(
                eq(new Student(basicNetId, basicType, false,
                        new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime())),
                any(Lecture.class));
    }

    @Test
    public void testSchedulingLecture() {
        // Setup
        final Lecture l = new Lecture(0, 0, new Time(0L), false, 2021);
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 10, basicName)));
        final Set<Student> courseStudents = Set.of(
                new Student(basicNetId, basicType, false,
                        new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
        final PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
        studentsQueue.addAll(courseStudents);
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);
        when(mockApiCommunicator.allowedOnCampus(any(Student.class))).thenReturn(true);

        // Run the test
        final boolean result = generatorUnderTest.schedulingLecture(l, rooms, courseStudents, studentsQueue);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockApiCommunicator).assignRoomToLecture(any(Lecture.class),
                eq(new Room(0, 10, basicName)));
        verify(mockApiCommunicator).setLectureTime(any(Lecture.class), any(Timestamp.class));
        verify(mockApiCommunicator).assignStudentToLecture(
                eq(new Student(basicNetId, basicType, false,
                        new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime())),
                any(Lecture.class));
    }

    @Test
    public void testMoveOnline() {
        // Setup
        final Lecture l = new Lecture(0, 0, new Time(0L), false, 2021);
        final Room room = new Room(0, 0, basicName);
        final Room onlineRoom = new Room(0, 0, basicName);
        final ApiCommunicator apiCom = new ApiCommunicator();
        final Set<Student> courseStudents = Set.of(
                new Student(basicNetId, basicType, false,
                        new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);

        // Run the test
        final boolean result = generatorUnderTest.moveOnline(l, room, onlineRoom, apiCom, courseStudents);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testFindRoomAuto() {
        // Setup
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 0, basicName)));
        final Lecture lecture = new Lecture(0, 0, new Time(0L), false, 2021);
        final Room expectedResult = new Room(0, 0, basicName);
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);

        // Run the test
        final Room result = generatorUnderTest.findRoom(rooms, lecture);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetEarliestTimeAuto() {
        // Setup
        final Room room = new Room(0, 0, basicName);
        final Lecture lecture = new Lecture(0, 0, new Time(1000L), false, 3);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(makeBasicStartTime().getTime());
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 45);
        c.set(Calendar.MILLISECOND, 0);
        Timestamp expectedResult = new Timestamp(c.getTimeInMillis());
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);

        // Run the test
        final Timestamp result = generatorUnderTest.getEarliestTime(room, lecture);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testIsFreeAuto() {
        // Setup
        final Timestamp timeslot = makeBasicStartTime();
        final Room room = new Room(0, 10, basicName);
        final Lecture lecture = new Lecture(0, 0, new Time(0L), false, 2021);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(makeBasicStartTime().getTime());
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 45);
        c.set(Calendar.MILLISECOND, 0);
        final Timestamp expectedResult = new Timestamp(c.getTimeInMillis());
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);

        // Run the test
        generatorUnderTest.setCurrentTime(makeBasicStartTime());
        final Timestamp result = generatorUnderTest.isFree(timeslot, room, lecture, 1);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetCapacity() {
        // Setup
        final Room room = new Room(0, 0, basicName);

        // Run the test
        final int result = generatorUnderTest.getCapacity(room);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testGetIntervalBetweenLectures() {
        // Setup
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);

        // Run the test
        final long result = generatorUnderTest.getIntervalBetweenLectures();

        // Verify the results
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void testGetApiCommunicator() {
        // Setup

        // Run the test
        final ApiCommunicator result = generatorUnderTest.getApiCommunicator();

        // Verify the results
        assertThat(result).isEqualTo(this.mockApiCommunicator);
    }

    @Test
    public void testEquals() {
        // Setup

        // Run the test
        final boolean result = generatorUnderTest.equals("o");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testHashCode() {
        // Setup

        // Run the test
        final int result = generatorUnderTest.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(-169967052);
    }

    @Test
    public void testToString() {
        // Setup

        // Run the test
        final String result = generatorUnderTest.toString();

        // Verify the results
        assertThat(result).isEqualTo("Generator"
                + "(currentTime=2020-12-14 09:45:00.0, numOfDays=10, "
                + "MAX_ITERATIONS=2, timeTable=[[], [], "
                + "[], [], [], [], [], [], [], []], apiCommunicator=mockApiCommunicator)");
    }

    // test that it returns an empty list when all lectures are unscheduled
    @org.junit.jupiter.api.Test
    void testCreateTimetableNoneScheduled() {
        Generator test = makeGenerator();
        List<List<Lecture>> l = test.createTimeTable(createListLectures(),
                makeBasicStartTime(), 5);
        assertEquals(5, l.size());
        assertEquals(0, l.get(0).size());
    }

    // test that it creates a timetable when the lectures are scheduled
    @org.junit.jupiter.api.Test
    void testCreateTimetableAllScheduled() {
        Generator test = makeGenerator();
        List<List<Lecture>> l = test.createTimeTable(createListLecturesScheduled(),
                makeBasicStartTime(), 5);
        assertEquals(5, l.size());
        assertEquals(1, l.get(0).size());
    }

    @org.junit.jupiter.api.Test
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // the null assignment is necessary
    void testScheduleGenerate() {
        Exception e = null;
        Generator test = makeGenerator();
        test.scheduleGenerate(makeBasicStartTime());
        try {
            test.scheduleGenerate(makeBasicStartTime());
        } catch (Exception a) {
            e = a;
        }
        assertNull(e);
    }

    @org.junit.jupiter.api.Test
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // the null assignment is necessary
    void testSchedulingWorks() {
        Generator test = makeGenerator();
        ArrayList<Lecture> l = createListLectures();
        List<List<Lecture>> timet = new ArrayList<>();
        Room r = makeRoom();
        Room rr = new Room(4324, 100, "la vache");
        ArrayList<Room> lr = new ArrayList<>();
        lr.add(r);
        lr.add(rr);
        Exception e = null;
        test.setTimeTable(timet);
        test.setCurrentTime(makeBasicStartTime());
        try {
            test.scheduling(l, lr, createListCourses());
        } catch (Exception a) {
            e = a;
        }
        assertNull(e);
    }

    @org.junit.jupiter.api.Test
    void testScheduling() {
        Generator test = makeGenerator();
        Generator spy = spy(test);
        ArrayList<Lecture> l = createListLectures();
        List<List<Lecture>> timet = new ArrayList<>();
        Room r = makeRoom();
        Room rr = new Room(4324, 100, "la vache");
        ArrayList<Room> lr = new ArrayList<>();
        lr.add(r);
        lr.add(rr);
        Timestamp basic = makeBasicStartTime();
        ArrayList<Course> lcs = createListCourses();
        test.setTimeTable(timet);
        test.setCurrentTime(basic);
        spy.scheduling(l, lr,
                lcs);
        verify(spy).scheduling(l, lr,
                lcs);
    }

    @org.junit.jupiter.api.Test
    void testGetEarliestTime() {
        Room r = makeRoom();
        Lecture l = new Lecture(3243, 0,
                makeBasicStartTime(), makeTimeLength(2),
                false, 1, r);
        List<List<Lecture>> tt = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tt.add(new ArrayList<>());
        }
        Generator g = makeGenerator();
        g.setCurrentTime(makeBasicStartTime());
        g.setTimeTable(tt);
        Timestamp t = g.getEarliestTime(r, l);
        assertEquals(makeBasicStartTime().getTime(), t.getTime());
    }



    @org.junit.jupiter.api.Test
    void testIsFree() {
        List<Lecture> lsc = createListLecturesScheduled();
        List<Lecture> ls = createListLectures();
        ArrayList<List<Lecture>> tt = new ArrayList<>();
        lsc.remove(2);
        lsc.remove(1);
        tt.add(lsc);
        Generator test = makeGenerator();
        Room r = makeRoom();
        Timestamp t = makeBasicStartTime();
        test.setCurrentTime(t);
        test.setTimeTable(tt);
        Timestamp min = new Timestamp(t.getTime() + makeTimeLength(2).getTime());
        Timestamp tmp = test.isFree(t, r, ls.get(0), 0);
        Timestamp max = new Timestamp(t.getTime() + makeTimeLength(3).getTime());
        assertTrue(tmp.getTime() >= min.getTime());
        assertTrue(tmp.before(max));
    }

    @org.junit.jupiter.api.Test
    void testFindRoom() {
        Room r = makeRoom();
        Room rr = new Room(4324, 100, "la vache");
        ArrayList<Room> lr = new ArrayList<>();
        lr.add(r);
        lr.add(rr);
        ArrayList<List<Lecture>> tt = new ArrayList<>(); // timetable
        int n = 5;
        for (int i = 0; i < n; i++) {
            tt.add(new ArrayList<>());
        }
        Timestamp t = makeBasicStartTime();
        Lecture l = createListLectures().get(0);
        List<Lecture> lsc = createListLecturesScheduled();
        lsc.remove(2);
        lsc.remove(1);
        tt.add(lsc);
        Generator test = makeGenerator();
        test.setTimeTable(tt);
        test.setCurrentTime(t);
        Room room = test.findRoom(lr, l);
        System.out.println(rr);
        System.out.println(r);
        assertEquals(room, r);
    }
}
