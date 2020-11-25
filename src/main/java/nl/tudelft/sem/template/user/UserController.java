package nl.tudelft.sem.template.user;


import nl.tudelft.sem.template.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequestMapping(path="/demo")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/register")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

//    @PostMapping(path = "/register/{netID}/{password}/{type}")
//    public String registerWithType(@RequestParam String netID, @RequestParam String password, @RequestParam String type) {
//        final User user = new User(netID, password, type);
//        return userService.register(user);
//    }

    @PostMapping(path = "/login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }
    @GetMapping(path = "/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/user/{netID}")
    public Optional<User> getUser(@PathVariable String netID) {
        return userService.getUser(netID);
    }


}
