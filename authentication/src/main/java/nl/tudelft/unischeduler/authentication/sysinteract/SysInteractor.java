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

  public String stateOfRuleFollowing(User user) {
    if(checkFaculty(user)) {
      // TODO poll rules module to know if we're following all the rules
      // (not a must-have)
    }
    unauthorizedAccess();
    return "";
  }

  public String courseInformation(User user, Course course) {
    if(checkStaff(user)) {
      // TODO poll database module to get course information and return it
    }
    unauthorizedAccess();
    return "";
  }

  public String lectureInformation(User user, Course course, int lectureNumber) {
    if(checkStaff(user)) {
      // TODO poll database to find lecture information and return it
    }
    unauthorizedAccess();
    return "";
  }

  

  private boolean checkFaculty(User user) {
    // first we need to check that the user is the correct role
    if(user.getType() == User.ROLE_FAC_MEMBER) {
      return true;
    }
    // endpoint should then return an error message
    return false;
  }

  private boolean checkStaff(User user) {
    // in here we check whether a user is a university employee
    if(user.getType() == User.ROLE_FAC_MEMBER || user.getType() == User.ROLE_TEACHER) {
      return true;
    }
    // endpoint should then return an error message
    return false;
  }

  private void unauthorizedAccess() {
    // TODO return an exception with message "unauthorized access"
  }
}