package nl.tudelft.unischeduler.schedulegenerate.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class CourseTest {

    private transient Course test;

    @Before
    public void setUp() {
        test = new Course(
                1L, "SEM", Collections.emptySet(), Collections.emptySet(), 2021);
    }

    @Test
    public void testGetId() {
        Long result = test.getId();

        assertThat(result == 1L).isTrue();
    }

    @Test
    public void testGetName() {
        String result = test.getName();

        assertThat(result.equals("SEM")).isTrue();
    }

    @Test
    public void testEquals() {
        // Setup

        // Run the test
        final boolean result1 = test.equals(new Course(
                        1L, "SEM", Collections.emptySet(), Collections.emptySet(), 2021));
        final boolean result2 = test.equals(new Course(
                2L, "AD", Collections.emptySet(), Collections.emptySet(), 2021));

        // Verify the results
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    public void testHashCode() {
        // Setup

        // Run the test
        final int result = test.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(Objects.hash(1L, "SEM", Collections.emptySet(), Collections.emptySet()));
    }
}
