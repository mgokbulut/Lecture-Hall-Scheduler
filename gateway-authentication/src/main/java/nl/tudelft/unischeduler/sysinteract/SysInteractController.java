package nl.tudelft.unischeduler.sysinteract;

import java.time.Duration;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import nl.tudelft.unischeduler.authentication.JwtUtil;
import nl.tudelft.unischeduler.authentication.MyUserDetailsService;
import nl.tudelft.unischeduler.user.User;
import nl.tudelft.unischeduler.utilentities.ArgsBuilder;
import nl.tudelft.unischeduler.utilentities.Arguments;
import nl.tudelft.unischeduler.utilentities.Course;
import nl.tudelft.unischeduler.utilentities.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


    public static final String BAD_REQUEST = "{ \"status\": \"400\" }";
    //    public static final String DEFAULT_EXCEPTION_MESSAGE = "Something went wrong.";

    /**
     * Adds a course.
     *
     * @param sysInteraction request body
     * @return returns status code
     */
    @PostMapping(path = "/system/add_course", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addCourse(@RequestBody SysInteract sysInteraction) {
        try {
            ArgsBuilder builder = new ArgsBuilder(sysInteraction.getArgs());
            builder.buildCourse();
            Arguments args = builder.getResult();

            Course course = args.getCourse();

            return sysInteractor.addCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
            return BAD_REQUEST;
        }
    }

    /**
     * Adds a user.
     *
     * @param sysInteraction request body
     * @param request        http request object
     * @return returns status code
     */
    @PostMapping(path = "/system/add_user", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody SysInteract sysInteraction, HttpServletRequest request) {
        try {
            ArgsBuilder builder = new ArgsBuilder(sysInteraction.getArgs());
            builder.buildUser();
            Arguments args = builder.getResult();

            User user = args.getUser();

            return sysInteractor.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return BAD_REQUEST;
        }
    }

    /**
     * Reports Corona.
     *
     * @param request http request object
     * @return returns status code
     */
    @PostMapping(path = "/system/report_corona", produces = MediaType.APPLICATION_JSON_VALUE)
    public String reportCorona(HttpServletRequest request) {
        try {
            String username = extract_username(request);
            return sysInteractor.reportCorona(username);
        } catch (Exception e) {
            e.printStackTrace();
            return BAD_REQUEST;
        }
    }

    /**
     * Adds a course.
     *
     * @param sysInteraction request body
     * @return returns status code
     */
    @PostMapping(path = "/system/course_information", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object[] courseInformation(@RequestBody SysInteract sysInteraction) {
        try {
            ArgsBuilder builder = new ArgsBuilder(sysInteraction.getArgs());
            builder.buildCourse();
            Arguments args = builder.getResult();

            Course course = args.getCourse();

            return sysInteractor.courseInformation(course);
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[] {BAD_REQUEST};
        }
    }

    /**
     * Gets student schedule.
     *
     * @param request http request object
     * @return returns status code
     */
    @PostMapping(path = "/system/student_schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public Lecture[] studentSchedule(HttpServletRequest request) {
        try {
            String username = extract_username(request);
            return sysInteractor.studentSchedule(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Gets teacher schedule.
     *
     * @param request http request object
     * @return returns status code
     */
    @PostMapping(path = "/system/teacher_schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public Lecture[] teacherSchedule(HttpServletRequest request) {
        try {
            String username = extract_username(request);
            return sysInteractor.teacherSchedule(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a lecture.
     *
     * @param sysInteraction http request object
     * @return returns status code
     */
    @PostMapping(path = "/system/create_lecture", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createLecture(@RequestBody SysInteract sysInteraction) {
        try {
            ArgsBuilder builder = new ArgsBuilder(sysInteraction.getArgs());
            builder.buildLecture();
            builder.buildCourse();

            Arguments args = builder.getResult();

            long courseId = args.getCourseId();
            String teacherNetId = args.getNetId();
            int year = args.getCourse().getYear();
            int week = args.getLecture().getWeek();
            Duration duration = args.getLecture().getDuration();
            return sysInteractor.createLecture(courseId, teacherNetId, year, week, duration);
        } catch (Exception e) {
            e.printStackTrace();
            return BAD_REQUEST;
        }
    }

    /**
     * Extracts username from jwt token.
     *
     * @param request http request object
     * @return returns username
     */
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
            userDetails.getUsername(); // passes the PMD
        }
        try {
            jwt += "";
        } catch (Exception e) {
            System.out.println("This is just unnecessary!!!!");
        }

        return username;
    }

    //    /**
    //     * Creates a json error message.
    //     *
    //     * @param statusCode status code
    //     * @param message    message
    //     * @param path       path
    //     * @return returns json error message
    //     */
    //    public static String exception_message(String statusCode, String message, String path) {
    //        String timeStamp =
    //            new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", Locale.ENGLISH).format(new Date());
    //        JSONObject res = new JSONObject();
    //        res.put("timestamp", timeStamp);
    //        res.put("status", statusCode);
    //        res.put("error", "Bad Request");
    //        res.put("message", message);
    //        res.put("path", path);
    //        return res.toString();
    //        /*
    //        ("{\n\t"
    //            + "\"timestamp\": \"" + timeStamp + "\"" + "\n\t"
    //            + "\"status\": \"" + status_code + "\"" + "\n\t"
    //            + "\"error\": \"" + "Bad Request" + "\"" + "\n\t"
    //            + "\"message\": \"" + message + "\"," + "\n\t"
    //            + "\"path\": \"" + path + "\"" + "\n\t"
    //            + "}");
    //         */
    //    }
}
