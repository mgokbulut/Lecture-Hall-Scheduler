package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.Course;
import nl.tudelft.unischeduler.database.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long courseId) {
        return courseRepository.getOne(courseId);
    }
}
