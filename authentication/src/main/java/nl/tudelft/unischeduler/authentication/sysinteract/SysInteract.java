package nl.tudelft.unischeduler.authentication.sysinteract;

import java.util.Objects;

/**
 * This object models an interaction between the user and the rest of the system.
 * It models a request to do something, such as add a course to the schedule
 */
@Entity
public class SysInteract {


  private String action;

  private int hashCode;

  private User user;

  /***
   * <p>This method initialises the SysInteract object.</p>
   *
   * @param netId the net id of the SysInteract
   * @param password the password of the SysInteract
   * @param type the type of the SysInteract
   */
  public SysInteract(String action, int hashCode, User user) {
    this.action = action;

    this.hash = hashCode;

    this.user = user;
  }

  /***
   * <p>This method initialises the SysInteract object.</p>
   */
  public SysInteract() {

  }

  public String getAction() { return action; }

  public String getHashCode() {
    return hashCode;
  }

  public String getUser() {
    return user;
  }

  /***
   * <p>This method return the role of the user for authetication purposes.</p>
   *
   * @return returns a string role.
   */
  public String authenticationRole() {
    if (this.user.type.equals("STUDENT")) {
      return "ROLE_STUDENT";
    } else if (this.user.type.equals("TEACHER")) {
      return "ROLE_TEACHER";
    } else if (this.user.type.equals("FACULTY_MEMBER")) {
      return "ROLE_ADMIN";
    }
    return null;
  }

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) {
//      return true;
//    }
//    if (o == null || getClass() != o.getClass()) {
//      return false;
//    }
//    User user = (User) o;
//    return Objects.equals(netId, user.netId)
//        && Objects.equals(password, user.password)
//        && Objects.equals(type, user.type);
//
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(netId, password, type);
//  }
}