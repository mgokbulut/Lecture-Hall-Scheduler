package nl.tudelft.unischeduler.sysinteract;

import nl.tudelft.unischeduler.user.User;
import nl.tudelft.unischeduler.utilentities.Course;
import nl.tudelft.unischeduler.utilentities.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Iterator;

@RestController
public class SysInteractController {

    @Autowired
    private transient SysInteractor sysInteractor;

    @PostMapping(path = "/system/add_course")
    public String addCourse(@RequestBody SysInteract sysInteraction) {
        try {
            Course course = (Course) sysInteraction.getArgs().get(0);

            return sysInteractor.addCourse(sysInteraction.getUser(), course);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "404";
        }
    }

    @PostMapping(path = "/system/add_user")
    public String addUser(@RequestBody SysInteract sysInteraction) {

        User user = (User) sysInteraction.getArgs().get(0);

        return sysInteractor.addUser(sysInteraction.getUser(), user);

    }

    @PostMapping(path = "/system/report_corona")
    public String reportCorona(@RequestBody SysInteract sysInteraction) {
        try {
            return sysInteractor.reportCorona(sysInteraction.getUser());
        } catch (URISyntaxException e) {
            return "404";
        }

    }

    @PostMapping(path = "/system/course_information")
    public Object[] courseInformation(@RequestBody SysInteract sysInteraction) {

        try {
            Course course = (Course) sysInteraction.getArgs().get(0);

            return sysInteractor.courseInformation(sysInteraction.getUser(), course);
        } catch (URISyntaxException e) {
            return new Object[]{"404"};
        }


    }

    @PostMapping(path = "/system/student_schedule")
    public Lecture[] studentSchedule(@RequestBody SysInteract sysInteraction) {

        try {
            return sysInteractor.studentSchedule(sysInteraction.getUser());
        } catch (URISyntaxException e) {
            return null;
        }

    }

    @PostMapping(path = "/system/teacher_schedule")
    public Lecture[] teacherSchedule(@RequestBody SysInteract sysInteraction) {

        try {
            return sysInteractor.teacherSchedule(sysInteraction.getUser());
        } catch (URISyntaxException e) {
            return null;
        }

    }

    @PostMapping(path = "/system/create_lecture")
    public String createLecture(@RequestBody SysInteract sysInteraction) {

        try {
            Iterator<Object> it = sysInteraction.getArgs().iterator();
            long courseId = (Long) it.next();
            String teacherNetId = (String) it.next();
            int year = (Integer) it.next();
            int week = (Integer) it.next();
            Duration duration = (Duration) it.next();
            return sysInteractor.createLecture(sysInteraction.getUser(), courseId, teacherNetId, year, week, duration);
        } catch (URISyntaxException e) {
            return null;
        }

    }



}
