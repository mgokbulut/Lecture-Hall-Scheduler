package nl.tudelft.unischeduler.rules.coreTests;

import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;

import java.sql.Time;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class RulesModuleTest {

    transient String roomName = "testRoom";
    
    Room makeRoom() {
        return new Room(1, 200, roomName);
    }

    static RulesModule makeRulesModule() {
        Ruleset rs = new Ruleset();
        int[][] thresholds = {{0, 20},
                {200, 30},
                {400, 40}};
        rs.setMaxDays(20);
        rs.setBreakTime(600000);
        rs.setThresholds(thresholds);
        RulesModule rm = new RulesModule();
        rm.setRules(rs);
        return rm;
    }

    @Test
    void getCapacity() {
        RulesModule test = makeRulesModule();
        Room room = new Room(1, 300, roomName);
        int expected = 90;
        int actual = test.getCapacity(room.getCapacity());
        assertEquals(expected, actual);

    }

    @Test
    void getCapacity_WhenEqualToThreshold() {
        RulesModule test = makeRulesModule();
        Room room = new Room(1, 200, roomName);
        int expected = 60;
        int actual = test.getCapacity(room.getCapacity());
        assertEquals(expected, actual);
    }

    @Test
    void getCapacity_WhenAboveAllThresholds() {
        RulesModule test = makeRulesModule();
        Room room = new Room(1, 1000, roomName);
        int expected = 400;
        int actual = test.getCapacity(room.getCapacity());
        assertEquals(expected, actual);
    }

    @Test
    void getNextStartTime() {
        RulesModule test = makeRulesModule();
        Timestamp ts = new Timestamp(2000, 1, 1, 0, 0, 0, 0);
        Time t = new Time(300000);
        Lecture lecture = new Lecture(1, 10, ts, t, null);
        Timestamp expected = new Timestamp(2000, 1, 1, 0, 15, 0, 0);
        Timestamp actual = test.getNextStartTime(lecture);
        assertEquals(expected, actual);
    }

    @Test
    void availableForSignUp_AttendanceBelowMax() {
        RulesModule rs = makeRulesModule();
        Room room = new Room(1, 200, roomName);
        Lecture lecture = new Lecture(1, 59, null, null, null);
        lecture.setRoom(room);
        boolean expected = true;
        boolean actual = rs.availableForSignUp(lecture);
        assertEquals(expected, actual);
    }

    @Test
    void availableForSignUp_AttendanceAboveMax() {
        RulesModule rs = makeRulesModule();
        Room room = new Room(1, 200, roomName);
        Lecture lecture = new Lecture(1, 60, null, null, null);
        lecture.setRoom(room);
        boolean expected = false;
        boolean actual = rs.availableForSignUp(lecture);
        assertEquals(expected, actual);
    }

    @Test
    void availableForSignUp_AttendanceEqualToMax() {
        RulesModule rs = makeRulesModule();
        Room room = new Room(1, 200, roomName);
        Lecture lecture = new Lecture(1, 61, null, null, null);
        lecture.setRoom(room);
        boolean expected = false;
        boolean actual = rs.availableForSignUp(lecture);
        assertEquals(expected, actual);
    }

    @Test
    void overlap_L1BeforeL2_NoOverlap() {
        RulesModule rs = makeRulesModule();
        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 60, new Timestamp(670000), new Time(600000), null);
        boolean expected = false;
        boolean actual = rs.overlap(l1, l2);
        assertEquals(expected, actual);
    }

    @Test
    void overlap_L1BeforeL2_L1FinishesAsL2Starts() {
        RulesModule rs = makeRulesModule();
        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 60, new Timestamp(660000), new Time(600000), null);
        boolean expected = false;
        boolean actual = rs.overlap(l1, l2);
        assertEquals(expected, actual);
    }

    @Test
    void overlap_L1BeforeL2_Overlap() {
        RulesModule rs = makeRulesModule();
        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 60, new Timestamp(650000), new Time(600000), null);
        boolean expected = true;
        boolean actual = rs.overlap(l1, l2);
        assertEquals(expected, actual);
    }

    @Test
    void overlap_L2BeforeL1_NoOverlap() {
        RulesModule rs = makeRulesModule();
        Lecture l2 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l1 = new Lecture(1, 60, new Timestamp(670000), new Time(600000), null);
        boolean expected = false;
        boolean actual = rs.overlap(l1, l2);
        assertEquals(expected, actual);
    }

    @Test
    void overlap_L2BeforeL1_L2FinishesAsL1Starts() {
        RulesModule rs = makeRulesModule();
        Lecture l2 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l1 = new Lecture(1, 60, new Timestamp(660000), new Time(600000), null);
        boolean expected = false;
        boolean actual = rs.overlap(l1, l2);
        assertEquals(expected, actual);
    }

    @Test
    void overlap_L2BeforeL1_Overlap() {
        RulesModule rs = makeRulesModule();
        Lecture l2 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l1 = new Lecture(1, 60, new Timestamp(650000), new Time(600000), null);
        boolean expected = true;
        boolean actual = rs.overlap(l1, l2);
        assertEquals(expected, actual);
    }

    @Test
    void verifySchedule_SameRoom_NoOverlap_UnderCapacity() {

        RulesModule rs = makeRulesModule();

        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 60, new Timestamp(670000), new Time(600000), null);

        Room room = new Room(1, 400, roomName);

        l1.setRoom(room);
        l2.setRoom(room);

        boolean expected = true;
        boolean actual = rs.verifyLectures(new Lecture[]{l1, l2}).length == 0;
        assertEquals(expected, actual);
    }

    @Test
    void verifySchedule_SameRoom_NoOverlap_OverCapacity() {

        RulesModule rs = makeRulesModule();

        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 200, new Timestamp(670000), new Time(600000), null);

        Room room = new Room(1, 400, roomName);

        l1.setRoom(room);
        l2.setRoom(room);

        boolean expected = false;
        boolean actual = rs.verifyLectures(new Lecture[]{l1, l2}).length == 0;
        assertEquals(expected, actual);

    }

    @Test
    void verifySchedule_SameRoom_Overlap_UnderCapacity() {

        RulesModule rs = makeRulesModule();

        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 60, new Timestamp(600000), new Time(600000), null);

        Room room = new Room(1, 400, roomName);

        l1.setRoom(room);
        l2.setRoom(room);

        boolean expected = false;
        boolean actual = rs.verifyLectures(new Lecture[]{l1, l2}).length == 0;
        assertEquals(expected, actual);

    }

    @Test
    void verifySchedule_SameRoom_Overlap_OverCapacity() {

        RulesModule rs = makeRulesModule();

        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 200, new Timestamp(600000), new Time(600000), null);

        Room room = new Room(1, 400, roomName);

        l1.setRoom(room);
        l2.setRoom(room);

        boolean expected = false;
        boolean actual = rs.verifyLectures(new Lecture[]{l1, l2}).length == 0;
        assertEquals(expected, actual);

    }

    @Test
    void verifySchedule_DifferentRoom_NoOverlap_UnderCapacity() {

        RulesModule rs = makeRulesModule();

        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 60, new Timestamp(670000), new Time(600000), null);

        Room room1 = new Room(1, 400, roomName);
        Room room2 = new Room(2, 400, roomName);
        l1.setRoom(room1);
        l2.setRoom(room2);

        boolean expected = true;
        boolean actual = rs.verifyLectures(new Lecture[]{l1, l2}).length == 0;
        assertEquals(expected, actual);

    }

    @Test
    void verifySchedule_DifferentRoom_NoOverlap_OverCapacity() {

        RulesModule rs = makeRulesModule();

        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 200, new Timestamp(670000), new Time(600000), null);

        Room room1 = new Room(1, 400, roomName);
        Room room2 = new Room(2, 400, roomName);
        l1.setRoom(room1);
        l2.setRoom(room2);

        boolean expected = false;
        boolean actual = rs.verifyLectures(new Lecture[]{l1, l2}).length == 0;
        assertEquals(expected, actual);

    }

    @Test
    void verifySchedule_DifferentRoom_Overlap_UnderCapacity() {

        RulesModule rs = makeRulesModule();

        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 60, new Timestamp(600000), new Time(600000), null);

        Room room1 = new Room(1, 400, roomName);
        Room room2 = new Room(2, 400, roomName);
        l1.setRoom(room1);
        l2.setRoom(room2);

        boolean expected = true;
        boolean actual = rs.verifyLectures(new Lecture[]{l1, l2}).length == 0;
        assertEquals(expected, actual);

    }

    @Test
    void verifySchedule_DifferentRoom_Overlap_OverCapacity() {

        RulesModule rs = makeRulesModule();

        Lecture l1 = new Lecture(1, 60,
                new Timestamp(0), new Time(60000), null);
        Lecture l2 = new Lecture(1, 200, new Timestamp(600000), new Time(600000), null);

        Room room1 = new Room(1, 400, roomName);
        Room room2 = new Room(2, 400, roomName);
        l1.setRoom(room1);
        l2.setRoom(room2);

        boolean expected = false;
        boolean actual = rs.verifyLectures(new Lecture[]{l1, l2}).length == 0;
        assertEquals(expected, actual);

    }


}