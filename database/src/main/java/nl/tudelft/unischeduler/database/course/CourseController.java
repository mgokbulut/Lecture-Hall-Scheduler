package nl.tudelft.unischeduler.database.course;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CourseController {

    @Autowired
    private transient CourseService courseService;

    /**
     * Returns all the Courses.
     *
     * @return Course objects
     */
    @GetMapping(path = "/courses/all")
    public @ResponseBody
    List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(path = "/courses/{courseId}")
    public @ResponseBody
    Course getCourse(@PathVariable Long courseId) {
        return courseService.getCourse(courseId);
    }

    //doesn't work
    @PutMapping(path = "/courses/create/{name}/{year}")
    public ResponseEntity<?> createCourse(@PathVariable String name, @PathVariable Integer year) {
        return courseService.createCourse(name, year);
    }
}
