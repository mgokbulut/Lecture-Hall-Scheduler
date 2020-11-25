package nl.tudelft.sem.template.test;


import nl.tudelft.sem.template.authentication.AuthenticationService;
import nl.tudelft.sem.template.user.User;
import nl.tudelft.sem.template.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequestMapping(path="/demo")
public class testController {
    @GetMapping(path = "/test")
    public String getAllUsers() {
        System.out.println("saaaaaaxxxx");
        return "You can access this route";
    }
}
