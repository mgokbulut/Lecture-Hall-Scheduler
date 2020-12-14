package nl.tudelft.unischeduler.database.EntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.unischeduler.database.lectureschedule.LectureSchedule;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LectureScheduleTest {
    @Autowired
    private transient LectureScheduleRepository lectureScheduleRepository;

    @Test
    public void saveAndRetrieve() {
        LectureSchedule lectureSchedule = new LectureSchedule(1L, 1L);
        lectureScheduleRepository.save(lectureSchedule);
        LectureSchedule test = lectureScheduleRepository
                .findByLectureIdAndScheduleId(lectureSchedule.getLectureId(),
                lectureSchedule.getScheduleId()).get();
        assertEquals(lectureSchedule, test);
    }

    @Test
    public void equals() {
        LectureSchedule lectureSchedule = new LectureSchedule(1L, 1L);
        LectureSchedule test = new LectureSchedule(1L, 1L);
        assertEquals(lectureSchedule, test);
    }
}
