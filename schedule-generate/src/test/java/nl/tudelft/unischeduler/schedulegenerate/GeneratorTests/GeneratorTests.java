package nl.tudelft.unischeduler.schedulegenerate.GeneratorTests;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.unischeduler.schedulegenerate.entities.Course;
import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Room;
import nl.tudelft.unischeduler.schedulegenerate.entities.Util;
import nl.tudelft.unischeduler.schedulegenerate.generator.Generator;
import org.junit.jupiter.api.Test;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeBasicStartTime;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeGenerator;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeRoom;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.makeTimeLength;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.createListCourses;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.createListLecturesScheduled;
import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.createListLectures;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class GeneratorTests {





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
