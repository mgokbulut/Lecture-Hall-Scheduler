package nl.tudelft.unischeduler.database.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Time;
import java.sql.Timestamp;
import nl.tudelft.unischeduler.database.lecture.Lecture;
import nl.tudelft.unischeduler.database.lecture.LectureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;



@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LectureTest {
    @Autowired
    private transient LectureRepository lectureRepository;

    @Test
    public void saveAndRetrieveTest() {
        Lecture lecture = new Lecture(1L, 1L, 1L, "Test",
                new Timestamp(10L), new Time(10L), true);
        lectureRepository.save(lecture);
        Lecture test = lectureRepository.findById(lecture.getId()).get();
        assertEquals(lecture, test);
    }

    @Test
    public void equalsTest() {
        Lecture lecture = new Lecture(1L, 1L, 1L, "Test",
                new Timestamp(10L), new Time(10L), true);
        Lecture test = new Lecture(1L, 1L, 1L, "Test",
                new Timestamp(10L), new Time(10L), true);
        assertEquals(lecture, test);
    }
}
