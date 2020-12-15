package nl.tudelft.unischeduler.authentication.user;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
//@RequestMapping(path="/demo")
public class UserController {

    @Autowired
    private WebClient.Builder webClientBuilder;

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
        return "this is an example method";
    }

    @GetMapping(path = "/user/{netId}")
    public Optional<User> getUser(@PathVariable String netId) {
        return userService.getUser(netId);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
