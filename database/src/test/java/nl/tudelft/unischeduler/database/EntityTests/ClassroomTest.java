package nl.tudelft.unischeduler.database.EntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.unischeduler.database.Classroom.Classroom;
import nl.tudelft.unischeduler.database.Classroom.ClassroomRepository;
import nl.tudelft.unischeduler.database.Lecture.Lecture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ClassroomTest {
    @Autowired
    private ClassroomRepository classroomRepository;

    @Test
    public void saveAndRetrieve() {
        Classroom classroom = new Classroom(1, 1, "Test", "Test", 1, new Set<Lecture>());
        classroomRepository.save(classroom);
        Classroom test = classroomRepository.findById(classroom.getId());
        assertEquals(classroom, test);
    }

    @Test
    public void equals() {
        Classroom classroom = new Classroom(1, 1, "Test", "Test", 1, new Set<Lecture>());
        Classroom test = new Classroom(1, 1, "Test", "Test", 1, new Set<Lecture>());
        assertEquals(classroom, test);
    }
}
