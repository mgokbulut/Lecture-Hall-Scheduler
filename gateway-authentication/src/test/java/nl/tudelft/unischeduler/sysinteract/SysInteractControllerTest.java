
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
package nl.tudelft.unischeduler.sysinteract;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.ArgumentMatchers.anyInt;
    import static org.mockito.ArgumentMatchers.anyString;
    import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.stubbing.Answer;

public class SysInteractControllerTest {

 @Mock         private nl.tudelft.unischeduler.authentication.JwtUtil mockJwtUtil;
 @Mock         private nl.tudelft.unischeduler.authentication.MyUserDetailsService mockUserDetailsService;
 @Mock         private nl.tudelft.unischeduler.sysinteract.SysInteractor mockSysInteractor;

 @InjectMocks     private nl.tudelft.unischeduler.sysinteract.SysInteractController sysInteractControllerUnderTest;

@Before
public void setUp() throws Exception {
 initMocks(this);
                     }
                
    @Test     public void testAddCourse() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.sysinteract.SysInteract sysInteraction = new nl.tudelft.unischeduler.sysinteract.SysInteract(new java.util.Hashtable<>(java.util.Map.ofEntries()));
                            when( mockSysInteractor .addCourse(new nl.tudelft.unischeduler.utilentities.Course(0L, "name", java.util.Set.of(new nl.tudelft.unischeduler.user.User()), 2021, 1))).thenReturn( "result" );

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.addCourse(sysInteraction);

        // Verify the results
 assertEquals( "result" , result) ;
    }
                                                    
    @Test     public void testAddCourse_SysInteractorThrowsURISyntaxException() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.sysinteract.SysInteract sysInteraction = new nl.tudelft.unischeduler.sysinteract.SysInteract(new java.util.Hashtable<>(java.util.Map.ofEntries()));
        when( mockSysInteractor .addCourse(new nl.tudelft.unischeduler.utilentities.Course(0L, "name", java.util.Set.of(new nl.tudelft.unischeduler.user.User()), 2021, 1))).thenThrow(java.net.URISyntaxException.class);

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.addCourse(sysInteraction);

        // Verify the results
 assertEquals( "result" , result) ;
    }
                
    @Test     public void testAddUser() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.sysinteract.SysInteract sysInteraction = new nl.tudelft.unischeduler.sysinteract.SysInteract(new java.util.Hashtable<>(java.util.Map.ofEntries()));
        final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockSysInteractor .addUser(any(nl.tudelft.unischeduler.user.User.class))).thenReturn( "result" );

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.addUser(sysInteraction,request);

        // Verify the results
 assertEquals( "result" , result) ;
    }
                                                                                    
    @Test     public void testReportCorona() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
                            when( mockUserDetailsService .loadUserByUsername("netId")).thenReturn( null );
                            when( mockSysInteractor .reportCorona("username")).thenReturn( "result" );

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.reportCorona(request);

        // Verify the results
 assertEquals( "result" , result) ;
    }
                                                    
    @Test     public void testReportCorona_MyUserDetailsServiceThrowsUsernameNotFoundException() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
        when( mockUserDetailsService .loadUserByUsername("netId")).thenThrow(org.springframework.security.core.userdetails.UsernameNotFoundException.class);
                            when( mockSysInteractor .reportCorona("username")).thenReturn( "result" );

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.reportCorona(request);

        // Verify the results
 assertEquals( "result" , result) ;
    }
                
    @Test     public void testReportCorona_SysInteractorThrowsURISyntaxException() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
                            when( mockUserDetailsService .loadUserByUsername("netId")).thenReturn( null );
        when( mockSysInteractor .reportCorona("username")).thenThrow(java.net.URISyntaxException.class);

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.reportCorona(request);

        // Verify the results
 assertEquals( "result" , result) ;
    }
                
    @Test     public void testCourseInformation() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.sysinteract.SysInteract sysInteraction = new nl.tudelft.unischeduler.sysinteract.SysInteract(new java.util.Hashtable<>(java.util.Map.ofEntries()));
                            when( mockSysInteractor .courseInformation(new nl.tudelft.unischeduler.utilentities.Course(0L, "name", java.util.Set.of(new nl.tudelft.unischeduler.user.User()), 2021, 1))).thenReturn( new java.lang.Object[]{"value"} );

    // Run the test
 final java.lang.Object[] result =  sysInteractControllerUnderTest.courseInformation(sysInteraction);

        // Verify the results
    }
                                                    
    @Test     public void testCourseInformation_SysInteractorThrowsURISyntaxException() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.sysinteract.SysInteract sysInteraction = new nl.tudelft.unischeduler.sysinteract.SysInteract(new java.util.Hashtable<>(java.util.Map.ofEntries()));
        when( mockSysInteractor .courseInformation(new nl.tudelft.unischeduler.utilentities.Course(0L, "name", java.util.Set.of(new nl.tudelft.unischeduler.user.User()), 2021, 1))).thenThrow(java.net.URISyntaxException.class);

    // Run the test
 final java.lang.Object[] result =  sysInteractControllerUnderTest.courseInformation(sysInteraction);

        // Verify the results
    }
                
    @Test     public void testStudentSchedule() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
                            when( mockUserDetailsService .loadUserByUsername("netId")).thenReturn( null );
                            when( mockSysInteractor .studentSchedule("username")).thenReturn( new nl.tudelft.unischeduler.utilentities.Lecture[]{new nl.tudelft.unischeduler.utilentities.Lecture(0)} );

    // Run the test
 final nl.tudelft.unischeduler.utilentities.Lecture[] result =  sysInteractControllerUnderTest.studentSchedule(request);

        // Verify the results
    }
                                                    
    @Test     public void testStudentSchedule_MyUserDetailsServiceThrowsUsernameNotFoundException() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
        when( mockUserDetailsService .loadUserByUsername("netId")).thenThrow(org.springframework.security.core.userdetails.UsernameNotFoundException.class);
                            when( mockSysInteractor .studentSchedule("username")).thenReturn( new nl.tudelft.unischeduler.utilentities.Lecture[]{new nl.tudelft.unischeduler.utilentities.Lecture(0)} );

    // Run the test
 final nl.tudelft.unischeduler.utilentities.Lecture[] result =  sysInteractControllerUnderTest.studentSchedule(request);

        // Verify the results
    }
                
    @Test     public void testStudentSchedule_SysInteractorThrowsURISyntaxException() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
                            when( mockUserDetailsService .loadUserByUsername("netId")).thenReturn( null );
        when( mockSysInteractor .studentSchedule("username")).thenThrow(java.net.URISyntaxException.class);

    // Run the test
 final nl.tudelft.unischeduler.utilentities.Lecture[] result =  sysInteractControllerUnderTest.studentSchedule(request);

        // Verify the results
    }
                
    @Test     public void testTeacherSchedule() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
                            when( mockUserDetailsService .loadUserByUsername("netId")).thenReturn( null );
                            when( mockSysInteractor .teacherSchedule("username")).thenReturn( new nl.tudelft.unischeduler.utilentities.Lecture[]{new nl.tudelft.unischeduler.utilentities.Lecture(0)} );

    // Run the test
 final nl.tudelft.unischeduler.utilentities.Lecture[] result =  sysInteractControllerUnderTest.teacherSchedule(request);

        // Verify the results
    }
                                                    
    @Test     public void testTeacherSchedule_MyUserDetailsServiceThrowsUsernameNotFoundException() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
        when( mockUserDetailsService .loadUserByUsername("netId")).thenThrow(org.springframework.security.core.userdetails.UsernameNotFoundException.class);
                            when( mockSysInteractor .teacherSchedule("username")).thenReturn( new nl.tudelft.unischeduler.utilentities.Lecture[]{new nl.tudelft.unischeduler.utilentities.Lecture(0)} );

    // Run the test
 final nl.tudelft.unischeduler.utilentities.Lecture[] result =  sysInteractControllerUnderTest.teacherSchedule(request);

        // Verify the results
    }
                
    @Test     public void testTeacherSchedule_SysInteractorThrowsURISyntaxException() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
                            when( mockUserDetailsService .loadUserByUsername("netId")).thenReturn( null );
        when( mockSysInteractor .teacherSchedule("username")).thenThrow(java.net.URISyntaxException.class);

    // Run the test
 final nl.tudelft.unischeduler.utilentities.Lecture[] result =  sysInteractControllerUnderTest.teacherSchedule(request);

        // Verify the results
    }
                
    @Test     public void testCreateLecture() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.sysinteract.SysInteract sysInteraction = new nl.tudelft.unischeduler.sysinteract.SysInteract(new java.util.Hashtable<>(java.util.Map.ofEntries()));
                            when( mockSysInteractor .createLecture(0L,"teacherNetId",2021,1,java.time.Duration.ofDays(0L))).thenReturn( "result" );

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.createLecture(sysInteraction);

        // Verify the results
 assertEquals( "result" , result) ;
    }
                                                    
    @Test     public void testCreateLecture_SysInteractorThrowsURISyntaxException() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.sysinteract.SysInteract sysInteraction = new nl.tudelft.unischeduler.sysinteract.SysInteract(new java.util.Hashtable<>(java.util.Map.ofEntries()));
        when( mockSysInteractor .createLecture(0L,"teacherNetId",2021,1,java.time.Duration.ofDays(0L))).thenThrow(java.net.URISyntaxException.class);

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.createLecture(sysInteraction);

        // Verify the results
 assertEquals( "result" , result) ;
    }
                
    @Test     public void testExtract_username() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
                            when( mockUserDetailsService .loadUserByUsername("netId")).thenReturn( null );

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.extract_username(request);

        // Verify the results
 assertEquals( "result" , result) ;
    }
                                                    
    @Test     public void testExtract_username_MyUserDetailsServiceThrowsUsernameNotFoundException() throws Exception {
    // Setup
                final javax.servlet.http.HttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
                            when( mockJwtUtil .extractUsername("token")).thenReturn( "result" );
        when( mockUserDetailsService .loadUserByUsername("netId")).thenThrow(org.springframework.security.core.userdetails.UsernameNotFoundException.class);

    // Run the test
 final java.lang.String result =  sysInteractControllerUnderTest.extract_username(request);

        // Verify the results
 assertEquals( "result" , result) ;
    }
}

