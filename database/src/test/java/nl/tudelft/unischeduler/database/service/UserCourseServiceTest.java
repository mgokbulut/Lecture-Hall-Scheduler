package nl.tudelft.unischeduler.database.service;

import nl.tudelft.unischeduler.database.classroom.Classroom;
import nl.tudelft.unischeduler.database.classroom.ClassroomRepository;
import nl.tudelft.unischeduler.database.course.Course;
import nl.tudelft.unischeduler.database.course.CourseService;
import nl.tudelft.unischeduler.database.lecture.Lecture;
import nl.tudelft.unischeduler.database.lecture.LectureRepository;
import nl.tudelft.unischeduler.database.sicklog.SickLog;
import nl.tudelft.unischeduler.database.sicklog.SickLogRepository;
import nl.tudelft.unischeduler.database.user.User;
import nl.tudelft.unischeduler.database.user.UserRepository;
import nl.tudelft.unischeduler.database.usercourse.UserCourse;
import nl.tudelft.unischeduler.database.usercourse.UserCourseRepository;
import nl.tudelft.unischeduler.database.usercourse.UserCourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class UserCourseServiceTest {

    private transient List<User> users;

    private transient List<UserCourse> userCourses;

    private transient List<Lecture> lectures;

    private final transient UserCourseRepository userCourseRepository =
            Mockito.mock(UserCourseRepository.class);

    private final transient UserRepository userRepository =
            Mockito.mock(UserRepository.class);

    private final transient LectureRepository lectureRepository =
            Mockito.mock(LectureRepository.class);

    private final transient ClassroomRepository classroomRepository =
            Mockito.mock(ClassroomRepository.class);

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 01, 0, 0).getTimeInMillis());

    @BeforeEach
    void setup(){
        users = new ArrayList<>(
                List.of(
                        new User("a.baran@student.tudelft.nl", "STUDENT", true, timestamp),
                        new User("a.kuba@student.tudelft.nl", "STUDENT", true, timestamp),
                        new User("teacher@tudelft.nl", "TEACHER", true, timestamp)
                ));

        userCourses = new ArrayList<>(
                List.of(
                        new UserCourse("a.baran@student.tudelft.nl", 1L),
                        new UserCourse("a.kuba@student.tudelft.nl", 3L),
                        new UserCourse("teacher@tudelft.nl", 2L)
                ));

        lectures = new ArrayList<>(
                List.of(
                        new Lecture(0L, 1L, 1L, "sanders@tudelft.nl",
                                timestamp,  new Time(7200000),  false),
                        new Lecture(1L, 1L, 1L, "sanders@tudelft.nl",
                                new Timestamp(timestamp.getTime() + 10800000),
                                new Time(7200000), false),
                        new Lecture(2L, 1L, 2L, "sanders@tudelft.nl",
                                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false)
                ));

    }

    @Test
    public void getStudentsInCourseTest(){
        when(userCourseRepository.findAllByCourseId(1L)).thenReturn(List.of(userCourses.get(0)));
        when(userRepository.findByNetId("a.baran@student.tudelft.nl"))
                .thenReturn(java.util.Optional.ofNullable(users.get(0)));

        UserCourseService userCourseService = new UserCourseService(
                userCourseRepository, userRepository, lectureRepository, classroomRepository);

        Assertions.assertEquals(List.of(users.get(0)), userCourseService.getStudentsInCourse(1L));

        verify(userCourseRepository, times(1)).findAllByCourseId(1L);
        verify(userRepository, times(1)).findByNetId("a.baran@student.tudelft.nl");
        verifyNoMoreInteractions(userCourseRepository);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void addStudentToCourseTest(){
        UserCourse userCourse = new UserCourse("Test", 1L);
        when(userCourseRepository.findByCourseIdAndNetId(1L, "Test"))
                .thenReturn(Optional.empty());
        when(userCourseRepository.save(Mockito.any(UserCourse.class))).thenAnswer(x -> x.getArguments()[0]);

        UserCourseService userCourseService = new UserCourseService(
                userCourseRepository, userRepository, lectureRepository, classroomRepository);

        Assertions.assertEquals(new ResponseEntity<>(List.of(userCourse), HttpStatus.OK), userCourseService
                .addStudentToCourse(List.of("Test"), 1L));

        verify(userCourseRepository, times(1)).save(Mockito.any(UserCourse.class));
        verify(userCourseRepository, times(1)).findByCourseIdAndNetId( 1L, "Test");
        verifyNoMoreInteractions(userCourseRepository);
    }

    @Test
    public void getPossibleLecturesTest(){
        Classroom classroom = new Classroom(30, "Test", "Building", 1);
        when(userCourseRepository.findAllByNetId("a.baran@student.tudelft.nl"))
                .thenReturn(List.of(userCourses.get(0), userCourses.get(2)));
        when(lectureRepository.findAllByCourse(2L))
                .thenReturn(List.of(lectures.get(2)));
        when(classroomRepository.findById(1L)).thenReturn(
                Optional.of(classroom));

        UserCourseService userCourseService = new UserCourseService(
                userCourseRepository, userRepository, lectureRepository, classroomRepository);

        Assertions.assertTrue(Arrays.equals(new Object[]{lectures.get(2), classroom},
                userCourseService.getPossibleLectures("a.baran@student.tudelft.nl").get(0)));
    }

    @Test
    public void getPossibleLecturesTestFail(){
        Classroom classroom = new Classroom(30, "Test", "Building", 1);
        when(userCourseRepository.findAllByNetId("a.baran@student.tudelft.nl"))
                .thenReturn(List.of(userCourses.get(0), userCourses.get(2)));
        when(lectureRepository.findAllByCourse(2L))
                .thenReturn(List.of(lectures.get(2)));
        when(classroomRepository.findById(10L)).thenReturn(
                Optional.of(classroom));

        UserCourseService userCourseService = new UserCourseService(
                userCourseRepository, userRepository, lectureRepository, classroomRepository);

        Assertions.assertNull(userCourseService.getPossibleLectures("a.baran@student.tudelft.nl"));
    }

    @Test
    public void getStudentsInCourseTestFail(){
        when(userCourseRepository.findAllByCourseId(1L)).thenReturn(List.of(userCourses.get(0)));
        when(userRepository.findByNetId("a.baran@student.tudelft.nl"))
                .thenReturn(Optional.empty());

        UserCourseService userCourseService = new UserCourseService(
                userCourseRepository, userRepository, lectureRepository, classroomRepository);

        Assertions.assertNull(userCourseService.getStudentsInCourse(1L));
    }
}
