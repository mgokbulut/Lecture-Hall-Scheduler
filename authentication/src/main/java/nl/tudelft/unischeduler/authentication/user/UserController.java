package nl.tudelft.unischeduler.authentication.user;


import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
//@RequestMapping(path="/demo")
public class UserController {

//    @Autowired
//    private WebClient.Builder webClientBuilder;

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

    @PostMapping(path = "/api/login")
    public String loginApi(@RequestBody User user) {
        String token = userService.loginApi(user);
        if (token == null) {
            return "0";
        }
        return token;
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
