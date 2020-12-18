package nl.tudelft.unischeduler.database.service;

import nl.tudelft.unischeduler.database.classroom.Classroom;
import nl.tudelft.unischeduler.database.course.Course;
import nl.tudelft.unischeduler.database.course.CourseRepository;
import nl.tudelft.unischeduler.database.lecture.Lecture;
import nl.tudelft.unischeduler.database.lecture.LectureRepository;
import nl.tudelft.unischeduler.database.lecture.LectureService;
import nl.tudelft.unischeduler.database.lectureschedule.LectureSchedule;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleService;
import nl.tudelft.unischeduler.database.schedule.Schedule;
import nl.tudelft.unischeduler.database.sicklog.SickLog;
import nl.tudelft.unischeduler.database.sicklog.SickLogRepository;
import nl.tudelft.unischeduler.database.sicklog.SickLogService;
import nl.tudelft.unischeduler.database.user.User;
import nl.tudelft.unischeduler.database.user.UserRepository;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class LectureServiceTest {

    private transient List<Lecture> lectures;

    private transient List<Course> courses;

    private final transient LectureRepository lectureRepository =
            Mockito.mock(LectureRepository.class);

    private final transient CourseRepository courseRepository =
            Mockito.mock(CourseRepository.class);

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 01, 0, 0).getTimeInMillis());

    @BeforeEach
    void setup(){
        lectures = new ArrayList<>(
                List.of(
                        new Lecture(0L, 0L, 0L, "sanders@tudelft.nl",
                                timestamp,  new Time(7200000),  false),
                        new Lecture(1L, 1L, 0L, "sanders@tudelft.nl",
                                new Timestamp(timestamp.getTime() + 10800000),
                                new Time(7200000), false),
                        new Lecture(2L, 2L, 0L, "sanders@tudelft.nl",
                                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000), false)
                ));

        courses = new ArrayList<>(
                List.of(
                        new Course(0L, "ADS", 1),
                        new Course(1L, "SEM", 2),
                        new Course(2L, "AD", 2)
                ));
    }

    @Test
    public void getLecturesWithCoursesTest() {
        when(lectureRepository.findAll()).thenReturn(lectures);

        when(courseRepository.findById(0L)).thenReturn(Optional.of((courses.get(0))));

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        Assertions.assertTrue(Arrays.equals(new Object[]{lectures.get(0), courses.get(0)},
                lectureService.getLecturesWithCourses().get(0)));
    }

    @Test
    public void getLecturesInCourseTest(){
        when(lectureRepository.findAllByCourse(0L)).thenReturn(List.of(lectures.get(0)));

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        Assertions.assertEquals(List.of(lectures.get(0)),
                lectureService.getLecturesInCourse(0L,
                        new Timestamp(timestamp.getTime() - 1000), new Time(2*timestamp.getTime())));
    }

    @Test
    public void setClassroomToEmptyTest() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setClassroom(-1L);

        Assertions.assertEquals(new ResponseEntity<>(lectures.get(0), HttpStatus.OK),
                lectureService.setClassroomToEmpty(0L));
    }

    @Test
    public void setTimeTest() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setStartTimeDate(new Timestamp(timestamp.getTime() - 100000L));

        Assertions.assertEquals(new ResponseEntity<>(lectures.get(0), HttpStatus.OK),
                lectureService.setTime(0L, new Timestamp(timestamp.getTime() - 100000L)));
    }

    @Test
    public void setClassroomTest() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setClassroom(2L);

        Assertions.assertEquals(new ResponseEntity<>(lectures.get(0), HttpStatus.OK),
                lectureService.setClassroom(0L, 2L));
    }

    @Test
    public void setLectureToOnlineTest1() {
        when(lectureRepository.findAllByTeacherAndStartTimeDateBetween(
                "sanders@tudelft.nl", timestamp, new Timestamp(7200000 + timestamp.getTime())))
                .thenReturn(List.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setMovedOnline(true);
        lectures.get(0).setClassroom(-2L);

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                lectureService.setLectureToOnline("sanders@tudelft.nl", timestamp,
                        new Timestamp(7200000 + timestamp.getTime()), true));
    }

    @Test
    public void setLectureToOnlineTest2() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setMovedOnline(true);
        lectures.get(0).setClassroom(-2L);

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                lectureService.setLectureToOnline(0L, true));
    }

    @Test
    public void setLectureToOfflineTest1() {
        when(lectureRepository.findAllByTeacherAndStartTimeDateGreaterThanEqual(
                "sanders@tudelft.nl", timestamp))
                .thenReturn(List.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setMovedOnline(false);
        lectures.get(0).setClassroom(-1L);

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                lectureService.setLectureToOffline("sanders@tudelft.nl", timestamp));
    }

    @Test
    public void setLectureToOfflineTest2() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setMovedOnline(false);
        lectures.get(0).setClassroom(-1L);

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                lectureService.setLectureToOffline(0L));
    }

    @Test
    public void createLectureTest() {
        Lecture lecture = new Lecture(-1L, 1L, "TestTeacher",
                timestamp, new Time(1000000L), false);
        when(lectureRepository
                .findAllByClassroomAndCourseAndTeacherAndStartTimeDateAndDurationAndMovedOnline(
                        -1L, 1L, "TestTeacher", timestamp, new Time(1000000L),
                        false)).thenReturn(Optional.empty());

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        Assertions.assertEquals(new ResponseEntity<>(lecture, HttpStatus.OK),
                lectureService.createLecture(1L,
                        "TestTeacher", timestamp, new Time(1000000L), false));
    }


}
