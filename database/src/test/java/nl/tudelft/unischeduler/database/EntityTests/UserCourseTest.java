package nl.tudelft.unischeduler.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.unischeduler.database.UserCourse.UserCourse;
import nl.tudelft.unischeduler.database.UserCourse.UserCourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserCourseTest {
    @Autowired
    private UserCourseRepository userCourseRepository;

    @Test
    public void saveAndRetrieve() {
    }
}
