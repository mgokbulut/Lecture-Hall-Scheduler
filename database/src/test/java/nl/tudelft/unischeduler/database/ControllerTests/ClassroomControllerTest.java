package nl.tudelft.unischeduler.database.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.tudelft.unischeduler.database.Classroom.Classroom;
import nl.tudelft.unischeduler.database.Classroom.ClassroomController;
import nl.tudelft.unischeduler.database.Classroom.ClassroomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ContextConfiguration(classes = ClassroomController.class)
@AutoConfigureMockMvc
@WebMvcTest
public class ClassroomControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    @MockBean
    private ClassroomService classroomService;

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private MockMvc mockMvc;

    private List<Classroom> classrooms = new ArrayList<>(
            List.of(
                    new Classroom(0L, 50, "Amper Hall", "EWI", 1),
                    new Classroom(1L, 100, "Boole Hall", "EWI", 2),
                    new Classroom(2L, 250, "Pi Hall", "EWI", 1)
            ));

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllClassroomsTest() throws Exception {
        when(classroomService.getAllClassrooms()).thenReturn(classrooms);
        String uri = "/classrooms";

        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].name", is("Amper Hall")))
                .andExpect(jsonPath("$[0].buildingName", is("EWI")))
                .andExpect(jsonPath("$[0].floor", is(1)))
                .andExpect(jsonPath("$[1].name", is("Boole Hall")))
                .andExpect(jsonPath("$[1].buildingName", is("EWI")))
                .andExpect(jsonPath("$[1].floor", is(2)))
                .andExpect(jsonPath("$[2].name", is("Pi Hall")))
                .andExpect(jsonPath("$[2].buildingName", is("EWI")))
                .andExpect(jsonPath("$[2].floor", is(1)))
                .andExpect(status().isOk());

        verify(classroomService, times(1)).getAllClassrooms();
        verifyNoMoreInteractions(classroomService);
    }
}
