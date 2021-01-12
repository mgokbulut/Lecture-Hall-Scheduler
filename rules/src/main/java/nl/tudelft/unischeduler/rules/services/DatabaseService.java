package nl.tudelft.unischeduler.rules.services;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@NoArgsConstructor
@Service
public class DatabaseService {

    private WebClient webClient;

    public DatabaseService(@Autowired WebClient.Builder webClientBuilder) {
        webClientBuilder.baseUrl("http://database-service/");
        webClient = webClientBuilder.build();
    }

    /**
     * Retrieves the student info of the student with the netId from the database.
     *
     * @param netId the netId of the student info to retrieve.
     * @return The student entity corresponding to the netId.
     */
    public Student getStudent(String netId) {
        return webClient.get()
                .uri("users/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Student.class)
                .block();
    }


    /**
     * Retrieves the classroom info of the classroom with the specified id.
     *
     * @param classroomId The id of the classroom.
     * @return A classroom entity that represents the classroom.
     */
    public Room getClassroom(int classroomId) {
        return webClient.get()
                .uri("classrooms/" + classroomId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Room.class)
                .block();
    }

    /**
     * retrieves all the lectures currently stored in the database.
     *
     * @return All lectures stored in the database.
     */
    public Lecture[] getLectures() {
        List<Lecture> list = webClient.get()
                .uri("lectures/courses")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Lecture.class)
                .collectList()
                .block();
        assert list != null;
        Lecture[] result = new Lecture[list.size()];
        return list.toArray(result);
    }

    /**
     * Sets the room of the lecture with lecture id to EMPTY.
     *
     * @param lectureId The id of the lecture for which to edit the classroom.
     * @return true iff the request succeeded.
     */
    public boolean removeRoomFromLecture(int lectureId) {
        String result = webClient.put()
                .uri("lectures/setClassroomToEmpty/" + lectureId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result != null && result.equals("true");
    }

    /**
     * removes the lecture from the schedule.
     *
     * @param lectureId The id of the lecture to remove.
     * @return True iff the request succeeded.
     */
    public boolean removeLectureFromSchedule(int lectureId) {
        String result = webClient.delete()
                .uri("lectures/remove/" + lectureId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result != null && result.equals("true");
    }
}
