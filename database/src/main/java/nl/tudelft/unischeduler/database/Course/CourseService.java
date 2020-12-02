package nl.tudelft.unischeduler.database.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long courseId) {
        Optional<Course> temp = courseRepository.findById(courseId);
        if(temp.isEmpty()){
            System.out.println("No course with such Id exists");
            return null;
        }
        return temp.get();
    }
}
