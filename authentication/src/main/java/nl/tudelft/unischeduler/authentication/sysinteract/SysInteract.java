package nl.tudelft.unischeduler.authentication.sysinteract;

import java.util.Objects;
import javax.persistence.Entity;
import nl.tudelft.unischeduler.authentication.user.User;
import nl.tudelft.unischeduler.authentication.utilentities.Course;

/**
 * This object models an interaction between the user and the rest of the system.
 * It models a request to do something, such as add a course to the schedule
 */
@Entity
public class SysInteract {


    private transient String action;

    private transient int givenHashCode;

    private transient User user;

    /***
     * <p>This method initialises the SysInteract object.</p>
     *
     * @param action the name of the action the user wants to perform
     * @param hashCode the session hashcode
     * @param user the user who is doing the action
     */
    public SysInteract(String action, int hashCode, User user) {
        this.action = action;

        this.givenHashCode = hashCode;

        this.user = user;
    }

    /***
     * <p>This method initialises the SysInteract object.</p>
     */
    public SysInteract() {

    }

    public String getAction() {
        return action;
    }

    public int getHashCode() {
        return givenHashCode;
    }

    public User getUser() {
        return user;
    }


    /***
     * <p>This method return the role of the user for authetication purposes.</p>
     *
     * @return returns a string role.
     */
    public String authenticationRole() {
        if (this.user.getType().equals("STUDENT")) {
            return "ROLE_STUDENT";
        } else if (this.user.getType().equals("TEACHER")) {
            return "ROLE_TEACHER";
        } else if (this.user.getType().equals("FACULTY_MEMBER")) {
            return "ROLE_ADMIN";
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, givenHashCode, user);
    }

    @Override
    public boolean equals(Object other) {
        return this.hashCode() == other.hashCode();
    }
}