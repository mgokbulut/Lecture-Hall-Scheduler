package nl.tudelft.unischeduler.authentication.sysinteract;

import java.security.MessageDigest;
import java.util.Optional;
import nl.tudelft.unischeduler.authentication.AuthenticationService;
import nl.tudelft.unischeduler.authentication.user.User;
import nl.tudelft.unischeduler.authentication.utilentities.Course;
import nl.tudelft.unischeduler.authentication.utilentities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class SysInteractor {

  public String addCourse(User user, Course course) {
    // first we need to check that the user is the correct role
    if(checkFaculty(user)) {
      // TODO add the course to the db-module
    }
    unauthorizedAccess();
    return "";
  }

  public String addUser(User admin, User userToBeAdded) {
    if(checkFaculty(admin)) {
      // TODO add userToBeAdded to the database
    }
    unauthorizedAccess();
    return "";
  }

  public String addClassroom(User user, Room room) {
    if(checkFaculty(user)) {
      // TODO add room to DB
    }
    unauthorizedAccess();
    return "";
  }

  public String reportCorona(User user) {
    if(user.getType() == User.ROLE_STUDENT || user.getType() == User.ROLE_TEACHER) {
      // TODO report to schedule-edit module that user has corona
    }
    // TODO throw exception of invalid user type for this action
    return "";
  }
}