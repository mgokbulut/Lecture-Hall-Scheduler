package nl.tudelft.unischeduler.database.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleId;
import nl.tudelft.unischeduler.database.usercourse.UserCourse;
import nl.tudelft.unischeduler.database.usercourse.UserCourseId;
import nl.tudelft.unischeduler.database.usercourse.UserCourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserCourseTest {
    @Autowired
    private transient UserCourseRepository userCourseRepository;

    @Test
    public void saveAndRetrieveTest() {
        UserCourse userCourse = new UserCourse("Test", 1L);
        userCourseRepository.save(userCourse);
        UserCourse test = userCourseRepository
                .findById(new UserCourseId(userCourse.getNetId(), userCourse.getCourseId())).get();
        assertEquals(userCourse, test);
    }

    @Test
    public void equalsTest() {
        UserCourse userCourse = new UserCourse("Test", 1L);
        UserCourse test = new UserCourse("Test", 1L);
        assertEquals(userCourse, test);
    }

    @Test
    public void constructorTest() {
        assertEquals(new UserCourseId().getCourseId(),
                new UserCourseId().getCourseId());
        assertEquals(new UserCourseId().getNetId(),
                new UserCourseId().getNetId());
    }
}
