package nl.tudelft.sem.template.controllers;


import nl.tudelft.sem.template.models.AppUser;
import nl.tudelft.sem.template.repositories.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public @ResponseBody
    Iterable<AppUser> getAllUsers() {
        System.out.println("A Request has Arrived !!!!!!!!!!!!!!!!!!!!!!!");
        return userService.getAllUsers();
    }
}
