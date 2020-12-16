package nl.tudelft.unischeduler.database.usercourse;

import java.util.List;
import java.util.stream.Collectors;
import nl.tudelft.unischeduler.database.user.User;
import nl.tudelft.unischeduler.database.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

}
