package nl.tudelft.unischeduler.database.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private transient CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("No such object in DB"));
    }
}
