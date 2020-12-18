package nl.tudelft.unischeduler.database.course;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CourseService {

    @Autowired
    private transient CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

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

    /**
     * Creates a new course.
     *
     * @param name name of the course
     * @param year year of students this course will be for
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> createCourse(String name, Integer year) {
        try {
            Optional<Course> courseOptional = courseRepository
                    .findByAndNameAndYear(name, year);
            if (courseOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Course course = new Course(name, year);
            courseRepository.save(course);
            return ResponseEntity.ok(course.getId());
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
