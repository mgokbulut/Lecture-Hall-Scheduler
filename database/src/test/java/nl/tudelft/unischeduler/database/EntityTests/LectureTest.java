package nl.tudelft.unischeduler.database.EntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.unischeduler.database.Lecture.Lecture;
import nl.tudelft.unischeduler.database.Lecture.LectureRepository;
import java.sql.Time;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LectureTest {
    @Autowired
    private LectureRepository lectureRepository;

    @Test
    public void saveAndRetrieve() {
        Lecture lecture = new Lecture(1, 1, 1, "Test", new Timestamp(), new Time(), true);
        lectureRepository.save(lecture);
        Lecture test = lectureRepository.findById(lecture.getId());
        assertEquals(lecture, test);
    }

    @Test
    public void equals() {
        Lecture lecture = new Lecture(1, 1, 1, "Test", new Timestamp(), new Time(), true);
        Lecture test = new Lecture(1, 1, 1, "Test", new Timestamp(), new Time(), true);
        assertEquals(lecture, test);
    }
}
