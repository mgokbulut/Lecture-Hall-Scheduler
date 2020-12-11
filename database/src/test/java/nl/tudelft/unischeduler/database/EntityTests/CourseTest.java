package nl.tudelft.unischeduler.database.EntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.unischeduler.database.Course.Course;
import nl.tudelft.unischeduler.database.Course.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CourseTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void saveAndRetrieve() {
        Course course = new Course(1, "Test", 1);
        courseRepository.save(course);
        Course test = courseRepository.findById(course.getId());
        assertEquals(course, test);
    }

    @Test
    public void equals() {
        Course course = new Course(1, "Test", 1);
        Course test = new Course(1, "Test", 1);
        assertEquals(course, test);
    }
}
