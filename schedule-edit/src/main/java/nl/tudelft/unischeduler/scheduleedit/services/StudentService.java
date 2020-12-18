package nl.tudelft.unischeduler.scheduleedit.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import org.springframework.stereotype.Service;

/**
 * This package acts as a placeholder until the communication Part is worked out.
 */
@EqualsAndHashCode(callSuper = true)
@Service
@Data
public class StudentService extends DatabaseService {

    /**
     * Adds an entry into the sick log where the user is specified to be sick at this date.
     *
     * @param netId the netId of the user to mark sick.
     * @param startTime The time for which the student should be marked sick.
     * @throws ConnectionException When the connection to the database service fails.
     */
    public void setUserSick(String netId, LocalDateTime startTime) throws IOException {
        webClientBuilder.build().put()
                .uri("/sickLogs/new/"
                        + netId
                        + "/" + Timestamp.valueOf(startTime))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    /**
     * Cancels the attendance of the student for all lectures
     * they are following in between the start and end date.
     *
     * @param studentNetId The netId of the students for which to cancel their attendance.
     * @param start The start time for which the student is sick.
     * @param end The end date for when the student is expected to be better (exclusive).
     * @throws ConnectionException When the connection to the database service fails.
     */
    public void cancelStudentAttendance(String studentNetId, LocalDateTime start, LocalDateTime end)
            throws IOException {
        webClientBuilder.build().delete()
                .uri("/lectureSchedules/remove/"
                        + studentNetId + "/"
                        + Timestamp.valueOf(start) + "/"
                        + Timestamp.valueOf(end))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
