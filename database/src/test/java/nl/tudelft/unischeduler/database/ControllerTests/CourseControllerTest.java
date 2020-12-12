package nl.tudelft.unischeduler.database.ControllerTests;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import nl.tudelft.unischeduler.database.Classroom.Classroom;
import nl.tudelft.unischeduler.database.Course.Course;
import nl.tudelft.unischeduler.database.Course.CourseRepository;
import nl.tudelft.unischeduler.database.Course.CourseController;
import nl.tudelft.unischeduler.database.Course.CourseService;
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

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = CourseController.class)
@AutoConfigureMockMvc
@WebMvcTest
public class CourseControllerTest {
    @Autowired
    private transient WebApplicationContext webApplicationContext;

    @Autowired
    @MockBean
    private transient CourseService courseService;

    private transient MockMvc mockMvc;

    private transient List<Course> courses = new ArrayList<>(
            List.of(
                    new Course(0L,"ADS",1),
                    new Course(1L,"SEM",2),
                    new Course(2L,"AD",2)
            ));

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllCoursesIdsTest() throws Exception {
        when(courseService.getAllCourses()).thenReturn(courses);
        String uri = "/courses/all";

        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].name", is("ADS")))
                .andExpect(jsonPath("$[0].year", is(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[1].name", is("SEM")))
                .andExpect(jsonPath("$[1].year", is(2)))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[2].name", is("AD")))
                .andExpect(jsonPath("$[2].year", is(2)))
                .andExpect(jsonPath("$[2].id", is(2)))
                .andExpect(status().isOk());

        verify(courseService, times(1)).getAllCourses();
        verifyNoMoreInteractions(courseService);
    }

    @Test
    public void getClassroomTest() throws Exception {
        when(courseService.getCourse(1L)).thenReturn(courses.get(1));
        String uri = "/courses/1";

        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", is("SEM")))
                .andExpect(jsonPath("$.year", is(2)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(status().isOk());

        verify(courseService, times(1)).getCourse(1L);
        verifyNoMoreInteractions(courseService);
    }
}
