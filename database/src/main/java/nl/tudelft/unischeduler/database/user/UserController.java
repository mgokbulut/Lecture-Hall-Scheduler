package nl.tudelft.unischeduler.database.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private transient UserService userService;

    @GetMapping(path = "/users/all")
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
