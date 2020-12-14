package nl.tudelft.unischeduler.database.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
}
