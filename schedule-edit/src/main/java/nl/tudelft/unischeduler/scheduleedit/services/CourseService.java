package nl.tudelft.unischeduler.scheduleedit.services;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This package acts as a placeholder until the communication Part is worked out.
 */
@Service
public class CourseService extends DatabaseService{

    /**
     * Creates a course and marks it to be scheduled by the schedule generate module.
     *
     * @param name The name of the course.
     * @param year The year the course is thought.
     * @return The generated id of the course.
     */
    public long createCourse(String name, int year) throws IOException {
        ResponseEntity<Long> response = webClient.put()
                .uri("/courses/create/{name}/{year}", name, year)
                .retrieve()
                .toEntity(Long.class)
                .block();
        verifyStatusCode(response);
        return extractLong(response);
    }

    public long createLecture(long courseId,
                              String teacher,
                              LocalDateTime startTime,
                              Duration duration) throws IOException {
        ResponseEntity<Long> response = webClient.put()
                .uri("/lectures/create/{courseId}/{teacher}/{startTime}/{duration}/{movedOnline}",
                        courseId,
                        teacher,
                        startTime,
                        duration,
                        false)
                .retrieve()
                .toEntity(Long.class)
                .block();
        verifyStatusCode(response);
        return extractLong(response);
    }

    public void addStudentToCourse(String netId, long courseId) throws IOException {
        ResponseEntity<Void> response = webClient.put()
                .uri("/lectureSchedules/{netId}/{lectureId}", netId, courseId)
                .retrieve()
                .toBodilessEntity()
                .block();
        verifyStatusCode(response);
    }
}
