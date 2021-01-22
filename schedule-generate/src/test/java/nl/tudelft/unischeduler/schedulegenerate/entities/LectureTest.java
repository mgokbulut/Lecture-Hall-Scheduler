package nl.tudelft.unischeduler.schedulegenerate.entities;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class LectureTest {

    private transient Lecture test;

    @Before
    public void setUp() {
        test = new Lecture(1, 1,
                new Timestamp(1L), new Time(1L), true, 2021, new Room(1, 1, "Room"));
    }

    @Test
    public void testGetId() {
        int result = test.getId();

        assertThat(result == 1).isTrue();
    }

    @Test
    public void testGetAttendance() {
        int result = test.getAttendance();

        assertThat(result == 1).isTrue();
    }

    @Test
    public void testGetYear() {
        int result = test.getYear();

        assertThat(result == 2021).isTrue();
    }

    @Test
    public void testComputeEndTime() {
        Timestamp result = test.computeEndTime();

        assertThat(result.equals(new Timestamp(2L))).isTrue();
    }

    @Test
    public void testCompareTo() {
        // Setup
        Lecture yes = new Lecture(1, 1,
                new Timestamp(1L), new Time(1L), false, 2021, new Room(1, 1, "Room"));
        Lecture no = new Lecture(1, 1,
                new Timestamp(2L), new Time(2L), false, 2021, new Room(1, 1, "Room"));

        // Run the test
        final int result = test.compareTo(yes);
        final int result2 = test.compareTo(no);

        // Verify the results
        assertThat(result).isEqualTo(0);
        assertThat(result2).isEqualTo(-1);
    }
}
