package nl.tudelft.unischeduler.database.User;

import nl.tudelft.unischeduler.database.Course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private transient UserService userService;

    @Autowired
    private transient CourseService courseService;

    @GetMapping(path = "/users")
    public @ResponseBody
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

//    @GetMapping(path = "/users/{courseId}")
//    public List<User> getStudentsInCourse(@PathVariable Long courseId) {
//        Course c = courseService.getCourse(courseId);
//
//    }
}
