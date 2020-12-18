package nl.tudelft.unischeduler.scheduleedit.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class StudentControllerTest extends ControllerTest {

    @Test
    public void canclePastTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/student/{studentNetId}/sick", "student")
                        .param("until", "2000-10-31T01:30:00.000-05:00")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(400));
    }

    @Test
    public void cancelStandardStudentAttendenceTest() throws Exception {
        MockResponse response =
                new MockResponse().setHeader("Content-Type", "application/json");
        server.enqueue(response);
        server.enqueue(response);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/student/{studentNetId}/sick", "student")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        assertThat(server.getRequestCount()).isEqualTo(2);
        RecordedRequest recordedRequest1 = server.takeRequest();
        assertEquals(2, server.getRequestCount());
        assertEquals("DELETE", recordedRequest1.getMethod());
        assertThat(recordedRequest1.getRequestUrl()).isNotNull();
        assertThat(recordedRequest1.getRequestUrl()
                .uri()
                .getPath().contains("lectureSchedules/remove/student")).isTrue();

        RecordedRequest recordedRequest2 = server.takeRequest();
        assertEquals("PUT", recordedRequest2.getMethod());
        assertThat(recordedRequest2.getRequestUrl()).isNotNull();
        assertThat(recordedRequest2.getRequestUrl()
                .uri()
                .getPath().contains("/sickLogs/new/student/")).isTrue();
    }
}
