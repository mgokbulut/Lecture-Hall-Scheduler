package nl.tudelft.sem.template.user;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(path="/demo")
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

    @GetMapping(path = "/user/{netId}")
    public Optional<User> getUser(@PathVariable String netId) {
        return userService.getUser(netId);
    }


}
