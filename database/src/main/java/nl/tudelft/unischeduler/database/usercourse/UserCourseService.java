package nl.tudelft.unischeduler.database.usercourse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.tudelft.unischeduler.database.classroom.ClassroomRepository;
import nl.tudelft.unischeduler.database.lecture.LectureRepository;
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

    @Autowired
    private transient LectureRepository lectureRepository;

    @Autowired
    private transient ClassroomRepository classroomRepository;

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

    /**
     * Returns a List of Object arrays of size 2,
     * arr[0] = all lectures with a course_id that
     * has an entry in user_course with the given netId
     * arr[1] = corresponding classroom object.
     *
     * @param netId student netId
     * @return List of all the lectures
     */
    //TODO: actually test
    public List<Object []> getPossibleLectures(String netId) {
        try {
            return userCourseRepository
                    .findAllByNetId(netId)
                    .stream()
                    .map(x -> x.getCourseId())
                    .flatMap(x -> lectureRepository.findAllByCourse(x).stream())
                    .map(x -> new Object[]{x, classroomRepository.findById(x.getClassroom())})
                    .collect(Collectors.toList());
        } catch(Exception a) {
            System.err.println("No such object in DB");
            a.printStackTrace();
            return null;
        }
    }

}
