package nl.tudelft.unischeduler.rules.servicestests;

import lombok.Getter;
import lombok.Setter;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.services.ClassRoomDatabaseService;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ClassRoomDatabaseServiceTest extends ServiceTest {
    @Getter @Setter
    ClassRoomDatabaseService classRoomDatabaseService;


    @BeforeEach
    public void setUp() {
        classRoomDatabaseService = new ClassRoomDatabaseService(databaseWebClient);
    }

    @Test
    public void getClassroomTest() {
        //this response is copied from a real response from the databaseService
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":1,"
                        + "\"capacity\":100,"
                        + "\"name\":\"Boole Hall\","
                        + "\"buildingName\":\"EWI\","
                        + "\"floor\":2}"));
        Room result = classRoomDatabaseService.getClassroom(1);
        Room expected = new Room(1, 100, "Boole Hall");
        Assertions.assertEquals(expected, result);
    }

    /**
     * Tests to see if the lectureId is actually sent through the request.
     *
     * @param lectureId the lectureId for which to test the method.
     */
    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void removeRoomFromLectureTest(int lectureId) {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":" + lectureId + ","
                        + "\"capacity\":100,"
                        + "\"name\":\"Boole Hall\","
                        + "\"buildingName\":\"EWI\","
                        + "\"floor\":2}"));
        boolean result = classRoomDatabaseService.removeRoomFromLecture(lectureId);
        Assertions.assertTrue(result);
    }
}
