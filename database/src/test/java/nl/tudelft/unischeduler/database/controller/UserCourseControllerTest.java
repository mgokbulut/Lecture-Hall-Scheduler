package nl.tudelft.unischeduler.database.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import nl.tudelft.unischeduler.database.user.User;
import nl.tudelft.unischeduler.database.usercourse.UserCourseController;
import nl.tudelft.unischeduler.database.usercourse.UserCourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;



@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@ContextConfiguration(classes = UserCourseController.class)
@AutoConfigureMockMvc
@WebMvcTest
public class UserCourseControllerTest {
    @Autowired
    private transient WebApplicationContext webApplicationContext;

    @Autowired
    @MockBean
    private transient UserCourseService userCourseService;

    private transient MockMvc mockMvc;

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 1).getTimeInMillis());

    private final transient List<User> users = new ArrayList<>(
            List.of(
                    new User("a.baran@student.tudelft.nl", "STUDENT", true, timestamp),
                    new User("a.kuba@student.tudelft.nl", "STUDENT", true, timestamp),
                    new User("a.nathan@student.tudelft.nl", "STUDENT", true, timestamp)
            ));

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void getClassroomTest() throws Exception {
        when(userCourseService.getStudentsInCourse(0L)).thenReturn(users);
        String uri = "/userCourses/0";

        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").exists())
                //.andDo(print())
                .andExpect(jsonPath("$[0].netId", is("a.baran@student.tudelft.nl")))
                .andExpect(jsonPath("$[0].type", is("STUDENT")))
                .andExpect(jsonPath("$[0].interested", is(true)))
                .andExpect(jsonPath("$[1].netId", is("a.kuba@student.tudelft.nl")))
                .andExpect(jsonPath("$[1].type", is("STUDENT")))
                .andExpect(jsonPath("$[1].interested", is(true)))
                .andExpect(jsonPath("$[2].netId", is("a.nathan@student.tudelft.nl")))
                .andExpect(jsonPath("$[2].type", is("STUDENT")))
                .andExpect(jsonPath("$[2].interested", is(true)))
                .andExpect(status().isOk());

        verify(userCourseService, times(1)).getStudentsInCourse(0L);
        verifyNoMoreInteractions(userCourseService);
    }
}
