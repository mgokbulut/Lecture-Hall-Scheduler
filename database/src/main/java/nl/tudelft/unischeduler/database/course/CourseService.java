package nl.tudelft.unischeduler.database.course;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CourseService {

    @Autowired
    private transient CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Returns a specified course.
     *
     * @param courseId course ID
     * @return Course object
     */
    public Course getCourse(Long courseId) {
        return courseRepository
                .findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("No such object in DB"));
    }
}
