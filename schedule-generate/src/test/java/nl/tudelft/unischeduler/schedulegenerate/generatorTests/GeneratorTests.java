package nl.tudelft.unischeduler.schedulegenerate.generatorTests;

import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import org.junit.jupiter.api.Test;
import nl.tudelft.unischeduler.schedulegenerate.generator.Generator;

import java.sql.Timestamp;
import java.util.List;

import static nl.tudelft.unischeduler.schedulegenerate.generatorTests.Util.*;
import static org.junit.jupiter.api.Assertions.*;

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
        Timestamp tFriday = new Timestamp(t1.getTime()
                + (4 * makeTimeLength(24).getTime())); // 2020-12-18T05:45:10.430
        Timestamp tMonday = new Timestamp(tFriday.getTime()
                + (3 * makeTimeLength(24).getTime())); // 2020-12-21T02:45:10.430
         Generator test = makeGenerator();
        int n1 = test.calDistance(tFriday, tMonday);
        assertEquals(1, n1);
    }
}
