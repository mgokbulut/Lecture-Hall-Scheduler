package nl.tudelft.unischeduler.database.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.unischeduler.database.schedule.Schedule;
import nl.tudelft.unischeduler.database.schedule.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ScheduleTest {
    @Autowired
    private transient ScheduleRepository scheduleRepository;

    @Test
    public void saveAndRetrieveTest() {
        Schedule schedule = new Schedule(1L, "Test");
        scheduleRepository.save(schedule);
        Schedule test = scheduleRepository.findById(schedule.getId()).get();
        assertEquals(schedule, test);
    }

    @Test
    public void equalsTest() {
        Schedule schedule = new Schedule(1L, "Test");
        Schedule test = new Schedule(1L, "Test");
        assertEquals(schedule, test);
    }
}
