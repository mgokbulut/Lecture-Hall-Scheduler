package nl.tudelft.unischeduler.sysinteract;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.annotation.PostConstruct;
import nl.tudelft.unischeduler.user.User;
import nl.tudelft.unischeduler.utilentities.Course;
import nl.tudelft.unischeduler.utilentities.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SysInteractor {

    @Autowired
    protected WebClient.Builder webClientBuilder;

    protected WebClient webClient;

    @PostConstruct
    public void setUp() {
        webClient = webClientBuilder.build();
    }

    /**
     * Registers a new course to the system.
     *
     * @param user   the user trying to register the course
     * @param course the course the user is trying to register
     * @return whether the addition was successful
     */
    public String addCourse(User user, Course course) throws URISyntaxException {
        // first we need to check that the user is the correct role
        if (checkFaculty(user)) {
            if (user != null && course != null) {
                long res = webClient
                    .post()
                    .uri(new URI(
                        "http://schedule-edit-service/course?courseName=" + course.getName()
                            + "&year" + course.getYear()))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Long.class)
                    .block();
            }
            System.out.println("it works!");
        }
        return unauthorizedAccess();
    }

    /**
     * Registers a new user to the system.
     *
     * @param admin         the user who has the right to add new users
     * @param userToBeAdded the user to be added to the system
     * @return whether the change was successful
     */
    public String addUser(User admin, User userToBeAdded) {
        if (checkFaculty(admin)) {
            String response = webClient.post().uri("http://gateway-service/authentication/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(userToBeAdded))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            System.out.println("it does work!");
            return response;
        }
        return unauthorizedAccess();
    }

    /**
     * Registers a new classroom to the system.
     *
     * @param user the user trying to add a new classroom
     * @param room the room to be added
     * @return whether the change was successful
     */
//    public String addClassroom(User user, Room room) {
//        if (checkFaculty(user)) {
//            // TODO add room to DB
//            System.out.println("working!");
//        }
//        unauthorizedAccess();
//        return "";
//    }

    /**
     * Reports to the system that the user has corona.
     *
     * @param user the user who is self-reporting
     * @return whether the change was successful
     */
    public String reportCorona(User user) {
        if (user.getType() == User.ROLE_STUDENT) {
            webClient.put()
                .uri(new URI("http://schedule-edit-service/student/"
                    + user.getNetId() + "/sick"))
                .block();
            return "Successful";
            System.out.println("workful!");
        } else if (user.getType() == User.ROLE_TEACHER) {
            webClient.put()
                .uri(new URI("http://schedule-edit-service/teacher/"
                    + user.getNetId() + "/sick"))
                .block();
            return "Successful";
        }
        // TODO throw exception of invalid user type for this action
        return unauthorizedAccess();
    }

    /**
     * Checks whether the COVID rules are being followed.
     *
     * @param user the user who is checking
     * @return whether the rules are being followed or not
     */
//    public String stateOfRuleFollowing(User user) {
//        if (checkFaculty(user)) {
//            // TODO poll rules module to know if we're following all the rules
//            // (not a must-have)
//            System.out.println("workable!");
//        }
//        unauthorizedAccess();
//        return "";
//    }

    /**
     * Returns all the infromation that concerns a course,
     * including all students who are signed-up to attend it.
     *
     * @param user   the user making the request
     * @param course the course queried for
     * @return the course's information
     */
    public Object[] courseInformation(User user, Course course) throws URISyntaxException {
        if (checkStaff(user)) {
            // TODO poll database to find lecture information and return it
            // /lectureSchedules/course/{courseId}
            Lecture[] res = webClient
                .get()
                .uri(new URI(
                    "http://viewer-service/lectureSchedules/course/"
                        + course.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Lecture[].class)
                .block();
            List<User> students = webClient
                .get()
                .uri(new URI(
                    "http://database-service/userCourses/" + course.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class)
                .collectList()
                .block();
            Object[] o = new Object[]{res,students};
            return o;
        }
        return null;
    }

    /**
     * Returns the information of a lecture, including enrolled students.
     *
     * @param user          the user making the request
     * @param course        the corresponding course of the lecture
     * @param lectureNumber nth lecture of the course we want info for
     * @return the lecture's information
     */
//    public Object lectureInformation(User user, Course course, int lectureNumber)
//        throws URISyntaxException {
//        if (checkStaff(user)) {
//            // TODO poll database to find lecture information and return it
//            // /lectureSchedules/course/{courseId}
//            Lecture[] res = webClient
//                .get()
//                .uri(new URI(
//                    "http://viewer-service/lectureSchedules/course/"
//                        + course.getId()))
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(Lecture[].class)
//                .block();
//            List<User> students = webClient
//                .get()
//                .uri(new URI(
//                    "http://database-service/userCourses/" + course.getId()))
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToFlux(User.class)
//                .collectList()
//                .block();
//            Object[] o = new Object[]{res,students};
//            return o;
//        }
//        return null;
//    }


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
    private String unauthorizedAccess() {
        return "NOT AUTHORIZED!";
    }
}