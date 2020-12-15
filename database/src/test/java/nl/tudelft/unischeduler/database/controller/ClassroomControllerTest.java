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

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.unischeduler.database.classroom.Classroom;
import nl.tudelft.unischeduler.database.classroom.ClassroomController;
import nl.tudelft.unischeduler.database.classroom.ClassroomService;
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
@ContextConfiguration(classes = ClassroomController.class)
@AutoConfigureMockMvc
@WebMvcTest
public class ClassroomControllerTest {

    @Autowired
    private transient WebApplicationContext webApplicationContext;

    @Autowired
    @MockBean
    private transient ClassroomService classroomService;

    //ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private transient MockMvc mockMvc;

    private transient List<Classroom> classrooms = new ArrayList<>(
            List.of(
                    new Classroom(50, "Amper Hall", "EWI", 1),
                    new Classroom(100, "Boole Hall", "EWI", 2),
                    new Classroom(250, "Pi Hall", "EWI", 1)
            ));

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllClassroomsTest() throws Exception {
        when(classroomService.getAllClassrooms()).thenReturn(classrooms);
        String uri = "/classrooms/all";

        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].name", is("Amper Hall")))
                .andExpect(jsonPath("$[0].buildingName", is("EWI")))
                .andExpect(jsonPath("$[0].floor", is(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].fullCapacity", is(50)))
                .andExpect(jsonPath("$[1].name", is("Boole Hall")))
                .andExpect(jsonPath("$[1].buildingName", is("EWI")))
                .andExpect(jsonPath("$[1].floor", is(2)))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].fullCapacity", is(100)))
                .andExpect(jsonPath("$[2].name", is("Pi Hall")))
                .andExpect(jsonPath("$[2].buildingName", is("EWI")))
                .andExpect(jsonPath("$[2].floor", is(1)))
                .andExpect(jsonPath("$[2].id", is(2)))
                .andExpect(jsonPath("$[2].fullCapacity", is(250)))
                .andExpect(status().isOk());

        verify(classroomService, times(1)).getAllClassrooms();
        verifyNoMoreInteractions(classroomService);
    }

    @Test
    public void getClassroomByIdTest() throws Exception {
        when(classroomService.getClassroom(1L)).thenReturn(classrooms.get(1));
        String uri = "/classrooms/1";

        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
                //.andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", is("Boole Hall")))
                .andExpect(jsonPath("$.buildingName", is("EWI")))
                .andExpect(jsonPath("$.floor", is(2)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fullCapacity", is(100)))
                .andExpect(status().isOk());

        verify(classroomService, times(1)).getClassroom(1L);
        verifyNoMoreInteractions(classroomService);
    }
}
