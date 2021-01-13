package nl.tudelft.unischeduler.schedulegenerate.UtilTests;

import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Util;
import nl.tudelft.unischeduler.schedulegenerate.generator.Generator;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static nl.tudelft.unischeduler.schedulegenerate.Utility.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
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

}
