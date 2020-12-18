package nl.tudelft.unischeduler.rules.services;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.entities.Student;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@AllArgsConstructor
@Service
public class DatabaseService {

    @NonNull private WebClient.Builder webClientBuilder;

    @PostConstruct
    public void setUp() {
        webClientBuilder.baseUrl("http://database-service/");
    }

    /**
     * Retrieves the student info of the student with the netId from the database.
     *
     * @param netId the netId of the student info to retrieve.
     * @return The student entity corresponding to the netId.
     */
    public Student getStudent(String netId) {
        Student result = webClientBuilder.build()
                .get()
                .uri("users/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Student.class)
                .block();
        return result;
    }

    /**
     * Retrieves a lsit of all students in a lecture.
     *
     * @param lectureId The id that specifies the lecture.
     * @return A list of all the students in the lecture.
     */
    public Student[] getStudentsInLecture(int lectureId) {
        List<Student> list = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/students/" + lectureId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Student.class)
                .collectList()
                .block();
        Student[] result = new Student[list.size()];

        return result;
    }

    /**
     * Removes the student with netId from the lecture.
     *
     * @param netId The netId of the student which to remove.
     * @param lectureId The id of the lecture from which to remove.
     * @return true if it succeeded otherwise false.
     */
    public boolean removeStudentFromLecture(String netId, int lectureId) {
        String result = webClientBuilder.build()
                .delete()
                .uri("lectureSchedules/remove/" + netId + "/" + lectureId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result != null && result.equals("true");
    }

    /**
     * Retrieves the classroom info of the classroom with the specified id.
     *
     * @param classroomId The id of the classroom.
     * @return A classroom entity that represents the classroom.
     */
    public Room getClassroom(int classroomId) {
        Room result = webClientBuilder.build()
                .get()
                .uri("classrooms/" + classroomId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Room.class)
                .block();
        return result;
    }

    /**
     * retrieves all the lectures currently stored in the database.
     *
     * @return All lectures stored in the database.
     */
    public Lecture[] getLectures() {
        List<Lecture> list = webClientBuilder.build()
                .get()
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
        String result = webClientBuilder.build()
                .put()
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
        String result = webClientBuilder.build()
                .delete()
                .uri("lectures/remove/" + lectureId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result != null && result.equals("true");
    }



}
