package nl.tudelft.unischeduler.database.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import nl.tudelft.unischeduler.database.LectureSchedule.LectureSchedule;
import nl.tudelft.unischeduler.database.LectureSchedule.LectureScheduleController;
import nl.tudelft.unischeduler.database.LectureSchedule.LectureScheduleRepository;
import nl.tudelft.unischeduler.database.LectureSchedule.LectureScheduleService;
import nl.tudelft.unischeduler.database.Schedule.Schedule;
import nl.tudelft.unischeduler.database.Schedule.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


@ContextConfiguration(classes = LectureScheduleController.class)
@AutoConfigureMockMvc
@WebMvcTest
public class LectureScheduleControllerTest {
    @Autowired
    private transient WebApplicationContext webApplicationContext;

    @MockBean
    private transient LectureScheduleService lectureScheduleService;

    private final transient ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule());

    private transient MockMvc mockMvc;

    private transient LectureSchedule lectureSchedule = new LectureSchedule(0L, 1L);

    @MockBean //not sure if this is needed
    private transient LectureScheduleRepository lectureScheduleRepository;

    @MockBean //not sure if this is needed
    private transient ScheduleRepository scheduleRepository;

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 1).getTimeInMillis());

    //private final transient User user =
    // new User("a.kuba@student.tudelft.nl","STUDENT",true,timestamp);

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void assignStudentToLectureTest() throws Exception {
        String uri = "/lectureSchedules/a.kuba@student.tudelft.nl/0";
        Optional<Schedule> schedule = Optional.of(new Schedule(1L, "a.kuba@student.tudelft.nl"));
        Optional<LectureSchedule> lectureSchedule = Optional.of(new LectureSchedule(0L, 1L));

        when(scheduleRepository.findByUser("a.kuba@student.tudelft.nl"))
                .thenReturn(schedule);
        when(lectureScheduleRepository.findByLectureIdAndScheduleId(0L, 1L))
                .thenReturn(lectureSchedule);

        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lectureSchedule)))
                .andExpect(status().isOk());
    }

    //    @Test
    //    public void removeLectureFromScheduleTest() throws Exception {
    //        String uri = "/lectureSchedules/remove/0";
    //        Optional<LectureSchedule> lectureSchedule = Optional.of(new LectureSchedule(0L,1L));
    ////        when(lectureScheduleService.removeLectureFromSchedule(0L))
    ////                .thenReturn((lectureSchedule));
    // "Lecture successfully deleted from all the schedules");
    //
    //
    //        mockMvc.perform(delete(uri, lectureSchedule.get().getLectureId())
    //                .contentType(MediaType.APPLICATION_JSON_VALUE)
    //                .content(objectMapper.writeValueAsString(lectureSchedule)))
    //                .andExpect(status().isNoContent());
    //    }
}
