package nl.tudelft.unischeduler.rules.services;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@AllArgsConstructor
@Service
public class LectureDatabaseService {

    @Autowired
    private WebClient databaseWebClient;

    /**
     * retrieves all the lectures currently stored in the database.
     *
     * @return All lectures stored in the database.
     */
    public Lecture[] getLectures() {
        List<Lecture> list = getDatabaseWebClient().get()
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
     * removes the lecture from the schedule.
     *
     * @param lectureId The id of the lecture to remove.
     * @return True iff the request succeeded.
     */
    public boolean removeLectureFromSchedule(int lectureId) {
        String result = getDatabaseWebClient().delete()
                .uri("lectures/remove/" + lectureId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result != null && result.equals("true");
    }
}
