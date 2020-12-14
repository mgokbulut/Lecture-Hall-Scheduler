package nl.tudelft.unischeduler.database.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import nl.tudelft.unischeduler.database.lecture.Lecture;
import nl.tudelft.unischeduler.database.lecture.LectureController;
import nl.tudelft.unischeduler.database.lecture.LectureService;
import org.junit.jupiter.api.BeforeEach;
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
            2020, Calendar.DECEMBER, 9, 0, 0).getTimeInMillis());

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private transient MockMvc mockMvc;

    private transient List<Lecture> lectures = new ArrayList<>(
        List.of(
                new Lecture(0L, 0L, 0L, "sanders@tudelft.nl",
                        timestamp,  new Time(7200000),  false),
                new Lecture(1L, 1L, 0L, "sanders@tudelft.nl",
                        new Timestamp(timestamp.getTime() + 10800000), new Time(7200000), false),
                new Lecture(2L, 2L, 0L, "sanders@tudelft.nl",
                        new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false)
        ));

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    //TODO

    //    @Test
    //    public void getLecturesInCourse() throws Exception {                    //24h
    //        when(lectureService.getLecturesInCourse(0L,
    //        timestamp,new Time(86400000))).thenReturn(lectures);
    //        String uri = "/lectures/0/2020-12-10 00:00:00/24:00:00";
    //        //http://localhost:8081/lectures/0/2020-12-10%2000:00:00/24:00:00
    //
    //        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
    //                .andDo(print())
    //                .andExpect(jsonPath("$").exists())
    //                .andExpect(jsonPath("$[0].id", is(0)))
    //                .andExpect(jsonPath("$[0].classroom", is(0)))
    //                .andExpect(jsonPath("$[0].course", is(0)))
    //                .andExpect(jsonPath("$[0].teacher", is("sanders@tudelft.nl")))
    //                .andExpect(jsonPath("$[0].startTimeDate", is(timestamp)))
    //                .andExpect(jsonPath("$[0].duration", is(new Time(7200000))))
    //                .andExpect(jsonPath("$[0].movedOnline", is(false)))
    //                .andExpect(status().isOk());
    //
    //        verify(lectureService, times(1)).getLecturesInCourse(0L,timestamp,new Time(86400000));
    //        verifyNoMoreInteractions(lectureService);
    //    }
}
