package nl.tudelft.unischeduler.database.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import nl.tudelft.unischeduler.database.course.Course;
import nl.tudelft.unischeduler.database.lecture.Lecture;
import nl.tudelft.unischeduler.database.lecture.LectureController;
import nl.tudelft.unischeduler.database.lecture.LectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;



@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@ContextConfiguration(classes = LectureController.class)
@AutoConfigureMockMvc
@WebMvcTest
public class LectureControllerTest {
    @Autowired
    private transient WebApplicationContext webApplicationContext;

    @MockBean
    private transient LectureService lectureService;

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
                2020, Calendar.DECEMBER, 10, 0, 0).getTimeInMillis());

    private final transient ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private transient MockMvc mockMvc;

    private transient List<Lecture> lectures = new ArrayList<>(
            List.of(
                    new Lecture(0L, 0L, 0L, "sanders@tudelft.nl",
                            timestamp,  new Time(7200000),  false),
                    new Lecture(1L, 1L, 1L, "sanders@tudelft.nl",
                            new Timestamp(timestamp.getTime() + 10800000),
                            new Time(7200000), false),
                    new Lecture(2L, 2L, 2L, "sanders@tudelft.nl",
                            new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false)
            ));

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void setClassroomTest() throws Exception {
        String uri = "/lectures/setClassroom/2/1";
        Lecture lecture = new Lecture(2L, 1L, 2L, "sanders@tudelft.nl",
                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false);
        mockMvc.perform(put(uri).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lecture)))
                .andExpect(status().isOk());
    }

    @Test
    public void setLectureTimeTest() throws Exception {
        String uri = "/lectures/setTime/2/2020-12-11 00:00:00";
        Lecture lecture = new Lecture(2L, 1L, 2L, "sanders@tudelft.nl",
                new Timestamp(timestamp.getTime() + 86400000), new Time(7200000), false);
        mockMvc.perform(put(uri).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lecture)))
                .andExpect(status().isOk());
    }

    @Test
    public void setClassroomToEmpty() throws Exception {
        String uri = "/lectures/setClassroomToEmpty/2";
        Lecture lecture = new Lecture(2L, -1L, 2L, "sanders@tudelft.nl",
                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false);
        mockMvc.perform(put(uri).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lecture)))
                .andExpect(status().isOk());
    }


    @Disabled
    @Test
    public void getLecturesInCourse() throws Exception {
        when(lectureService.getLecturesInCourse(0L, timestamp,
                new Time(82800000))).thenReturn(lectures);
        String uri = "/lectures/0/2020-12-10 00:00:00/24:00:00";
        //http://localhost:8081/lectures/1/2020-12-10%2000:00:00/24:00:00

        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
                //.andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].classroom", is(0)))
                .andExpect(jsonPath("$[0].course", is(0)))
                .andExpect(jsonPath("$[0].teacher", is("sanders@tudelft.nl")))
                .andExpect(jsonPath("$[0].duration", is(new Time(7200000).toString())))
                .andExpect(jsonPath("$[0].movedOnline", is(false)))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].classroom", is(1)))
                .andExpect(jsonPath("$[1].course", is(1)))
                .andExpect(jsonPath("$[1].teacher", is("sanders@tudelft.nl")))
                .andExpect(jsonPath("$[1].duration", is(new Time(7200000).toString())))
                .andExpect(jsonPath("$[1].movedOnline", is(false)))
                .andExpect(jsonPath("$[2].id", is(2)))
                .andExpect(jsonPath("$[2].classroom", is(2)))
                .andExpect(jsonPath("$[2].course", is(2)))
                .andExpect(jsonPath("$[2].teacher", is("sanders@tudelft.nl")))
                .andExpect(jsonPath("$[2].duration", is(new Time(7200000).toString())))
                .andExpect(jsonPath("$[2].movedOnline", is(false)))
                .andExpect(status().isOk());

        verify(lectureService, times(1))
                .getLecturesInCourse(0L, timestamp, new Time(82800000)); //wanted
        verifyNoMoreInteractions(lectureService);
    }

    @Test
    public void getLecturesInCourseTest() throws Exception {
        Object[] a = {new Lecture(0L, 0L, 0L, "sanders@tudelft.nl",
                timestamp,  new Time(7200000),  false), new Course(0L, "AD", 2)};
        List<Object []> object = new ArrayList<>(List.<Object[]>of(a));

        when(lectureService.getLecturesWithCourses()).thenReturn(object);
        String uri = "/lectures/courses";

        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
                //.andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0][0].id", is(0)))
                .andExpect(jsonPath("$[0][0].classroom", is(0)))
                .andExpect(jsonPath("$[0][0].course", is(0)))
                .andExpect(jsonPath("$[0][0].teacher", is("sanders@tudelft.nl")))
                .andExpect(jsonPath("$[0][0].duration", is(new Time(7200000).toString())))
                .andExpect(jsonPath("$[0][0].movedOnline", is(false)))
                .andExpect(jsonPath("$[0][1].id", is(0)))
                .andExpect(jsonPath("$[0][1].name", is("AD")))
                .andExpect(jsonPath("$[0][1].year", is(2)))
                .andExpect(status().isOk());

        verify(lectureService, times(1)).getLecturesWithCourses();
        verifyNoMoreInteractions(lectureService);
    }

    @Test
    public void setLectureToOnlineTest() throws Exception{
        String uri =  "/lectures/setToOnline/sanders@tudelft.nl/2020-12-11 00:00:00/2020-12-11 00:45:00/true";
        Lecture lecture = new Lecture(2L, 1L, 2L, "sanders@tudelft.nl",
                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false);

        mockMvc.perform(put(uri).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lecture)))
                .andExpect(status().isOk());
    }

    @Test
    public void setLectureToOnlineTest2() throws Exception {
        String uri = "/lectures/setToOnline/2/true";
        Lecture lecture = new Lecture(2L, 1L, 2L, "sanders@tudelft.nl",
                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false);

        mockMvc.perform(put(uri).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lecture)))
                .andExpect(status().isOk());
    }

    @Test
    public void setLectureToOfflineTest() throws Exception {
        String uri = "/lectures/setToOffline/sanders@tudelft.nl/2020-12-11 00:00:00";
        Lecture lecture = new Lecture(2L, 1L, 2L, "sanders@tudelft.nl",
                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false);

        mockMvc.perform(put(uri).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lecture)))
                .andExpect(status().isOk());
    }

    @Test
    public void setLectureToOfflineTest2() throws Exception{
        String uri = "/lectures/setToOffline/2";
        Lecture lecture = new Lecture(2L, 1L, 2L, "sanders@tudelft.nl",
                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false);

        mockMvc.perform(put(uri).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lecture)))
                .andExpect(status().isOk());
    }

    @Test
    public void createLectureTest() throws Exception{
        String uri = "/lectures/create/2/sanders@tudelft.nl/2020-12-11 00:00:00/00:45:00/false";
        Lecture lecture = new Lecture(2L, 1L, 2L, "sanders@tudelft.nl",
                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false);

        mockMvc.perform(put(uri).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lecture)))
                .andExpect(status().isOk());
    }
}
