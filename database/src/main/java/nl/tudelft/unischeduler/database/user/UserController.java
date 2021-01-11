package nl.tudelft.unischeduler.database.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(path = "/users/{netId}")
    public @ResponseBody
    Object[] getUser(@PathVariable String netId) {
        return userService.getUser(netId);
    }

    @GetMapping(path = "/lectureSchedules/studentsLecture/{lectureId}")
    public @ResponseBody
    List<Object []> getStudentsInLecture(@PathVariable Long lectureId) {
        return userService.getStudentsInLecture(lectureId);
    }
}
