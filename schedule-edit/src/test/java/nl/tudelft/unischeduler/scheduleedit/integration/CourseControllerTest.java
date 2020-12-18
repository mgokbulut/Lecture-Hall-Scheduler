package nl.tudelft.unischeduler.scheduleedit.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CourseControllerTest extends ControllerTest {

    @Test
    public void createCourseTest() throws Exception {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("1")
        );
        String courseId = mockMvc.perform(
                MockMvcRequestBuilders.post("/course")
                        .param("courseName", "test course")
                        .param("year", "2000")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(server.getRequestCount()).isEqualTo(1);
        RecordedRequest recordedRequest = server.takeRequest();

        assertEquals("1", courseId);
        assertEquals(1, server.getRequestCount());
        assertEquals("PUT", recordedRequest.getMethod());
        assertThat(recordedRequest.getRequestUrl()).isNotNull();
        assertThat(recordedRequest.getRequestUrl()
                .uri()
                .getPath())
                .isEqualTo("/courses/create/test course/2000");
    }

    @Test
    public void createLectureTest() throws Exception {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("127")
        );
        String courseId = mockMvc.perform(
                MockMvcRequestBuilders.post("/course/lecture")
                        .param("courseId", "1")
                        .param("teacherNetId", "testId")
                        .param("year", "2000")
                        .param("week", "52")
                        .param("duration", "PT2H")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(server.getRequestCount()).isEqualTo(1);
        RecordedRequest recordedRequest = server.takeRequest();

        assertEquals("127", courseId);
        assertEquals(1, server.getRequestCount());
        assertEquals("PUT", recordedRequest.getMethod());
        assertThat(recordedRequest.getRequestUrl()).isNotNull();
        assertThat(recordedRequest.getRequestUrl()
                .uri()
                .getPath())
                .isEqualTo("/lectures/create/1/testId/2000-12-18T00:00/PT2H/false");
    }

    @Test
    public void addStudentToLecture() throws Exception {
        MockResponse response = new MockResponse()
                .setHeader("Content-Type", "application/json");
        server.enqueue(response);
        server.enqueue(response);
        server.enqueue(response);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/course/student")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"testId1\",\"testId2\",\"testId3\"]")
                        .param("courseId", "1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(server.getRequestCount()).isEqualTo(1);
        RecordedRequest recordedRequest = server.takeRequest();


        assertEquals("PUT", recordedRequest.getMethod());
        assertThat(recordedRequest.getRequestUrl()).isNotNull();
        assertThat(recordedRequest.getRequestUrl()
                .uri()
                .getPath())
                .isEqualTo("/courses/assignStudents/testId1,testId2,testId3/1");
    }
}
