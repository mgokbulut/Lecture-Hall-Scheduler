package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient UserService userService;

    @GetMapping(path = "/users")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
