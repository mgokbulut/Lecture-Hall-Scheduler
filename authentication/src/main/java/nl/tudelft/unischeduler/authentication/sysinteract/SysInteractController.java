package nl.tudelft.unischeduler.authentication.sysinteract;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysInteractController {

  @Autowired
  private SysInteractor sysInteractor;

  @PostMapping(path = "/system/add_course")
  public String addCourse(@RequestBody SysInteract sysInteraction) {
    // return sysInteractor.addCourse(sysInteraction.getUser(), sysInteraction.getCourse());
    return null;
  }

}
