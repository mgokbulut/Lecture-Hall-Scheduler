package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.Course;
import nl.tudelft.unischeduler.database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/courses")
    public @ResponseBody
    Iterable<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping(path = "/studentsInCourse/{courseId}")
    public Iterable<User> getStudentsInCourse(@PathVariable Long courseId){
        Course c = courseService.getCourse(courseId);
        return c.getStudents();
    }


}
