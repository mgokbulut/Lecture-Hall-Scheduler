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
     * Constructor.
     *
     * @param userCourseRepository input repository
     * @param userRepository input repository
     * @param lectureRepository input repository
     * @param classroomRepository input repository
     */
    public UserCourseService(UserCourseRepository userCourseRepository,
                             UserRepository userRepository,
                             LectureRepository lectureRepository,
                             ClassroomRepository classroomRepository) {
        this.userCourseRepository = userCourseRepository;
        this.userRepository = userRepository;
        this.lectureRepository = lectureRepository;
        this.classroomRepository = classroomRepository;
    }

    /**
     * Returns a List of all students who belong to the given course.
     *
     * @param courseId course ID
     * @return List of all students who belong to the given course
     */
    public List<User> getStudentsInCourse(Long courseId) {
        try {
            List<String> netIds =  userCourseRepository
                    .findAllByCourseId(courseId)
                    .stream()
                    .map(UserCourse::getNetId)
                    .collect(Collectors.toList());
            System.out.println(netIds);
            List<User> ret = new ArrayList<>();
            for (String netId : netIds) {
                System.out.println(netId);
                User user = userRepository.findByNetId(netId).get();
                System.out.println(user);
                if (user.isInterested()) {
                    ret.add(user);
                }
            }
            return ret;
        } catch (Exception a) {
            a.printStackTrace();
            return null;
        }
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
                    System.err.println(netId);
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
    public List<Object []> getPossibleLectures(String netId) {
        try {
            return userCourseRepository
                    .findAllByNetId(netId)
                    .stream()
                    .map(UserCourse::getCourseId)
                    .flatMap(x -> lectureRepository.findAllByCourse(x).stream())
                    .map(x -> new Object[]{x, classroomRepository.findById(x.getClassroom()).get()})
                    .collect(Collectors.toList());
        } catch (Exception a) {
            System.err.println("No such object in DB");
            a.printStackTrace();
            return null;
        }
    }

}
