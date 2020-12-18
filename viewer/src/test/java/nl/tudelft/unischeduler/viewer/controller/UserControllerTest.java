package nl.tudelft.unischeduler.viewer.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.sql.Date;

import lombok.Data;
import nl.tudelft.unischeduler.viewer.entities.User;
import nl.tudelft.unischeduler.viewer.controllers.UserController;
import nl.tudelft.unischeduler.viewer.services.DatabaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

public class UserControllerTest extends ControllerTest{

    @Test
    public void getLecturesInCourseTest() throws Exception {
        String uri = "/lectureSchedules/course/1";
        User user = new User("a.baran@student.tudelft.nl", "STUDENT", new Date(10L));

        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void getPossibleLecturesTest() throws Exception{
        String uri = "/userCourseService/possibleLectures/a.baran@student.tudelft.nl";
        User user = new User("a.baran@student.tudelft.nl", "STUDENT", new Date(10L));

        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentScheduleTest() throws Exception{
        String uri = "/lectureSchedules/a.baran@student.tudelft.nl";
        User user = new User("a.baran@student.tudelft.nl", "STUDENT", new Date(10L));

        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void getTeacherScheduleTest() throws Exception{
        String uri = "/lectureSchedules/teacher/a.baran@student.tudelft.nl";
        User user = new User("a.baran@student.tudelft.nl", "STUDENT", new Date(10L));

        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsInLectureTest() throws Exception{
        String uri = "/lectureSchedules/studentsLecture/1";
        User user = new User("a.baran@student.tudelft.nl", "STUDENT", new Date(10L));

        mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }
}
