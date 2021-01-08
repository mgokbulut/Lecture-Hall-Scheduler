package nl.tudelft.unischeduler.sysinteract;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;
import nl.tudelft.unischeduler.authentication.JwtUtil;
import nl.tudelft.unischeduler.authentication.MyUserDetailsService;
import nl.tudelft.unischeduler.user.User;
import nl.tudelft.unischeduler.utilentities.Course;
import nl.tudelft.unischeduler.utilentities.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Iterator;

@RestController
public class SysInteractController {

    @Autowired
    @Getter
    @Setter
    private JwtUtil jwtUtil;

    @Autowired
    @Getter
    @Setter
    private MyUserDetailsService userDetailsService;

    @Autowired
    private transient SysInteractor sysInteractor;

    @PostMapping(path = "/system/add_course", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addCourse(@RequestBody SysInteract sysInteraction) {
        try {
            Course course = (Course) sysInteraction.getArgs().get(0);
            return sysInteractor.addCourse(course);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return exception_message("404", "URI Syntax Exception", "/system/add_course");
        } catch (ClassCastException e) {
            e.printStackTrace();
            return exception_message("404", "could not parse request body", "/system/add_course");
        }
    }

    @PostMapping(path = "/system/add_user", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody SysInteract sysInteraction, HttpServletRequest request) {
        try {
            User user = (User) sysInteraction.getArgs().get(0);
            String username = extract_username(request);
            return sysInteractor.addUser(user);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return exception_message("404", "could not parse request body", "/system/add_user");
        }
    }

    @PostMapping(path = "/system/report_corona", produces = MediaType.APPLICATION_JSON_VALUE)
    public String reportCorona(HttpServletRequest request) {
        try {
            String username = extract_username(request);
            return sysInteractor.reportCorona(username);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return exception_message("404", "URI Syntax Exception", "/system/report_corona");
        } catch (ClassCastException e) {
            e.printStackTrace();
            return exception_message("404", "could not parse request body", "/system/report_corona");
        }

    }

    @PostMapping(path = "/system/course_information", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object[] courseInformation(@RequestBody SysInteract sysInteraction) {
        try {
            Course course = (Course) sysInteraction.getArgs().get(0);
            return sysInteractor.courseInformation(course);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new Object[] {exception_message("404", "URI Syntax Exception", "/system/course_information")};
        } catch (ClassCastException e) {
            e.printStackTrace();
            return new Object[] {exception_message("404", "could not parse request body", "/system/course_information")};
        }


    }

    @PostMapping(path = "/system/student_schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public Lecture[] studentSchedule(HttpServletRequest request) {
        try {
            String username = extract_username(request);
            return sysInteractor.studentSchedule(username);
        }  catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }

    }

    @PostMapping(path = "/system/teacher_schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public Lecture[] teacherSchedule(HttpServletRequest request) {
        try {
            String username = extract_username(request);
            return sysInteractor.teacherSchedule(username);
        } catch (URISyntaxException e) {
            return null;
        }

    }

    @PostMapping(path = "/system/create_lecture", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createLecture(@RequestBody SysInteract sysInteraction) {
        try {
            Iterator<Object> it = sysInteraction.getArgs().iterator();
            long courseId = (Long) it.next();
            String teacherNetId = (String) it.next();
            int year = (Integer) it.next();
            int week = (Integer) it.next();
            Duration duration = (Duration) it.next();
            return sysInteractor.createLecture(courseId, teacherNetId, year, week, duration);
        } catch (URISyntaxException e) {
            return null;
        }

    }

    public String extract_username(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = "";
        String jwt = "";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt += authorizationHeader.substring(7);
            username += jwtUtil.extractUsername(jwt);
        }
        if (!username.equals("")
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        }
        return username;
    }
    public static String exception_message(String status_code, String message, String path) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        JSONObject res = new JSONObject();
        res.put("timestamp", timeStamp);
        res.put("status", status_code);
        res.put("error", "Bad Request");
        res.put("message", message);
        res.put("path", path);
        return res.toString();
        /*
        ("{\n\t"
            + "\"timestamp\": \"" + timeStamp + "\"" + "\n\t"
            + "\"status\": \"" + status_code + "\"" + "\n\t"
            + "\"error\": \"" + "Bad Request" + "\"" + "\n\t"
            + "\"message\": \"" + message + "\"," + "\n\t"
            + "\"path\": \"" + path + "\"" + "\n\t"
            + "}");
         */
    }
}
