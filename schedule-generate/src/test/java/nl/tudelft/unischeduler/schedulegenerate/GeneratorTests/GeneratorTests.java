package nl.tudelft.unischeduler.schedulegenerate.GeneratorTests;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.unischeduler.schedulegenerate.entities.Course;
import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Room;
import nl.tudelft.unischeduler.schedulegenerate.generator.Generator;
import org.junit.jupiter.api.Test;

import static nl.tudelft.unischeduler.schedulegenerate.GeneratorTests.Util.makeBasicStartTime;
import static nl.tudelft.unischeduler.schedulegenerate.GeneratorTests.Util.makeGenerator;
import static nl.tudelft.unischeduler.schedulegenerate.GeneratorTests.Util.makeRoom;
import static nl.tudelft.unischeduler.schedulegenerate.GeneratorTests.Util.makeTimeLength;
import static nl.tudelft.unischeduler.schedulegenerate.GeneratorTests.Util.createListCourses;
import static nl.tudelft.unischeduler.schedulegenerate.GeneratorTests.Util.createListLecturesScheduled;
import static nl.tudelft.unischeduler.schedulegenerate.GeneratorTests.Util.createListLectures;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class GeneratorTests {

    @Test
    void testDistanceTwoTimestamps1() {
        Timestamp t1 = makeBasicStartTime();
        Timestamp t2 = new Timestamp(t1.getTime() + makeTimeLength(24).getTime());
        Generator test = makeGenerator();
        int n1 = test.calDistance(t1, t2);
        assertEquals(1, n1);
    }

    @Test
    void testDistanceTwoTimestamps2() {
        Timestamp t1 = makeBasicStartTime();
        Timestamp t2 = new Timestamp(t1.getTime() + makeTimeLength(24).getTime());
        Timestamp t3 = new Timestamp(t2.getTime() + makeTimeLength(24).getTime());
        Generator test = makeGenerator();
        int n2 = test.calDistance(t1, t3);
        assertEquals(2, n2);
    }

    @Test
    void testDistanceTwoTimestampsWeekend() {
        Timestamp t1 = makeBasicStartTime(); // 2020-12-14T09:45:10.430
        Timestamp timeFriday = new Timestamp(t1.getTime()
                + (4 * makeTimeLength(24).getTime())); // 2020-12-18T05:45:10.430
        Timestamp timeMonday = new Timestamp(timeFriday.getTime()
                + (3 * makeTimeLength(24).getTime())); // 2020-12-21T02:45:10.430
        Generator test = makeGenerator();
        int n1 = test.calDistance(timeFriday, timeMonday);
        assertEquals(1, n1);
    }

    @Test
    void testNextDay() {
        Timestamp timeMonday = makeBasicStartTime();
        Timestamp timeTuesday = new Timestamp(timeMonday.getTime()
                + makeTimeLength(24).getTime());
        Generator test = makeGenerator();
        Timestamp supposedTuesday = test.nextDay(timeMonday);
        assertEquals(timeTuesday.getTime(), supposedTuesday.getTime());
    }

    @Test
    void testNextDayWeekend() {
        Timestamp t1 = makeBasicStartTime();
        Timestamp timeFriday = new Timestamp(t1.getTime()
                + (4 * makeTimeLength(24).getTime()));
        Timestamp timeMonday = new Timestamp(timeFriday.getTime()
                + (3 * makeTimeLength(24).getTime()));
        Generator test = makeGenerator();
        Timestamp supposedMonday = test.nextDay(timeFriday);
        assertEquals(timeMonday.getTime(), supposedMonday.getTime());
    }

    // test that it returns an empty list when all lectures are unscheduled
    @Test
    void testCreateTimetableNoneScheduled() {
        Generator test = makeGenerator();
        List<List<Lecture>> l = test.createTimeTable(createListLectures(),
                makeBasicStartTime(), 5);
        assertEquals(5, l.size());
        assertEquals(0, l.get(0).size());
    }

    // test that it creates a timetable when the lectures are scheduled
    @Test
    void testCreateTimetableAllScheduled() {
        Generator test = makeGenerator();
        List<List<Lecture>> l = test.createTimeTable(createListLecturesScheduled(),
                makeBasicStartTime(), 5);
        assertEquals(5, l.size());
        assertEquals(1, l.get(0).size());
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    void testOverlapSameLecture() {
        List<Lecture> lsc = createListLecturesScheduled();
        Lecture l = lsc.get(0);
        Generator test = makeGenerator();
        assertTrue(test.overlap(l, l.getStartTime(), l));
    }

    @Test
    void testOverlapBasicFalse() {
        List<Lecture> lsc = createListLecturesScheduled();
        Lecture l = lsc.get(0);
        Lecture ll = lsc.get(1);
        Generator test = makeGenerator();
        assertFalse(test.overlap(l, l.getStartTime(), ll));
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
        assertTrue(test.overlap(l, inMiddleOfLec, ll));
    }

    @Test
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

    @Test
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
        assertEquals(room, r);
    }
}
