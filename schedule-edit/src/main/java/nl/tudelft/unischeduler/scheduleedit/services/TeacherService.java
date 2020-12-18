package nl.tudelft.unischeduler.scheduleedit.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This package acts as a placeholder until the communication Part is worked out.
 */
@EqualsAndHashCode(callSuper = true)
@Service
@Data
public class TeacherService extends DatabaseService {

    /**
     * Marks all the lectures of the lecturer during the timeslot as online.
     *
     * @param teacherNetId The netId of the lecturer for which to cancel the lectures.
     * @param start The date for which to start canceling lectures (inclusive).
     * @param end the date for which to end canceling lectures (inclusive).
     */
    public void cancelLectures(String teacherNetId, LocalDateTime start, LocalDateTime end)
            throws IOException {
        ResponseEntity<Void> response = webClient.delete()
                .uri("/lectures/setToOffline/{teacherId}/{start}", teacherNetId,
                        Timestamp.valueOf(start),
                        Timestamp.valueOf(end))
                .retrieve()
                .toBodilessEntity()
                .block();
        verifyStatusCode(response);
    }
}
