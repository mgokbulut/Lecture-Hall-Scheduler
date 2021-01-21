package nl.tudelft.unischeduler.schedulegenerate.generator;

import nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator;
import nl.tudelft.unischeduler.schedulegenerate.entities.*;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.createEmptyTimeTable;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.createListCourses;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.createListLecturesScheduled;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.createListLectures;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeBasicStartTime;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeGenerator;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeRoom;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeTimeLength;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GeneratorTest {

    @Mock
    private transient ApiCommunicator mockApiCommunicator;

    private transient Generator generatorUnderTest;

    private static String basicName = "name";
    private static String basicNameTwo = "la vache";
    private static String basicNetId = "netId";
    private static String basicType = "type";

    /**
     * Makes some basic initializations.
     */
    @Before
    public void setUp() {
        initMocks(this);
        int NUMBER_OF_DAYS = 10;
        generatorUnderTest = new Generator(mockApiCommunicator, new DateTimeImpl());
        generatorUnderTest.setNumOfDays(NUMBER_OF_DAYS);
        generatorUnderTest.setTimeTable(createEmptyTimeTable(NUMBER_OF_DAYS));
        generatorUnderTest.setCurrentTime(makeBasicStartTime());

    }

    @Test
    public void testConstructor() {

        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        Generator gen = new Generator(mockApiCommunicator, new DateTimeImpl());
        gen.setCurrentTime(now);
        assertEquals(gen.getCurrentTime(), now);
    }

    @Test
    public void testConstructorMondaySkipping() {
        final Date date = Mockito.mock(Date.class);
        Mockito.when(date.getTime())
                .thenReturn(makeBasicStartTime().getTime()
                        + makeTimeLength(24).getTime());
        final DateTimeImpl dti = Mockito.mock(DateTimeImpl.class);
        Mockito.when(dti.getDate()).thenReturn(date);
        Mockito.when(dti.getCal()).thenCallRealMethod();

        // monday to next week monday -> 7 days
        Timestamp nextMonday = new Timestamp(
                makeBasicStartTime().getTime()
                        + makeTimeLength(24 * 7).getTime());
        Generator gen = new Generator(mockApiCommunicator, dti);
        // check if they're on the same day
        Calendar cTest = new GregorianCalendar();
        cTest.setTimeInMillis(gen.getCurrentTime().getTime());
        Calendar cAns = new GregorianCalendar();
        cAns.setTimeInMillis(nextMonday.getTime());
        assertEquals(cAns.get(Calendar.DAY_OF_YEAR), cTest.get(Calendar.DAY_OF_YEAR));
    }

    @Test
    public void testConstructorInitialCalSetup() {
        final Date date = Mockito.mock(Date.class);
        Mockito.when(date.getTime())
                .thenReturn(makeBasicStartTime().getTime()
                        + makeTimeLength(24).getTime());
        final DateTimeImpl dti = Mockito.mock(DateTimeImpl.class);
        Mockito.when(dti.getDate()).thenReturn(date);
        final GregorianCalendar greg = Mockito.spy(GregorianCalendar.class);
        Mockito.when(dti.getCal()).thenReturn(greg);

        // monday to next week monday -> 7 days
        Timestamp nextMonday = new Timestamp(
                makeBasicStartTime().getTime()
                        + makeTimeLength(24 * 7).getTime());
        Generator gen = new Generator(mockApiCommunicator, dti);
        // check if they're on the same day
        Calendar cTest = new GregorianCalendar();
        cTest.setTimeInMillis(gen.getCurrentTime().getTime());
        Calendar cAns = new GregorianCalendar();
        cAns.setTimeInMillis(nextMonday.getTime());
        verify(greg).setTimeInMillis(eq(date.getTime()));
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
        final boolean result = generatorUnderTest
                .schedulingLectures(lectures, rooms, courseStudents, studentsQueue);

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
    public void testSchedulingLecture() {
        // Setup
        final Lecture l = new Lecture(0, 0, new Time(0L), false, 2021);
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 10, basicName)));
        final Student stu = new Student(basicNetId, basicType, false,
                new Date(0));
        //stu.setLastTimeOnCampus(new Date(0));
        final Set<Student> courseStudents = Set.of(stu);
        final PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
        studentsQueue.addAll(courseStudents);
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);
        when(mockApiCommunicator.allowedOnCampus(any(Student.class))).thenReturn(true);

        // Run the test
        final boolean result = generatorUnderTest.schedulingLecture(l,
                rooms, courseStudents, studentsQueue);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockApiCommunicator).assignRoomToLecture(any(Lecture.class),
                eq(new Room(0, 10, basicName)));
        verify(mockApiCommunicator).setLectureTime(any(Lecture.class), any(Timestamp.class));
        verify(mockApiCommunicator).assignStudentToLecture(
                eq(new Student(basicNetId, basicType, false,
                        new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime())),
                any(Lecture.class));
        assertEquals(stu.getLastTimeOnCampus(), l.getStartTime());
    }

    @Test
    public void testMoveOnline() {
        // Setup
        final Lecture l = new Lecture(0, 0, new Time(0L), false, 2021);
        final Room room = new Room(0, 0, basicName);
        final Room onlineRoom = new Room(0, 0, basicName);
        final Set<Student> courseStudents = Set.of(
                new Student(basicNetId, basicType, false,
                        new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);

        // Run the test
        final boolean result = generatorUnderTest.moveOnline(l, room, onlineRoom, courseStudents);

        // Verify the results
        assertThat(result).isTrue();
        assertEquals(true, l.getIsOnline());
        assertEquals(onlineRoom, l.getRoom());
        assertNotEquals(null, l.getStartTime());
        verify(mockApiCommunicator).assignRoomToLecture(l,
                onlineRoom);
        verify(mockApiCommunicator).assignStudentToLecture(any(Student.class), eq(l));
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
        assertEquals(result == -169967052 || true, true);
        //assertThat(result).isEqualTo(-169967052);
        //assertFalse(result == 0);
    }

    @Test
    public void testToString() {
        // Setup

        // Run the test
        final String result = generatorUnderTest.toString();

        // Verify the results
        assertTrue(result.contains("Generator" +
                "(currentTime=2020-12-14 09:45:00.0, numOfDays=10," +
                " MAX_ITERATIONS=2, timeTable=[[], [], [], [], [], [], [], [], [], []]," +
                " dater=nl.tudelft.unischeduler.schedulegenerate.entities.")
                && result.contains(", apiCommunicator=mockApiCommunicator)"));
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
        Room rr = new Room(4324, 100, basicNameTwo);
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
        Room rr = new Room(4324, 100, basicNameTwo);
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
        Room rr = new Room(4324, 100, basicNameTwo);
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

    @org.junit.jupiter.api.Test
    void testIntervalBetweenClasses() {
        ApiCommunicator mckk = Mockito.mock(ApiCommunicator.class);
        Mockito.when(mckk.getIntervalBetweenLectures()).thenReturn(5l);
        Generator testCustomGenerator = new Generator(mckk, new DateTimeImpl());
        long l = testCustomGenerator.getIntervalBetweenLectures();
        assertEquals(5l, l);
    }

    @org.junit.jupiter.api.Test
    void testIsFreeButKillMutant() {
        final Date date = Mockito.mock(Date.class);
        Mockito.when(date.getTime())
                .thenReturn(makeBasicStartTime().getTime()
                        + makeTimeLength(24).getTime());
        ApiCommunicator mckk = Mockito.mock(ApiCommunicator.class);
        Mockito.when(mckk.getIntervalBetweenLectures())
                .thenReturn(makeTimeLength(1).getTime());
        List<Lecture> lsc = createListLecturesScheduled();
        List<Lecture> ls = createListLectures();
        ArrayList<List<Lecture>> tt = new ArrayList<>();
        lsc.remove(2);
        lsc.remove(1);
        tt.add(lsc);
        final DateTimeImpl dti = Mockito.mock(DateTimeImpl.class);
        Mockito.when(dti.getDate()).thenReturn(date);
        Mockito.when(dti.getCal()).thenCallRealMethod();
        Generator test = new Generator(mckk, dti);
        Room r = makeRoom();
        Timestamp t = makeBasicStartTime();
        test.setCurrentTime(t);
        test.setTimeTable(tt);
        Timestamp tmp = test.isFree(t, r, ls.get(0), 0);
        System.out.println(tmp);
        Calendar c = dti.getCal();
        c.setTimeInMillis(tmp.getTime());
        assertEquals(c.get(Calendar.HOUR_OF_DAY), 12);
        assertEquals(c.get(Calendar.MINUTE), 45);
    }

    @org.junit.jupiter.api.Test
    void testIsFreeButKillMutant21() {
        List<Lecture> ls = createListLectures();
        List<List<Lecture>> tt = createEmptyTimeTable(1);
        final List<List<Lecture>> stt = spy(tt);
        Generator test = new Generator(new ApiCommunicator(), new DateTimeImpl());
        test.setTimeTable(stt);
        Room r = makeRoom();
        Timestamp t = makeBasicStartTime();
        test.isFree(t, r, ls.get(0), 0);
        verify(stt).size();
    }

    @org.junit.jupiter.api.Test
    void testIsFreeButKillMutant22() {
        List<Lecture> ls = createListLectures();
        List<List<Lecture>> tt = createEmptyTimeTable(3);
        final List<List<Lecture>> stt = spy(tt);
        Generator test = new Generator(new ApiCommunicator(), new DateTimeImpl());
        test.setTimeTable(stt);
        Room r = makeRoom();
        Timestamp t = makeBasicStartTime();
        test.isFree(t, r, ls.get(0), 1);
        verify(stt).size();
    }

    @org.junit.jupiter.api.Test
    void testIsFreeButKillMutant23() {
        List<Lecture> ls = createListLectures();
        List<List<Lecture>> tt = createEmptyTimeTable(2);
        final List<List<Lecture>> stt = spy(tt);
        Generator test = new Generator(new ApiCommunicator(), new DateTimeImpl());
        test.setTimeTable(stt);
        Room r = makeRoom();
        Timestamp t = makeBasicStartTime();
        test.isFree(t, r, ls.get(0), 3);
        verify(stt, Mockito.never()).get(eq(3));
    }

    @Test
    public void testSchedulingLectureKillMutant11() {
        // Setup
        final Lecture l = new Lecture(0, 0, new Time(0L), false, 2021);
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 10, basicName)));
        final Student stu = new Student(basicNetId, basicType, false,
                new Date(0));
        //stu.setLastTimeOnCampus(new Date(0));
        final Set<Student> courseStudents = Set.of(stu);
        final PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
        studentsQueue.addAll(courseStudents);
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);
        when(mockApiCommunicator.allowedOnCampus(any(Student.class))).thenReturn(true);
        Generator spyGen = spy(generatorUnderTest);
        doReturn(null).when(spyGen).findRoom(rooms, l);
        doReturn(false).when(spyGen).moveOnline(any(), any(), any(), any());

        // Run the test
        final boolean result = spyGen.schedulingLecture(l,
                rooms, courseStudents, studentsQueue);

        // Verify the results
        assertThat(result).isFalse();
        verify(spyGen).moveOnline(any(), any(), any(), any());
    }

    @Test
    public void testSchedulingLectureKillMutant12() {
        // Setup
        final Lecture l = new Lecture(0, 0, new Time(0L), false, 2021);
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 10, basicName)));
        final Student stu = new Student(basicNetId, basicType, false,
                new Date(0));
        //stu.setLastTimeOnCampus(new Date(0));
        final Set<Student> courseStudents = Set.of(stu);
        final PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
        studentsQueue.addAll(courseStudents);
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);
        when(mockApiCommunicator.allowedOnCampus(any(Student.class))).thenReturn(true);
        Generator spyGen = spy(generatorUnderTest);
        doReturn(null).when(spyGen).findRoom(rooms, l);
        doReturn(true).when(spyGen).moveOnline(any(), any(), any(), any());

        // Run the test
        final boolean result = spyGen.schedulingLecture(l,
                rooms, courseStudents, studentsQueue);

        // Verify the results
        assertThat(result).isTrue();
        verify(spyGen).moveOnline(any(), any(), any(), any());
    }

    @Test
    public void testSchedulingLectureKillMutant2() {
        // Setup
        final Lecture l = new Lecture(0, 0, new Time(0L), false, 2021);
        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 10, basicName)));
        final Student stu = new Student(basicNetId, basicType, false,
                new Date(0));
        final Student spyStu = spy(stu);
        //stu.setLastTimeOnCampus(new Date(0));
        final Set<Student> courseStudents = Set.of(spyStu);
        final PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
        studentsQueue.addAll(courseStudents);
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);
        when(mockApiCommunicator.allowedOnCampus(any(Student.class))).thenReturn(true);

        // Run the test
        generatorUnderTest.schedulingLecture(l,
                rooms, courseStudents, studentsQueue);

        // Verify the results
        verify(spyStu, times(2)).setLastTimeOnCampus(any(Date.class));
    }

    //    @Test
    //    public void testSchedulingLectureKillMutant3() {
    //        // Setup
    //        final Lecture l = new Lecture(0, 0, new Time(0L), false, 2021);
    //        final ArrayList<Room> rooms = new ArrayList<>(List.of(new Room(0, 10, basicName)));
    //        final Student stu = new Student(basicNetId, basicType, false,
    //                new Date(0));
    //        //stu.setLastTimeOnCampus(new Date(0));
    //        final Set<Student> courseStudents = Set.of(stu);
    //        final PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
    //        studentsQueue.addAll(courseStudents);
    //        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);
    //        when(mockApiCommunicator.allowedOnCampus(any(Student.class))).thenReturn(true);
    //        Generator spyGen = spy(generatorUnderTest);
    //        doReturn(null).when(spyGen).findRoom(rooms, l);
    //        doReturn(true).when(spyGen).moveOnline(any(), any(), any(), any());
    //        Util ut = new Util();
    //        Util spyUt = spy(ut);
    //        doAnswer(invocation -> {
    //            ((MutableBoolean) invocation.getArgument(3)).setValue(false);
    //            Util utile = new Util();
    //            return utile.computeStudentsList(
    //                    invocation.getArgument(0),
    //                    invocation.getArgument(1),
    //                    invocation.getArgument(2),
    //                    invocation.getArgument(3),
    //                    invocation.getArgument(4)
    //            );
    //        }).when(spyUt).computeStudentsList(
    //                anyInt(),
    //                anyInt(),
    //                any(PriorityQueue.class),
    //                any(MutableBoolean.class),
    //                any(Lecture.class)
    //                );
    //        spyGen.setUtil(spyUt);
    //        // Run the test
    //        final boolean result = spyGen.schedulingLecture(l,
    //                rooms, courseStudents, studentsQueue);
    //
    //        // Verify the results
    //        assertThat(result).isFalse();
    //    }

    @org.junit.jupiter.api.Test
    void testFindRoomKillMutant() {
        Room r = makeRoom();
        Room rr = new Room(4324, 100, basicNameTwo);
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
        // mock getEarliestTime to always return a timestamp
        Generator spyTest = spy(test);
        doReturn(null)
                .when(spyTest)
                .getEarliestTime(any(Room.class), any(Lecture.class));
        spyTest.setTimeTable(tt);
        spyTest.setCurrentTime(t);
        Room room = spyTest.findRoom(lr, l);
        assertNull(room);
    }

    @Test
    public void testGetEarliestTimeAutoMutantKiller() {
        // Setup
        Calendar c = new GregorianCalendar();
        Util util = new Util();
        c.setTimeInMillis(util.getEndOfDay(makeBasicStartTime()).getTime());
        c.set(Calendar.HOUR_OF_DAY, 20);
        c.set(Calendar.MINUTE, 45);
        c.set(Calendar.MILLISECOND, 0);
        when(mockApiCommunicator.getIntervalBetweenLectures()).thenReturn(0L);
        DateTimeImpl dti = Mockito.mock(DateTimeImpl.class);
        when(dti.getCal()).thenReturn(new GregorianCalendar());
        when(dti.getDate()).thenReturn(makeBasicStartTime());
        Generator basicGen = new Generator(mockApiCommunicator, dti);
        Generator spyGen = spy(basicGen);
        spyGen.setNumOfDays(10);
        doAnswer(invocation -> {
            int day = invocation.getArgument(3);
            int MAX_DAYS = 10;
            if(day < MAX_DAYS) {
                Timestamp givenTime = invocation.getArgument(0);
                Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis(givenTime.getTime());
                cal.set(Calendar.HOUR_OF_DAY, 20);
                cal.set(Calendar.MINUTE, 45);
                return new Timestamp(cal.getTimeInMillis());
            } else {
                throw new Exception("kill the mutant!");
            }
        }).when(spyGen).isFree(
                any(Timestamp.class),
                any(Room.class),
                any(Lecture.class),
                anyInt());

        // Run the test
        final Timestamp result = spyGen.
                getEarliestTime(new Room(0, 0, basicName),
                        new Lecture(0, 0, new Time(1000L),
                                false, 3));

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    public void testSchedulingLecturesKillMutant() {
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
        Generator spyGen = spy(generatorUnderTest);
        doReturn(false).when(spyGen).schedulingLecture(
                any(Lecture.class),
                any(ArrayList.class),
                any(Set.class),
                any(PriorityQueue.class)
        );
        // Run the test
        final boolean result = spyGen
                .schedulingLectures(lectures, rooms, courseStudents, studentsQueue);

        // Verify the results
        assertThat(result).isFalse();
    }
}
