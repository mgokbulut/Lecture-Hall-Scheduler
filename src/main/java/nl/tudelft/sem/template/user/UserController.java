package nl.tudelft.sem.template.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequestMapping(path="/demo")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(path = "/users")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        //System.out.println("A Request has Arrived !!!!!!!!!!!!!!!!!!!!!!!");
        return userService.getAllUsers();
    }

    @GetMapping(path = "/user/{netID}")
    public Optional<User> getUser(@PathVariable String netID) {
        //System.out.println("A Request has Arrived !!!!!!!!!!!!!!!!!!!!!!!");
        return userService.getUser(netID);
    }
}
