package nl.tudelft.unischeduler.sysinteract;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import nl.tudelft.unischeduler.authentication.JwtUtil;
import nl.tudelft.unischeduler.authentication.MyUserDetails;
import nl.tudelft.unischeduler.authentication.MyUserDetailsService;
import nl.tudelft.unischeduler.user.User;
import nl.tudelft.unischeduler.utilentities.Course;
import nl.tudelft.unischeduler.utilentities.Lecture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SysInteractControllerTest {

    @Mock
    private transient JwtUtil mockJwtUtil;

    @Mock
    private transient MyUserDetailsService mockUserDetailsService;

    @Mock
    private transient SysInteractor mockSysInteractor;

    @InjectMocks
    private transient SysInteractController sysInteractControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    private final transient String teacherToken = "eyJhbGciOiJIUzI1NiJ9."
        + "eyJzdWIiOiJhLm1lcnRAc3R1ZGVudC50dWRlbGZ0"
        + "Lm5sIiwiZXhwIjoxNjEwNjg5MTI5LCJpYXQiOjE2MTA2NTMxMjl9."
        + "DJsgo0pjuPQOPk8dHJr2W_XNRLOooW_xGT5HxhrpTu0";
    private final transient String teacherNetid = "teacher@tudelft.nl";
    private final transient String studentNetid = "a.mert@student.tudelft.nl";
    // @Test
    // public void testAddCourse() throws Exception {
    // // Setup
    // final SysInteract sysInteraction = new SysInteract(new
    // Hashtable<>(Map.ofEntries()));
    // when(mockSysInteractor.addCourse(new Course(0L, "name", Set.of(new User()),
    // 2021, 1)))
    // .thenReturn("{ \"status\": \"200\" }");
    //
    // // Run the test
    // final String result =
    // sysInteractControllerUnderTest.addCourse(sysInteraction);
    //
    // // Verify the results
    // assertEquals("{ \"status\": \"200\" }", result);
    // }

    @Test
    public void testaddcourse_sysinteractorthrowsurisyntaxexception() throws Exception {
        // Setup
        final SysInteract sysInteraction = new SysInteract(new Hashtable<>(Map.ofEntries()));
        when(mockSysInteractor.addCourse(new Course(0L, "name", Set.of(new User()), 2021, 1)))
            .thenThrow(URISyntaxException.class);

        // Run the test
        final String result = sysInteractControllerUnderTest.addCourse(sysInteraction);

        // Verify the results
        assertEquals("{ \"status\": \"400\" }", result);
    }

    @Test
    public void testAddUser() {
        // Setup
        final SysInteract sysInteraction = new SysInteract(new Hashtable<>(Map.ofEntries()));
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockSysInteractor.addUser(any(User.class))).thenReturn("200");

        // Run the test
        final String result = sysInteractControllerUnderTest.addUser(sysInteraction, request);

        // Verify the results
        assertEquals("200", result);
    }

    // @Test
    // public void testReportCorona() throws Exception {
    // // Setup
    // final HttpServletRequest request = new MockHttpServletRequest();
    // when(mockJwtUtil.extractUsername("token")).thenReturn("result");
    // when(mockUserDetailsService.loadUserByUsername("netId")).thenReturn(null);
    // when(mockSysInteractor.reportCorona("username")).thenReturn("result");
    //
    // // Run the test
    // final String result = sysInteractControllerUnderTest.reportCorona(request);
    //
    // // Verify the results
    // assertEquals("{ \"status\": \"200\" }", result);
    // }

    // @Test
    // public void
    // testReportCorona_MyUserDetailsServiceThrowsUsernameNotFoundException()
    // throws Exception {
    // // Setup
    // final HttpServletRequest request = new MockHttpServletRequest();
    // when(mockJwtUtil.extractUsername("token")).thenReturn("result");
    // when(mockUserDetailsService.loadUserByUsername("netId")).thenThrow(
    // UsernameNotFoundException.class);
    // when(mockSysInteractor.reportCorona("username")).thenReturn("result");
    //
    // // Run the test
    // final String result = sysInteractControllerUnderTest.reportCorona(request);
    //
    // // Verify the results
    // assertEquals("result", result);
    // }

    // @Test
    // public void testReportCorona_SysInteractorThrowsURISyntaxException() throws
    // Exception {
    // // Setup
    // final HttpServletRequest request = new MockHttpServletRequest();
    // when(mockJwtUtil.extractUsername("token")).thenReturn("result");
    // when(mockUserDetailsService.loadUserByUsername("netId")).thenReturn(null);
    // when(mockSysInteractor.reportCorona("username")).thenThrow(URISyntaxException.class);
    //
    // // Run the test
    // final String result = sysInteractControllerUnderTest.reportCorona(request);
    //
    // // Verify the results
    // assertEquals("{ \"status\": \"400\" }", result);
    // }

    @Test
    public void testCourseInformation() throws Exception {
        // Setup
        final SysInteract sysInteraction = new SysInteract(new Hashtable<>(Map.ofEntries()));
        when(mockSysInteractor
            .courseInformation(new Course(0L, "name", Set.of(new User()), 2021, 1)))
            .thenReturn(new Object[] {"value"});

        // Run the test
        sysInteractControllerUnderTest.courseInformation(sysInteraction);

        // Verify the results
    }

    @Test
    public void testcourseinformation_sysinteractorthrowsurisyntaxexception() throws Exception {
        // Setup
        final SysInteract sysInteraction = new SysInteract(new Hashtable<>(Map.ofEntries()));
        when(mockSysInteractor
            .courseInformation(new Course(0L, "name", Set.of(new User()), 2021, 1)))
            .thenThrow(URISyntaxException.class);

        // Run the test
        sysInteractControllerUnderTest.courseInformation(sysInteraction);

        // Verify the results
    }

    @Test
    public void testStudentSchedule() throws Exception {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtUtil.extractUsername(teacherToken)).thenReturn("result");
        when(mockUserDetailsService.loadUserByUsername(teacherNetid)).thenReturn(null);
        when(mockSysInteractor.studentSchedule(teacherNetid))
            .thenReturn(new Lecture[] {new Lecture(0)});

        // Run the test
        sysInteractControllerUnderTest.studentSchedule(request);

        // Verify the results
    }

    @Test
    public void testStudentSchedule_MyUserDetailsServiceThrowsUsernameNotFoundException()
        throws Exception {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtUtil.extractUsername(teacherToken)).thenReturn("result");
        when(mockUserDetailsService.loadUserByUsername(teacherNetid))
            .thenThrow(UsernameNotFoundException.class);
        when(mockSysInteractor.studentSchedule(teacherNetid))
            .thenReturn(new Lecture[] {new Lecture(0)});

        // Run the test
        sysInteractControllerUnderTest.studentSchedule(request);
    }

    @Test
    public void teststudentschedule_sysinteractorthrowsurisyntaxexception() throws Exception {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtUtil.extractUsername(teacherToken)).thenReturn("unknown");
        when(mockUserDetailsService.loadUserByUsername(teacherNetid)).thenReturn(null);
        when(mockSysInteractor.studentSchedule(teacherNetid)).thenThrow(URISyntaxException.class);

        // Run the test
        sysInteractControllerUnderTest.studentSchedule(request);
    }

    @Test
    public void testTeacherSchedule() throws Exception {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtUtil.extractUsername(teacherToken)).thenReturn("nothing");
        when(mockUserDetailsService.loadUserByUsername(teacherNetid)).thenReturn(null);
        when(mockSysInteractor.teacherSchedule(teacherNetid))
            .thenReturn(new Lecture[] {new Lecture(0)});

        // Run the test
        sysInteractControllerUnderTest.teacherSchedule(request);
    }

    @Test
    public void testTeacherSchedule_MyUserDetailsServiceThrowsUsernameNotFoundException()
        throws Exception {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtUtil.extractUsername(teacherToken)).thenReturn("whatever");
        when(mockUserDetailsService.loadUserByUsername(teacherNetid))
            .thenThrow(UsernameNotFoundException.class);
        when(mockSysInteractor.teacherSchedule(teacherNetid))
            .thenReturn(new Lecture[] {new Lecture(0)});

        // Run the test
        sysInteractControllerUnderTest.teacherSchedule(request);
    }

    @Test
    public void testteacherschedule_sysinteractorthrowsurisyntaxexception() throws Exception {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtUtil.extractUsername(teacherToken)).thenReturn("whatever");
        when(mockUserDetailsService.loadUserByUsername(teacherNetid)).thenReturn(null);
        when(mockSysInteractor.teacherSchedule(teacherNetid)).thenThrow(URISyntaxException.class);

        // Run the test
        sysInteractControllerUnderTest.teacherSchedule(request);
    }

    // @Test
    // public void testCreateLecture() throws Exception {
    // // Setup
    // Dictionary<Object, Object> args = new Hashtable<Object, Object>();
    // args.put("courseId", "0");
    // args.put("teacherNetId", "teacherNetId");
    // args.put("year", "2021");
    // args.put("week", "1");
    // args.put("Duration", "{\"seconds\": 3600, \"nanos\":0}");
    //
    // final SysInteract sysInteraction = new SysInteract(new
    // Hashtable<>(Map.ofEntries()));
    // when(mockSysInteractor.createLecture(0L, "teacherNetId", 2021, 1,
    // Duration.ofDays(0L))).thenReturn("{ \"status\": \"200\" }");
    //
    // // Run the test
    // final String result =
    // sysInteractControllerUnderTest.createLecture(sysInteraction);
    //
    // // Verify the results
    // assertEquals("{ \"status\": \"200\" }", result);
    // }

    // @Test
    // public void testCreateLecture_SysInteractorThrowsURISyntaxException() throws
    // Exception {
    // // Setup
    // final SysInteract sysInteraction = new SysInteract(new
    // Hashtable<>(Map.ofEntries()));
    // when(mockSysInteractor.createLecture(0L, "teacherNetId", 2021, 1,
    // Duration.ofDays(0L)))
    // .thenThrow(
    // URISyntaxException.class);
    //
    // // Run the test
    // final String result =
    // sysInteractControllerUnderTest.createLecture(sysInteraction);
    //
    // // Verify the results
    // assertEquals("{ \"status\": \"400\" }", result);
    // }

    @Test
    public void testExtract_username() {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);

        User u = new User(studentNetid, "pass3", "STUDENT");
        Optional<User> uo = Optional.of(u);
        UserDetails userDetails = uo.map(MyUserDetails::new).get();
        when(request.getHeader("Authorization")).thenReturn(teacherToken);
        when(mockJwtUtil
            .extractUsername(teacherToken))
            .thenReturn(studentNetid);
        when(mockUserDetailsService.loadUserByUsername(studentNetid))
            .thenReturn(userDetails);

        // Run the test
        // final String result = sysInteractControllerUnderTest.extract_username(request);
        sysInteractControllerUnderTest.extract_username(request);

        // Verify the results
        // assertEquals(studentNetid, result);
    }

    // @Test
    // public void
    // testExtract_username_MyUserDetailsServiceThrowsUsernameNotFoundException() {
    // // Setup
    // final HttpServletRequest request = new MockHttpServletRequest();
    // when(mockJwtUtil.extractUsername("token")).thenReturn("result");
    // when(mockUserDetailsService.loadUserByUsername("netId")).thenThrow(
    // UsernameNotFoundException.class);
    //
    // // Run the test
    // final String result =
    // sysInteractControllerUnderTest.extract_username(request);
    //
    // // Verify the results
    // assertEquals("{ \"status\": \"400\" }", result);
    // }
}
