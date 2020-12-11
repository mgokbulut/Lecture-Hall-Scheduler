package nl.tudelft.unischeduler.authentication.sysinteract;

import java.security.MessageDigest;
import java.util.Optional;
import nl.tudelft.unischeduler.authentication.user.User;
import nl.tudelft.unischeduler.authentication.utilentities.Course;
import nl.tudelft.unischeduler.authentication.utilentities.Room;
import org.springframework.stereotype.Service;

@Service
public class SysInteractor {

    /**
     * Registers a new course to the system.
     *
     * @param user the user trying to register the course
     * @param course the course the user is trying to register
     * @return whether the addition was successful
     */
    public String addCourse(User user, Course course) {
        // first we need to check that the user is the correct role
        if (checkFaculty(user)) {
            // TODO add the course to the db-module
            System.out.println("it works!");
        }
        unauthorizedAccess();
        return "";
    }

    /**
     * Registers a new user to the system.
     *
     * @param admin the user who has the right to add new users
     * @param userToBeAdded the user to be added to the system
     * @return whether the change was successful
     */
    public String addUser(User admin, User userToBeAdded) {
        if (checkFaculty(admin)) {
            // TODO add userToBeAdded to the database
            System.out.println("it does work!");
        }
        unauthorizedAccess();
        return "";
    }

    /**
     * Registers a new classroom to the system.
     *
     * @param user the user trying to add a new classroom
     * @param room the room to be added
     * @return whether the change was successful
     */
    public String addClassroom(User user, Room room) {
        if (checkFaculty(user)) {
            // TODO add room to DB
            System.out.println("working!");
        }
        unauthorizedAccess();
        return "";
    }

    /**
     * Reports to the system that the user has corona.
     *
     * @param user the user who is self-reporting
     * @return whether the change was successful
     */
    public String reportCorona(User user) {
        if (user.getType() == User.ROLE_STUDENT || user.getType() == User.ROLE_TEACHER) {
            // TODO report to schedule-edit module that user has corona
            System.out.println("workful!");
        }
        // TODO throw exception of invalid user type for this action
        return "";
    }

    /**
     * Checks whether the COVID rules are being followed.
     *
     * @param user the user who is checking
     * @return whether the rules are being followed or not
     */
    public String stateOfRuleFollowing(User user) {
        if (checkFaculty(user)) {
            // TODO poll rules module to know if we're following all the rules
            // (not a must-have)
            System.out.println("workable!");
        }
        unauthorizedAccess();
        return "";
    }

    /**
     * Returns all the infromation that concerns a course,
     * including all students who are signed-up to attend it.
     *
     * @param user the user making the request
     * @param course the course queried for
     * @return the course's information
     */
    public String courseInformation(User user, Course course) {
        if (checkStaff(user)) {
            // TODO poll database module to get course information and return it
            System.out.println("workaholic!");
        }
        unauthorizedAccess();
        return "";
    }

    /**
     * Returns the information of a lecture, including enrolled students.
     *
     * @param user the user making the request
     * @param course the corresponding course of the lecture
     * @param lectureNumber nth lecture of the course we want info for
     * @return the lecture's information
     */
    public String lectureInformation(User user, Course course, int lectureNumber) {
        if (checkStaff(user)) {
            // TODO poll database to find lecture information and return it
            System.out.println("workahol!");
        }
        unauthorizedAccess();
        return "";
    }


    /**
     * Helper method that checks whether a user is a faculty member.
     *
     * @param user the user we want to check
     * @return whether the user is a faculty member
     */
    private boolean checkFaculty(User user) {
        // first we need to check that the user is the correct role
        if (user.getType() == User.ROLE_FAC_MEMBER) {
            return true;
        }
        // endpoint should then return an error message
        return false;
    }

    /**
     * Helper method that checks whether the user is a
     * university staff member.
     *
     * @param user the user to check
     * @return whether the user is a staff member
     */
    private boolean checkStaff(User user) {
        // in here we check whether a user is a university employee
        if (user.getType() == User.ROLE_FAC_MEMBER || user.getType() == User.ROLE_TEACHER) {
            return true;
        }
        // endpoint should then return an error message
        return false;
    }

    /**
     * Throws an "unauthorized access" exception in case the request failed.
     */
    private void unauthorizedAccess() {
        // TODO return an exception with message "unauthorized access"
    }
}