package nl.tudelft.unischeduler.database.EntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.unischeduler.database.course.Course;
import nl.tudelft.unischeduler.database.course.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseTest {
    @Autowired
    private transient CourseRepository courseRepository;

    @Test
    public void saveAndRetrieve() {
        Course course = new Course(1L, "Test", 1);
        courseRepository.save(course);
        Course test = courseRepository.findById(course.getId()).get();
        assertEquals(course, test);
    }

    @Test
    public void equals() {
        Course course = new Course(1L, "Test", 1);
        Course test = new Course(1L, "Test", 1);
        assertEquals(course, test);
    }
}
