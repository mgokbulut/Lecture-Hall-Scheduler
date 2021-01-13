package nl.tudelft.unischeduler.rules.servicestests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.Getter;
import lombok.Setter;
import nl.tudelft.unischeduler.rules.entities.Student;
import nl.tudelft.unischeduler.rules.services.StudentDatabaseService;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentDatabaseServiceTest extends ServiceTest {

    @Getter
    @Setter
    StudentDatabaseService studentDatabaseService;


    @BeforeEach
    public void setUp() {
        studentDatabaseService = new StudentDatabaseService(databaseWebClient);
    }

    @Test
    public void getStudentTest() {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("{"
                + "\"netId\": \"a.jop@student.tudelft.nl\","
                + "\"type\": \"STUDENT\","
                + "\"interested\": true,"
                + "\"recovered\": \"true\""
                + "}"));
        Student result = studentDatabaseService.getStudent("a.jop@student.tudelft.nl");
        Student expected = new Student("a.jop@student.tudelft.nl", true, true);
        assertEquals(expected, result);
    }
}
