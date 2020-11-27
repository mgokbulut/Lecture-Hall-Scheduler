package nl.tudelft.unischeduler.authentication.controllers;

import nl.tudelft.unischeduler.authentication.entities.User;
import nl.tudelft.unischeduler.authentication.repositories.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/authentication")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(path = "/users")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
