package nl.tudelft.unischeduler.database.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private transient CourseService courseService;

    @GetMapping(path = "/courses/all")
    public @ResponseBody
    List<Course> getAllCoursesIds() {
        //return courseService.getAllCourses().stream().mapToLong(Course::getId).boxed().collect(Collectors.toList());
        return courseService.getAllCourses();
    }

    @GetMapping(path = "/courses/{course_id}")
    public @ResponseBody
    Course getCourse(@PathVariable Long course_id) {
        return courseService.getCourse(course_id);
    }



//    @GetMapping(path = "/lecturesInCourse/{lectureId}")
//    public Iterable<Lecture> getLecturesInCourse(@PathVariable Long courseId) {
//        Course c = courseService.getCourse(courseId);
//        return c.getLectures();
//    }
}
