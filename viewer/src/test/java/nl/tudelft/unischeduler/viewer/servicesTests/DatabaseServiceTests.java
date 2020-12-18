package nl.tudelft.unischeduler.viewer.servicesTests;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import nl.tudelft.unischeduler.viewer.entities.Classroom;
import nl.tudelft.unischeduler.viewer.entities.Lecture;
import nl.tudelft.unischeduler.viewer.entities.User;
import nl.tudelft.unischeduler.viewer.services.DatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


//@ContextConfiguration(classes = DatabaseService.class)
//@AutoConfigureMockMvc
//@WebMvcTest
public class DatabaseServiceTests {

    //@Autowired
    private transient WebClient.Builder webClientBuilder;

    //@Autowired
    //@MockBean
    private transient DatabaseService databaseService;

    private transient MockMvc mockMvc;

    private transient List<Object[]> lectures = new ArrayList<>(
            List.of(
                    new Object[]{
                            new Lecture(0l, null, null, "testTeacher1", new Timestamp(1l), new Time(2l), true),
                            new Classroom(1l, 1, "testName1", "testBuildingName1", 1)
                    },
                    new Object[]{
                            new Lecture(2l, null, null, "testTeacher2", new Timestamp(1l), new Time(2l), true),
                            new Classroom(3l, 2, "testName2", "testBuildingName2", 1)
                    }
            )
    );

    private transient List<Object[]> users = new ArrayList<>(
            List.of(
                    new Object[] {
                            new User("testUser1", "TestType1", new Date(0l)),
                            true
                    },
                    new Object[] {
                            new User("testUser2", "TestType2", new Date(1l)),
                            true
                    }
            )
    );

    @BeforeEach
    public void setup() {
        webClientBuilder = Mockito.mock(WebClient.Builder.class);
        WebClient webClient = Mockito.mock(WebClient.class);
        //webClientBuilder.baseUrl("http://database-service/");
        databaseService = new DatabaseService(webClientBuilder, webClient);
    }

    @Test
    void getLecturesTest() {

        Lecture[] expected = new Lecture[]{
                new Lecture(0l,
                        new Classroom(1l, 1, "testName1", "testBuildingName1", 1),
                        null, "testTeacher1", new Timestamp(1l), new Time(2l), true),
                new Lecture(2l,
                        new Classroom(3l, 2, "testName2", "testBuildingName2", 1),
                        null, "testTeacher2", new Timestamp(1l), new Time(2l), true)
        };

        Lecture[] actual = databaseService.getLectures(lectures);
        for(int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i]);
        }


    }

    @Test
    void getStudentsTest() {
        User[] expected = new User[] {
                new User("testUser1", "TestType1", new Date(0l)),
                new User("testUser2", "TestType2", new Date(1l))
        };
        User[] actual = databaseService.getUsers(users);
        for(int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    //@Test
    void getStudentScheduleTest() {

        String netId = "test";

        when(webClientBuilder.build()
                .get()
                .uri("lectureSchedules/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block())
                .thenReturn(lectures);
        Lecture[] expected = databaseService.getStudentSchedule(netId).getBody();
        Lecture[] actual = databaseService.getLectures(lectures);
        assertEquals(expected, actual);
    }
}
