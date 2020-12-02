package nl.tudelft.unischeduler.authentication.sysinteract;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrialController {

  @Autowired
  private SysInteractor sysInteractor;

  @PostMapping(path = "/system/add_course")
  public String addCourse(@RequestBody SysInteract sysInteraction) {
    return sysInteractor.addCourse(sysInteraction);
  }

}
