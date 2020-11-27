package nl.tudelft.sem.template.trial;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(path="/demo")
public class TrialController {
    @GetMapping(path = "/test")
    public String returntest() {
        return "You can access this route";
    }
}
