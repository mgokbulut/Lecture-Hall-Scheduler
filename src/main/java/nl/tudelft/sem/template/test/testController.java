package nl.tudelft.sem.template.test;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(path="/demo")
public class TestController {
    @GetMapping(path = "/test")
    public String getAllUsers() {
        return "You can access this route";
    }
}
