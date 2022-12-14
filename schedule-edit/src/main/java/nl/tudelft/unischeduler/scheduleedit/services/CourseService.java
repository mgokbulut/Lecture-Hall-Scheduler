package nl.tudelft.unischeduler.scheduleedit.services;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This package acts as a placeholder until the communication Part is worked out.
 */
@Service
public class CourseService extends DatabaseService {

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

    /**
     * Creates a new lecture on the database for the course with the coursId.
     *
     * @param courseId The id of the course this lecture belongs to.
     * @param teacher The teacher that gives this lecture.
     * @param startTime The startTime of the lecture.
     * @param duration The duration of the lecture.
     * @return the new Id of the lecture.
     * @throws IOException If the create is rejected.
     */
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

    /**
     * Constructs a string that can be used for a list of Strings in a path parameter.
     *
     * @param strings the strings to convert ot one single list.
     * @return The string that can be used as a path parameter.
     */
    public static String constructPathParam(List<String> strings) {
        if (strings == null || strings.size() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(strings.get(0));
        for (String string : strings.subList(1, strings.size())) {
            stringBuilder.append(",");
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    /**
     * Adds the list of students to the course.
     *
     * @param netIds The netIds of the students to add.
     * @param courseId The id of the course for which to add them to.
     * @throws IOException IOException If the update is rejected.
     */
    public void addStudentsToCourse(List<String> netIds, long courseId) throws IOException {
        ResponseEntity<Void> response = webClient.put()
                .uri("/courses/assignStudents/{netIdsFormat}/{lectureId}",
                        constructPathParam(netIds), courseId)
                .retrieve()
                .toBodilessEntity()
                .block();
        verifyStatusCode(response);
    }
}
