package nl.tudelft.unischeduler.database.UserCourse;

import nl.tudelft.unischeduler.database.Course.CourseRepository;
import nl.tudelft.unischeduler.database.Lecture.Lecture;
import nl.tudelft.unischeduler.database.LectureSchedule.LectureSchedule;
import nl.tudelft.unischeduler.database.Schedule.Schedule;
import nl.tudelft.unischeduler.database.Schedule.ScheduleRepository;
import nl.tudelft.unischeduler.database.User.User;
import nl.tudelft.unischeduler.database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
