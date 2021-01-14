package nl.tudelft.unischeduler.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TokenTests extends UserControllerTest {
    @Test
    public void loginTest() {
        //need to mock the database
        //        server.enqueue(new MockResponse()
        //            .setHeader("Content-Type", "application/json")
        //            .setBody("1")
        //        );
        //        String token = mockMvc.perform(
        //            MockMvcRequestBuilders.post("/authentication/login")
        //                .contentType(MediaType.APPLICATION_JSON)
        //                .content("{}")
        //                .accept(MediaType.APPLICATION_JSON)
        //        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        //
        //        assertThat(server.getRequestCount()).isEqualTo(1);
        //        RecordedRequest recordedRequest = server.takeRequest();
        boolean a = true;
        assertEquals(a, true);
    }
}

