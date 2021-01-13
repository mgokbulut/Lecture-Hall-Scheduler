package nl.tudelft.unischeduler.rules.servicestests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.services.LectureDatabaseService;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LectureDatabaseServiceTest extends ServiceTest{
    @Getter
    @Setter
    LectureDatabaseService lectureDatabaseService;


    @BeforeEach
    public void setUp() {
        lectureDatabaseService = new LectureDatabaseService(databaseWebClient);
    }

    public String readFile(String fileName) throws FileNotFoundException {
        try(Scanner scanner = new Scanner(new File(fileName))) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    @Test
    public void getLecturesTest() throws FileNotFoundException {
        String response = readFile("src/test/resources/get-lectures-response.json");
        server.enqueue(new MockResponse()

                .setHeader("Content-Type", "application/json")
                .setBody(response));
        Lecture[] result = lectureDatabaseService.getLectures();
        assertEquals(14, result.length);
    }

    @Test
    public void removeLectureFromScheduleTest() {
        server.enqueue(standardResponse);
        boolean result = lectureDatabaseService.removeLectureFromSchedule(5);
        assertTrue(result);
    }

    @Test
    public void removeLectureFromScheduleFailTest() {
        server.enqueue(new MockResponse().setBody("false"));
        boolean result = lectureDatabaseService.removeLectureFromSchedule(5);
        assertFalse(result);
    }

    @Test
    public void removeLectureFromScheduleNullTest() {
        server.enqueue(new MockResponse().setBody(""));
        boolean result = lectureDatabaseService.removeLectureFromSchedule(5);
        assertFalse(result);
    }
}
