package nl.tudelft.unischeduler.user;


import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping(path = "/authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/register")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping(path = "/login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping(path = "/users")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/examplepath")
    public @ResponseBody
    String examplemethod() {
        return "{message:\"this is my message \"}";
    }

    @GetMapping(path = "/user/{netId}")
    public Optional<User> getUser(@PathVariable String netId) {
        return userService.getUser(netId);
    }
}
