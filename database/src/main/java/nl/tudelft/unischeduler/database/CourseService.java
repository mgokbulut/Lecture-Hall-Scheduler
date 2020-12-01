package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.Course;
import nl.tudelft.unischeduler.database.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Iterable<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourse(Long courseId){
        return courseRepository.getOne(courseId);
    }
}
