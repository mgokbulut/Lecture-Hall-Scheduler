package nl.tudelft.unischeduler.sysinteract;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import nl.tudelft.unischeduler.user.User;
import nl.tudelft.unischeduler.user.UserRepository;
import nl.tudelft.unischeduler.utilentities.Course;
import nl.tudelft.unischeduler.utilentities.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@Service
public class SysInteractor {

    @Autowired
    private transient UserRepository userRepository;

    @Autowired
    protected transient WebClient.Builder webClientBuilder;

    protected transient WebClient webClient;

    @PostConstruct
    public void setUp() {
        webClient = webClientBuilder.build();
    }

    /**
     * Registers a new course to the system.
     *
     * @param course the course the user is trying to register
     * @return whether the addition was successful
     */
    public String addCourse(Course course) throws URISyntaxException {
        if (course != null) {
            webClient
                .post()
                .uri(new URI(
                    "http://schedule-edit-service/course?courseName=" + course.getName()
                        + "&year" + course.getYear()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
            return "{ \"status\": \"200\" }";
        }
        return "{ \"status\": \"400\" }";
    }

    /**
     * Registers a new user to the system.
     *
     * @param userToBeAdded the user to be added to the system
     * @return whether the change was successful
     */
    public String addUser(User userToBeAdded) {
        String response = webClient.post().uri("http://gateway-service/authentication/register")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(userToBeAdded))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return response;
    }

    //    /**
    //     * Registers a new classroom to the system.
    //     *
    //     * @param user the user trying to add a new classroom
    //     * @param room the room to be added
    //     * @return whether the change was successful
    //     */
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
     * @param username the user who is self-reporting
     * @return whether the change was successful
     */
    public String reportCorona(String username) throws URISyntaxException {
        Optional<User> optional = userRepository.findByNetId(username);
        User user = optional.get();
        if (user.getType() == User.ROLE_STUDENT) {
            webClient.put()
                .uri(new URI("http://schedule-edit-service/student/"
                    + user.getNetId() + "/sick"));
            //.block();
            return "{ \"status\": \"200\" }";
            //System.out.println("workful!");
        } else if (user.getType() == User.ROLE_TEACHER) {
            webClient.put()
                .uri(new URI("http://schedule-edit-service/teacher/"
                    + user.getNetId() + "/sick"));
            //.block();
            return "{ \"status\": \"200\" }";
        }
        return "{ \"status\": \"400\" }";
    }

    //    /**
    //     * Checks whether the COVID rules are being followed.
    //     *
    //     * @param user the user who is checking
    //     * @return whether the rules are being followed or not
    //     */
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
     * @param course the course queried for
     * @return the course's information
     */
    public Object[] courseInformation(Course course) throws URISyntaxException {
        // TODO poll database to find lecture information and return it
        // /lectureSchedules/course/{courseId}
        //Optional<User> optional = userRepository.findByNetId(username);
        //User user = optional.get();
        List<Lecture> res = webClient
            .get()
            .uri(new URI(
                "http://viewer-service/lectureSchedules/course/"
                    + course.getId()))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Lecture.class)
            .collectList()
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
        Object[] o = new Object[] {res, students};
        return o;
    }

    /**
     * returns a list of all lectures a student is scheduled to attend
     * in person.
     *
     * @param username the user making the request
     * @return a list of all lectures
     * @throws URISyntaxException exception
     */
    public Lecture[] studentSchedule(String username) throws URISyntaxException {
        Optional<User> optional = userRepository.findByNetId(username);
        User user = optional.get();
        List<Lecture> res = webClient
            .get()
            .uri(new URI(
                "http://viewer-service/" + user.getNetId()))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Lecture.class)
            .collectList()
            .block();
        return res.toArray(new Lecture[res.size()]);
    }

    /**
     * returns a list of all lectures a student is scheduled to attend
     * in person.
     *
     * @param username the user making the request, specifically a teacher
     * @return a list of all lectures taught by the teacher
     * @throws URISyntaxException exception
     */
    public Lecture[] teacherSchedule(String username) throws URISyntaxException {
        Optional<User> optional = userRepository.findByNetId(username);
        User user = optional.get();
        List<Lecture> res = webClient
            .get()
            .uri(new URI(
                "http://viewer-service/teacher/" + user.getNetId()))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Lecture.class)
            .collectList()
            .block();
        return res.toArray(new Lecture[res.size()]);
    }

    /**
     * creates a new lecture using the parameters given.
     *
     * @param courseId     the id of the course this lecture will belong to
     * @param teacherNetId the id of the teacher teaching this lecture
     * @param year         the year of this lecture
     * @param week         the week of this lecture
     * @param duration     the duration of this lecture
     * @return returns status code
     * @throws URISyntaxException exception
     */
    public String createLecture(long courseId, String teacherNetId, int year,
                                int week, Duration duration) throws URISyntaxException {
        webClient.put()
            .uri(new URI("http://schedule-edit-service/lecture/"
                + courseId + teacherNetId + year + week + duration));
        //.block();
        return "{ \"status\": \"200\" }";
    }

    //    /**
    //     * Adds the given lists of students to a lecture.
    //     *
    //     * @param user user
    //     * @param studentNetIds studentNetIds
    //     * @param courseId courseId
    //     * @return
    //     * @throws URISyntaxException
    //     */
    //    public String addStudentsToLecture(User user, List<String> studentNetIds, long courseId)
    //            throws URISyntaxException {
    //        if(checkStaff(user) || checkFaculty(user)) {
    //            webClient.put()
    //                    .uri(new URI("http://schedule-edit-service/student/"
    //                            + courseId))
    //            .body(BodyInserters.fromValue(studentNetIds));
    //            //.block();
    //            return "200";
    //        }
    //        return "403";
    //    }


    //    /**
    //     * Returns the information of a lecture, including enrolled students.
    //     *
    //     * @param user          the user making the request
    //     * @param course        the corresponding course of the lecture
    //     * @param lectureNumber nth lecture of the course we want info for
    //     * @return the lecture's information
    //     */
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


    //    /**
    //     * Helper method that checks whether a user is a faculty member.
    //     *
    //     * @param user the user we want to check
    //     * @return whether the user is a faculty member
    //     */
    //    private boolean checkFaculty(User user) {
    //        // first we need to check that the user is the correct role
    //        if (user.getType() == User.ROLE_FAC_MEMBER) {
    //            return true;
    //        }
    //        // endpoint should then return an error message
    //        return false;
    //    }

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