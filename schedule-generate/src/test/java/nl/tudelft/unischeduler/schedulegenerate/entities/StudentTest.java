package nl.tudelft.unischeduler.schedulegenerate.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {

    private transient Student studentUnderTest;

    @Before
    public void setUp() {
        studentUnderTest = new Student("netId", "type",
                false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
    }

    @Test
    public void testEquals() {
        // Setup

        // Run the test
        final boolean result = studentUnderTest.equals("o");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testGetNetId() {
        // Setup

        // Run the test
        final String result = studentUnderTest.getNetId();

        // Verify the results
        assertThat(result).isEqualTo("netId");
    }

    @Test
    public void testGetType() {
        // Setup

        // Run the test
        final String result = studentUnderTest.getType();

        // Verify the results
        assertThat(result).isEqualTo("type");
    }

    @Test
    public void testIsInterested() {
        // Setup

        // Run the test
        final boolean result = studentUnderTest.isInterested();

        // Verify the results
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testSetInterested() {
        // Setup

        // Run the test
        studentUnderTest.setInterested(true);
        final boolean result = studentUnderTest.isInterested();

        // Verify the results
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testSetType() {
        // Setup

        // Run the test
        studentUnderTest.setType("yellow");
        final String result = studentUnderTest.getType();

        // Verify the results
        assertThat(result).isEqualTo("yellow");
    }

    @Test
    public void testHashCode() {
        // Setup

        // Run the test
        final int result = studentUnderTest.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(521673814);
    }

    @Test
    public void testCompareTo() {
        // Setup
        final Student otherStudent = new Student("netId", "type", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());

        // Run the test
        final int result = studentUnderTest.compareTo(otherStudent);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }
}
