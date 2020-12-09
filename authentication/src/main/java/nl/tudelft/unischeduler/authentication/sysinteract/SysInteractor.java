package nl.tudelft.unischeduler.authentication.sysinteract;

import java.security.MessageDigest;
import java.util.Optional;
import nl.tudelft.unischeduler.authentication.AuthenticationService;
import nl.tudelft.unischeduler.authentication.user.User;
import nl.tudelft.unischeduler.authentication.utilentities.Course;
import nl.tudelft.unischeduler.authentication.utilentities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysInteractor {

  public String addCourse(Course course) {
    return "";
  }

  public String addUser(User user) {
    return "";
  }

  public String addClassroom(Room room) { return ""; }
}