package nl.tudelft.sem.template.controllers;


import nl.tudelft.sem.template.entities.User;
import nl.tudelft.sem.template.repositories.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RestController
@RequestMapping(path="/demo")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(path = "/users")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        System.out.println("A Request has Arrived !!!!!!!!!!!!!!!!!!!!!!!");
        return userService.getAllUsers();
    }
}
