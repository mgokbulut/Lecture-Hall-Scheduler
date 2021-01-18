package nl.tudelft.unischeduler.schedulegenerate.entities;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class LectureTest {

    private transient Lecture lectureTest;

    @Before
    public void setUp() {
        lectureTest = new Lecture(1, 1,
                new Timestamp(1L), new Time(1l), false, 2021, new Room(1, 1, "Room"));
    }

    @Test
    public void testComputeEndTime() {
        Timestamp result = lectureTest.computeEndTime();

        assertThat(result.equals(new Timestamp(2L))).isTrue();
    }

    @Test
    public void testCompareTo() {
        // Setup
        Lecture yes = new Lecture(1, 1,
                new Timestamp(1L), new Time(1l), false, 2021, new Room(1, 1, "Room"));
        Lecture no = new Lecture(1, 1,
                new Timestamp(2L), new Time(2l), false, 2021, new Room(1, 1, "Room"));

        // Run the test
        final int result = lectureTest.compareTo(yes);
        final int result2 = lectureTest.compareTo(no);

        // Verify the results
        assertThat(result).isEqualTo(0);
        assertThat(result2).isEqualTo(-1);
    }
}
