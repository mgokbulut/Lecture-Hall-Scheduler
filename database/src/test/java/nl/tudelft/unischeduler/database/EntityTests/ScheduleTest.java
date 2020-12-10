package nl.tudelft.unischeduler.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.unischeduler.database.Schedule.Schedule;
import nl.tudelft.unischeduler.database.Schedule.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ScheduleTest {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    public void saveAndRetrieve() {
    }
}
