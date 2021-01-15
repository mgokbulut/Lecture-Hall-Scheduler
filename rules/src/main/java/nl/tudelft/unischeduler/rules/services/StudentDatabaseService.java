package nl.tudelft.unischeduler.rules.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.rules.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@AllArgsConstructor
@Service
public class StudentDatabaseService {

    @Autowired
    private WebClient databaseWebClient;

    /**
     * Retrieves the student info of the student with the netId from the database.
     *
     * @param netId the netId of the student info to retrieve.
     * @return The student entity corresponding to the netId.
     */
    public Student getStudent(String netId) {
        return getDatabaseWebClient().get()
                .uri("users/" + netId)
                .retrieve()
                .bodyToMono(Student.class)
                .block();
    }
}
