package nl.tudelft.unischeduler.database.usercourse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nl.tudelft.unischeduler.database.user.User;
import nl.tudelft.unischeduler.database.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@Service
public class UserCourseService {

    @Autowired
    private transient UserCourseRepository userCourseRepository;

    @Autowired
    private transient UserRepository userRepository;

    /**
     * Returns a List of all students who belong to the given course.
     *
     * @param courseId course ID
     * @return List of all students who belong to the given course
     */
    public List<User> getStudentsInCourse(Long courseId) {
        List<String> netIds =  userCourseRepository
                .findAllByCourseId(courseId)
                .stream()
                .map(UserCourse::getNetId)
                .collect(Collectors.toList());
        return userRepository
                .findAllById(netIds)
                .stream()
                .filter(User::isInterested)
                .collect(Collectors.toList());
    }

    /**
     * Adds all the users with specified netIds to the Course.
     *
     * @param netIds netIds of users
     * @param courseId course they will be added to
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> addStudentToCourse(List<String> netIds, Long courseId) {
        try {
            List<UserCourse> userCourses = new ArrayList<>();
            for (String netId : netIds) {
                if (userCourseRepository.findByCourseIdAndNetId(courseId, netId).isPresent()) {
                    return ResponseEntity.notFound().build();
                }
                UserCourse userCourse = new UserCourse(netId, courseId);
                userCourseRepository.save(userCourse);
                userCourses.add(userCourse);
            }
            return ResponseEntity.ok(userCourses);
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
