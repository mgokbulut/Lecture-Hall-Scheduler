package nl.tudelft.unischeduler.database.UserCourse;

import nl.tudelft.unischeduler.database.User.User;
import nl.tudelft.unischeduler.database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCourseService {

    @Autowired
    private transient UserCourseRepository userCourseRepository;

    @Autowired
    private transient UserRepository userRepository;

    public List<User> getStudentsInCourse(Long courseId) {
        List<String> netIds =  userCourseRepository.findAllByCourseId(courseId).stream().map(UserCourse::getNetId).collect(Collectors.toList());
        return userRepository.findAllById(netIds).stream().filter(User::isInterested).collect(Collectors.toList());
    }

}
