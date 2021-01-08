package nl.tudelft.unischeduler.sysinteract;

import java.util.List;
import java.util.Objects;
import nl.tudelft.unischeduler.user.User;

/**
 * This object models an interaction between the user and the rest of the system.
 * It models a request to do something, such as add a course to the schedule
 */
public class SysInteract {

    private transient List<Object> args;

    /***
     * <p>This method initialises the SysInteract object.</p>
     *
     * @param args a list of all arguments needed for the requested call
     */
    public SysInteract(List<Object> args) {
        this.args = args;
    }

    /***
     * <p>This method initialises the SysInteract object.</p>
     */
    public SysInteract() {

    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysInteract that = (SysInteract) o;
        return Objects.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(args);
    }
}