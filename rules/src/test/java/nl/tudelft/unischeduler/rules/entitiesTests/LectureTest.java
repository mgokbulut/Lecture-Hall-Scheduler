package nl.tudelft.unischeduler.rules.entitiesTests;

import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;

import java.sql.Time;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class LectureTest {

    static Lecture makeLecture() {
        Timestamp ts = new Timestamp(2000, 1, 1, 0, 0, 0, 0);
        Time t = new Time(1, 0, 0);
        Lecture test = new Lecture(1, 10, ts, t);
        return test;
    }

    @Test
    void getId() {
        Lecture test = makeLecture();
        int expected = 1;
        int actual = test.getId();
        assertEquals(expected, actual);
    }

    @Test
    void setId() {
        Lecture test = makeLecture();
        test.setId(100);
        int expected = 100;
        int actual = test.getId();
        assertEquals(expected, actual);
    }

    @Test
    void getAttendance() {
        Lecture test = makeLecture();
        int expected = 10;
        int actual = test.getAttendance();
        assertEquals(expected, actual);
    }

    @Test
    void setAttendance() {
        Lecture test = makeLecture();
        test.setAttendance(2020);
        int expected = 2020;
        int actual = test.getAttendance();
        assertEquals(expected, actual);
    }

    @Test
    void getStartTime() {
        Lecture test = makeLecture();
        Timestamp expected = new Timestamp(2000, 1, 1, 0, 0, 0, 0);
        Timestamp actual = test.getStartTime();
        assertEquals(expected, actual);
    }

    @Test
    void setStartTime() {
        Lecture test = makeLecture();
        Timestamp expected = new Timestamp(2020, 12, 25, 0, 0, 0, 0);
        test.setStartTime(expected);
        Timestamp actual = test.getStartTime();
        assertEquals(expected, actual);
    }

    @Test
    void getDuration() {
        Lecture test = makeLecture();
        Time expected = new Time(1, 0, 0);
        Time actual = test.getDuration();
        assertEquals(expected, actual);
    }

    @Test
    void setDuration() {
        Lecture test = makeLecture();
        Time expected = new Time(1, 1, 1);
        test.setDuration(expected);
        Time actual = test.getDuration();
        assertEquals(expected, actual);
    }

    @Test
    void setRoom() {
        Lecture test = makeLecture();
        Room expected = new Room(1, 10, "ExpectedRoom");
        test.setRoom(expected);
        Room actual = test.getRoom();
        assertEquals(expected, actual);
    }
}