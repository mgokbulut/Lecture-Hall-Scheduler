package nl.tudelft.unischeduler.rules.entitiestests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.unischeduler.rules.entities.Room;
import org.junit.jupiter.api.Test;


class RoomTest {

    static Room makeRoom() {
        return new Room(1, 10, "testRoom");
    }

    @Test
    void getId() {
        Room test = makeRoom();
        int expected = 1;
        int actual = test.getId();
        assertEquals(expected, actual);
    }

    @Test
    void setId() {
        Room test = makeRoom();
        int expected = 99;
        test.setId(expected);
        int actual = test.getId();
        assertEquals(expected, actual);
    }

    @Test
    void getCapacity() {
        Room test = makeRoom();
        int expected = 10;
        int actual = test.getCapacity();
        assertEquals(expected, actual);
    }

    @Test
    void setCapacity() {
        Room test = makeRoom();
        int expected = 99;
        test.setCapacity(expected);
        int actual = test.getCapacity();
        assertEquals(expected, actual);
    }

    @Test
    void getName() {
        Room test = makeRoom();
        String expected = test.getName();
        String actual = "testRoom";
        assertEquals(expected, actual);
    }

    @Test
    void setName() {
        Room test = makeRoom();
        String expected = "fakeRoom";
        test.setName(expected);
        String actual = test.getName();
        assertEquals(expected, actual);
    }
}