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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import nl.tudelft.unischeduler.database.sicklog.SickLog;
import nl.tudelft.unischeduler.database.sicklog.SickLogController;
import nl.tudelft.unischeduler.database.sicklog.SickLogService;
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
@ContextConfiguration(classes = SickLogController.class)
@AutoConfigureMockMvc
@WebMvcTest
public class SickLogControllerTest {

    @Autowired
    private transient WebApplicationContext webApplicationContext;

    @Autowired
    @MockBean
    private transient SickLogService sickLogService;

    private transient MockMvc mockMvc;

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 10, 0, 0).getTimeInMillis());

    private transient List<SickLog> sickLogs = new ArrayList<>(
            List.of(
                    new SickLog(0L, "test-user1", new Date(timestamp.getTime()), true),
                    new SickLog(1L, "test-user2", new Date(timestamp.getTime() + 10000), true),
                    new SickLog(2L, "test-user3", new Date(timestamp.getTime() + 100000000), true)
            ));

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void getAllSickLogsTest() throws Exception {
        when(sickLogService.getAllSickLogs()).thenReturn(sickLogs);
        String uri = "/sickLogs/all";

        mockMvc.perform(get(uri).contentType(APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$[0].user", is("test-user1")))
                    .andExpect(jsonPath("$[0].id", is(0)))
                    .andExpect(jsonPath("$[0].finished", is(true)))
                    .andExpect(jsonPath("$[0].reportSick",
                            is(new Date(timestamp.getTime()).toString())))
                    .andExpect(jsonPath("$[1].user", is("test-user2")))
                    .andExpect(jsonPath("$[1].id", is(1)))
                    .andExpect(jsonPath("$[1].finished", is(true)))
                    .andExpect(jsonPath("$[1].reportSick",
                            is(new Date(timestamp.getTime() + 10000).toString())))
                    .andExpect(jsonPath("$[2].user", is("test-user3")))
                    .andExpect(jsonPath("$[2].id", is(2)))
                    .andExpect(jsonPath("$[2].finished", is(true)))
                    .andExpect(jsonPath("$[2].reportSick",
                            is(new Date(timestamp.getTime() + 100000000).toString())))
                    .andExpect(status().isOk());

        verify(sickLogService, times(1)).getAllSickLogs();
        verifyNoMoreInteractions(sickLogService);
    }
}
