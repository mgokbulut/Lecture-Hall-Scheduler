package nl.tudelft.unischeduler.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTests {
    @BeforeEach
    public void setup() {
    }

    @Test
    public void constructcorTest() {
        User testUser = new User();
        assertEquals(testUser.getNetId(), null);
    }

    @Test
    public void testAuthenticationRole() {
        User student = new User("user", "pass", "STUDENT");
        User teacher = new User("user", "pass", "TEACHER");
        User facultyMember = new User("user", "pass", "FACULTY_MEMBER");

        assertEquals(student.authenticationRole(), "ROLE_STUDENT");
        assertEquals(teacher.authenticationRole(), "ROLE_TEACHER");
        assertEquals(facultyMember.authenticationRole(), "ROLE_ADMIN");
    }

    @Test
    public void testStaticVariables() {
        assertEquals(User.ROLE_FAC_MEMBER, "FACULTY_MEMBER");
        assertEquals(User.ROLE_STUDENT, "STUDENT");
        assertEquals(User.ROLE_TEACHER, "TEACHER");
    }
}
