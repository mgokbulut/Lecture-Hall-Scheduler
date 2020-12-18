package nl.tudelft.unischeduler.database.usercourse;

import java.util.List;
import nl.tudelft.unischeduler.database.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserCourseController {

    @Autowired
    private transient UserCourseService userCourseService;

    @GetMapping(path = "/userCourses/{courseId}")
    public @ResponseBody
    List<User> getStudentsInCourse(@PathVariable Long courseId) {
        //edit for "along with the TimeStamp of their most recent Lecture"
        return userCourseService.getStudentsInCourse(courseId);
    }

    @PutMapping(path = "/userCourses/assignStudents/{netIds}/{courseId}")
    public @ResponseBody
    ResponseEntity<?> addStudentToCourse(@PathVariable List<String> netIds,
                                         @PathVariable Long courseId) {
        return userCourseService.addStudentToCourse(netIds, courseId);
    }

    @GetMapping(path = "/userCourseService/possibleLectures/{netId}")
    public @ResponseBody
    List<Object []> getPossibleLectures(@PathVariable String netId) {
        return userCourseService.getPossibleLectures(netId);
    }
}
