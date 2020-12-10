package nl.tudelft.unischeduler.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.unischeduler.database.LectureSchedule.LectureSchedule;
import nl.tudelft.unischeduler.database.LectureSchedule.LectureScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LectureScheduleTest {
    @Autowired
    private LectureScheduleRepository lectureScheduleRepository;

    @Test
    public void saveAndRetrieve() {
    }
}
