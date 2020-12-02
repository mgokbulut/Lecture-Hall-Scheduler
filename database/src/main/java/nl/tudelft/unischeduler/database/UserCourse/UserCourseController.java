package nl.tudelft.unischeduler.database.UserCourse;

import nl.tudelft.unischeduler.database.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCourseController {

    @Autowired
    private transient UserCourseService userCourseService;

    @GetMapping(path = "/userCourse/{courseId}")
    public @ResponseBody
    List<User> getStudentsInCourse(@PathVariable Long courseId){
        //edit for "along with the TimeStamp of their most recent Lecture"
        return userCourseService.getStudentsInCourse(courseId);
    }
}
