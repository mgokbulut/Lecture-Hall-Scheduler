package nl.tudelft.unischeduler.schedulegenerate.entities;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTest {

    private transient Room roomUnderTest;

    @Before
    public void setUp() {
        roomUnderTest = new Room(0, 0, "name");
    }

    @Test
    public void testEquals() {
        // Setup

        // Run the test
        final boolean result1 =
                roomUnderTest.equals(new Room(0, 0, "name"));
        final boolean result2 =
                roomUnderTest.equals("o");

        // Verify the results
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    public void testCompareTo() {
        // Setup
        final Room otherRoom = new Room(0, 0, "name");

        // Run the test
        final int result = roomUnderTest.compareTo(otherRoom);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void testHashCode() {
        // Setup

        // Run the test
        final int result = roomUnderTest.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(3403498);
    }
}
