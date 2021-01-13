package nl.tudelft.unischeduler.rules.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.rules.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@AllArgsConstructor
@Service
public class ClassRoomDatabaseService {

    @Autowired
    private WebClient databaseWebClient;

    /**
     * Retrieves the classroom info of the classroom with the specified id.
     *
     * @param classroomId The id of the classroom.
     * @return A classroom entity that represents the classroom.
     */
    public Room getClassroom(int classroomId) {
        return databaseWebClient.get()
                .uri("classrooms/" + classroomId)
                .retrieve()
                .bodyToMono(Room.class)
                .block();
    }

    /**
     * Sets the room of the lecture with lecture id to EMPTY.
     *
     * @param lectureId The id of the lecture for which to edit the classroom.
     * @return true iff the request succeeded.
     */
    public boolean removeRoomFromLecture(int lectureId) {
        String result = databaseWebClient.put()
                .uri("lectures/setClassroomToEmpty/" + lectureId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result != null && result.equals("true");
    }
}
