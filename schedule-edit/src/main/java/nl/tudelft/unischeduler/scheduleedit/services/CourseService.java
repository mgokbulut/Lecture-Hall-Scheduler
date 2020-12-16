package nl.tudelft.unischeduler.scheduleedit.services;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * This package acts as a placeholder until the communication Part is worked out.
 */
@Service
public class CourseService {

    /**
     * Creates a course and marks it to be scheduled by the schedule generate module.
     *
     * @param name The name of the course.
     * @param year The year the course is thought.
     * @return The generated id of the course.
     */
    public long createCourse(String name, int year) throws IOException {
        //TODO: this is a stub, and in the future should actually send the data.
        return -1;
    }

    public long createLecture(long course_id,
                              String teacher,
                              LocalDateTime startTime,
                              Duration duration) throws IOException{
        //TODO: this is a stub, and in the future should actually send the data.
        return -1;
    }

    public void addStudentToCourse(List<String> netIdList, long courseId) throws IOException {
        //TODO: this is a stub, and in the future should actually send the data.
    }
}
