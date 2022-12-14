package nl.tudelft.unischeduler.database.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import nl.tudelft.unischeduler.database.lectureschedule.LectureSchedule;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleController;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleRepository;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleService;
import nl.tudelft.unischeduler.database.schedule.Schedule;
import nl.tudelft.unischeduler.database.schedule.ScheduleRepository;
import nl.tudelft.unischeduler.database.user.User;
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

    @MockBean
    private transient LectureScheduleRepository lectureScheduleRepository;

    @MockBean
    private transient ScheduleRepository scheduleRepository;

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 1).getTimeInMillis());


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

    @Test
    public void  cancelStudentAttendanceTest() throws Exception {
        String uri = "/lectureSchedules/remove/byarar/2020-12-11 00:00:00/2020-12-11 00:45:00";
        Optional<LectureSchedule> lectureSchedule = Optional.of(new LectureSchedule(0L, 1L));

        mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lectureSchedule)))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentScheduleTest() throws Exception {
        String uri = "/lectureSchedules/student/byarar";
        Optional<LectureSchedule> lectureSchedule = Optional.of(new LectureSchedule(0L, 1L));

        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lectureSchedule)))
                .andExpect(status().isOk());
    }

    @Test
    public void removeLectureFromScheduleTest() throws Exception {
        String uri = "/lectureSchedules/remove/0";
        Optional<LectureSchedule> lectureSchedule = Optional.of(new LectureSchedule(0L, 1L));

        mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lectureSchedule)))
                .andExpect(status().isOk());
    }

    @Test
    public void removeStudentFromLectureTest() throws Exception {
        String uri = "/lectureSchedules/remove/1/0";
        Optional<LectureSchedule> lectureSchedule = Optional.of(new LectureSchedule(0L, 1L));

        mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lectureSchedule)))
                .andExpect(status().isOk());
    }

    @Test
    public void getTeacherScheduleTest() throws Exception {
        String uri = "/lectureSchedules/teacher/byarar";
        Optional<LectureSchedule> lectureSchedule = Optional.of(new LectureSchedule(0L, 1L));

        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lectureSchedule)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllLecturesInCourseTest() throws Exception {
        String uri = "/lectureSchedules/course/1";
        Optional<LectureSchedule> lectureSchedule = Optional.of(new LectureSchedule(0L, 1L));

        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(lectureSchedule)))
                .andExpect(status().isOk());
    }
}
