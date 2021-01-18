package nl.tudelft.unischeduler.schedulegenerate.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseTest {

    private transient Course courseTest;

    @Before
    public void setUp() {
        courseTest = new Course(
                1L, "SEM", Collections.emptySet(), Collections.emptySet(), 2021);
    }

    @Test
    public void testEquals() {
        // Setup

        // Run the test
        final boolean result1 = courseTest.equals(new Course(
                        1L, "SEM", Collections.emptySet(), Collections.emptySet(), 2021));
        final boolean result2 = courseTest.equals("o");

        // Verify the results
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    public void testHashCode() {
        // Setup

        // Run the test
        final int result = courseTest.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(Objects.hash(1L, "SEM", Collections.emptySet(), Collections.emptySet()));
    }
}
