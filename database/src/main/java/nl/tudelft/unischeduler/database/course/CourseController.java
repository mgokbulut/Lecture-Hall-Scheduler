package nl.tudelft.unischeduler.database.course;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        //return courseService
        // .getAllCourses()
        // .stream()
        // .mapToLong(Course::getId)
        // .boxed()
        // .collect(Collectors.toList());
        return courseService.getAllCourses();
    }

    @GetMapping(path = "/courses/{courseId}")
    public @ResponseBody
    Course getCourse(@PathVariable Long courseId) {
        return courseService.getCourse(courseId);
    }



    //    @GetMapping(path = "/lecturesInCourse/{lectureId}")
    //    public Iterable<Lecture> getLecturesInCourse(@PathVariable Long courseId) {
    //        Course c = courseService.getCourse(courseId);
    //        return c.getLectures();
    //    }
}
