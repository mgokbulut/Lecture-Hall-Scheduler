package nl.tudelft.unischeduler.scheduleedit.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TeacherControllerTest extends ControllerTest{

    @Test
    public void cancelPastTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/teacher/{teacherNetId}/sick", "teachNetId")
                        .param("until", "2000-10-31T01:30:00.000-05:00")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(400)).andDo(print());
    }

    @Test
    public void cancelStandardTeacherLectureTest() throws Exception {
        MockResponse response =
                new MockResponse().setHeader("Content-Type", "application/json");
        server.enqueue(response);
        server.enqueue(response);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/teacher/{teacherNetId}/sick", "teachNetId")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        assertThat(server.getRequestCount()).isEqualTo(1);
        RecordedRequest recordedRequest1 = server.takeRequest();
        assertEquals("DELETE", recordedRequest1.getMethod());
        assertThat(recordedRequest1.getRequestUrl()).isNotNull();
        assertThat(recordedRequest1.getRequestUrl()
                .uri()
                .getPath().contains("/lectures/setToOffline/teachNetId/")).isTrue();
    }
}
