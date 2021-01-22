package nl.tudelft.unischeduler.database.service;

import static org.mockito.Mockito.when;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import nl.tudelft.unischeduler.database.course.Course;
import nl.tudelft.unischeduler.database.course.CourseRepository;
import nl.tudelft.unischeduler.database.lecture.Lecture;
import nl.tudelft.unischeduler.database.lecture.LectureRepository;
import nl.tudelft.unischeduler.database.lecture.LectureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class LectureServiceTest {

    private transient List<Lecture> lectures;

    private transient List<Course> courses;

    private final transient LectureRepository lectureRepository =
            Mockito.mock(LectureRepository.class);

    private final transient CourseRepository courseRepository =
            Mockito.mock(CourseRepository.class);

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 1, 0, 0).getTimeInMillis());

    @BeforeEach
    void setup() {
        lectures = new ArrayList<>(
                List.of(
                        new Lecture(0L, 0L, 0L, "sanders@tudelft.nl",
                                timestamp,  new Time(7200000),  false),
                        new Lecture(1L, 1L, 0L, "sanders@tudelft.nl",
                                new Timestamp(timestamp.getTime() + 10800000),
                                new Time(7200000), false),
                        new Lecture(2L, 2L, 0L, "sanders@tudelft.nl",
                                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000),
                                false)
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
    public void getLecturesWithCoursesTestExceptionCourseRepo() {
        when(lectureRepository.findAll()).thenReturn(lectures);

        when(courseRepository.findById(0L)).thenThrow(IllegalArgumentException.class);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);

        //when(lectureService.getLecturesWithCourses()).thenThrow(Exception.class);

        Assertions.assertEquals(null, lectureService.getLecturesWithCourses());
    }

    @Test
    public void getLecturesWithCoursesTestExceptionLectureRepo() {
        when(lectureRepository.findAll()).thenThrow(IllegalArgumentException.class);

        when(courseRepository.findById(0L)).thenReturn(Optional.of((courses.get(0))));

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);

        //when(lectureService.getLecturesWithCourses()).thenThrow(Exception.class);

        Assertions.assertEquals(null, lectureService.getLecturesWithCourses());
    }

    @Test
    public void getLecturesInCourseTest() {
        when(lectureRepository.findAllByCourse(0L)).thenReturn(List.of(lectures.get(0)));

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        Assertions.assertEquals(List.of(lectures.get(0)),
                lectureService.getLecturesInCourse(0L,
                        new Timestamp(timestamp.getTime() - 1000),
                        new Time(2 * timestamp.getTime())));
    }

    @Test
    public void setClassroomToEmptyTest() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.empty());

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        Assertions.assertEquals(404,
                lectureService.setClassroomToEmpty(0L)
                .getStatusCodeValue());
    }

    @Test
    public void setClassroomToEmptyTestException1() {
        when(lectureRepository.findById(0L))
            .thenThrow(IllegalArgumentException.class);
        //when(lectureRepository.save(Mockito.any(Lecture.class)))
        //   .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);

        lectures.get(0).setClassroom(-1L);

        Assertions.assertEquals(400,
            lectureService.setClassroomToEmpty(0L).getStatusCodeValue());
    }

    @Test
    public void setClassroomToEmptyTestException2() {
        when(lectureRepository.findById(0L))
            .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
            .thenThrow(IllegalArgumentException.class);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);

        lectures.get(0).setClassroom(-1L);

        Assertions.assertEquals(400,
            lectureService.setClassroomToEmpty(0L).getStatusCodeValue());
    }

    @Test
    public void setTimeTest() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        Assertions.assertEquals(new ResponseEntity<>(lectures.get(0), HttpStatus.OK),
                lectureService.setTime(0L, new Timestamp(timestamp.getTime() - 100000L)));
    }

    @Test
    public void setTimeTestIsEmpty() {
        Optional<Lecture> res = Optional.empty();
        when(lectureRepository.findById(0L))
            .thenReturn(res);

        when(lectureRepository.save(Mockito.any(Lecture.class)))
            .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);

        Assertions.assertEquals(404,
            lectureService.setTime(0L, new Timestamp(timestamp.getTime() - 100000L))
                .getStatusCodeValue());
    }

    @Test
    public void setTimeTestException() {
        when(lectureRepository.findById(0L))
            .thenThrow(IllegalArgumentException.class);

        when(lectureRepository.save(Mockito.any(Lecture.class)))
            .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);

        Assertions.assertEquals(400,
            lectureService.setTime(0L, new Timestamp(timestamp.getTime() - 100000L))
                .getStatusCodeValue());
    }

    @Test
    public void setTimeTestException2() {
        when(lectureRepository.findById(0L))
            .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
            .thenThrow(IllegalArgumentException.class);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);

        Assertions.assertEquals(400,
            lectureService.setTime(0L, new Timestamp(timestamp.getTime() - 100000L))
                .getStatusCodeValue());
    }

    @Test
    public void setClassroomTest() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);


        Assertions.assertEquals(new ResponseEntity<>(lectures.get(0), HttpStatus.OK),
                lectureService.setClassroom(0L, 2L));

        Assertions.assertEquals((long) lectures.get(0).getClassroom(), 2L);
    }

    @Test
    public void setClassroomIsEmpty() {
        when(lectureRepository.findById(0L))
            .thenReturn(Optional.empty());

        when(lectureRepository.save(Mockito.any(Lecture.class)))
            .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);


        Assertions.assertEquals(404,
            lectureService.setClassroom(0L, 2L).getStatusCodeValue());
    }

    @Test
    public void setClassroomException() {
        when(lectureRepository.findById(0L))
            .thenThrow(IllegalArgumentException.class);

        when(lectureRepository.save(Mockito.any(Lecture.class)))
            .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);


        Assertions.assertEquals(400,
            lectureService.setClassroom(0L, 2L).getStatusCodeValue());
    }

    @Test
    public void setClassroomException2() {
        when(lectureRepository.findById(0L))
            .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
            .thenThrow(IllegalArgumentException.class);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);


        Assertions.assertEquals(400,
            lectureService.setClassroom(0L, 2L)
                .getStatusCodeValue());

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

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                lectureService.setLectureToOnline("sanders@tudelft.nl", timestamp,
                        new Timestamp(7200000 + timestamp.getTime()), true));

        Assertions.assertTrue(lectures.get(0).isMovedOnline());
        Assertions.assertEquals(lectures.get(0).getClassroom(), -2L);
    }

    @Test
    public void setLectureToOnlineTestException() {
        when(lectureRepository.findAllByTeacherAndStartTimeDateBetween(
            "sanders@tudelft.nl", timestamp, new Timestamp(7200000 + timestamp.getTime())))
            .thenReturn(List.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
            .thenThrow(IllegalArgumentException.class);

        LectureService lectureService = new LectureService(
            lectureRepository, courseRepository);

        Assertions.assertEquals(400,
            lectureService.setLectureToOnline("sanders@tudelft.nl", timestamp,
                new Timestamp(7200000 + timestamp.getTime()), true).getStatusCodeValue());

        Assertions.assertTrue(lectures.get(0).isMovedOnline());
        Assertions.assertEquals(lectures.get(0).getClassroom(), -2L);
    }

    @Test
    public void setLectureToOnlineTest2() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                lectureService.setLectureToOnline(0L, true));

        Assertions.assertTrue(lectures.get(0).isMovedOnline());
        Assertions.assertEquals(lectures.get(0).getClassroom(), -2L);
    }

    @Test
    public void setLectureToOfflineTest1() {
        when(lectureRepository.findAllByTeacherAndStartTimeDateGreaterThanEqual(
                "sanders@tudelft.nl", timestamp))
                .thenReturn(List.of(lectures.get(0), lectures.get(1)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        final LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setMovedOnline(true);
        lectures.get(1).setMovedOnline(true);
        lectures.get(1).setClassroom(-2L);

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                lectureService.setLectureToOffline("sanders@tudelft.nl", timestamp));

        Assertions.assertFalse(lectures.get(0).isMovedOnline());
        Assertions.assertTrue(lectures.get(1).isMovedOnline());
    }

    @Test
    public void setLectureToOfflineTest2() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setMovedOnline(true);

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                lectureService.setLectureToOffline(0L));

        Assertions.assertFalse(lectures.get(0).isMovedOnline());
    }

    @Test
    public void setLectureToOfflineTest3() {
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.of(lectures.get(0)));

        when(lectureRepository.save(Mockito.any(Lecture.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureService lectureService = new LectureService(
                lectureRepository, courseRepository);

        lectures.get(0).setMovedOnline(true);
        lectures.get(0).setClassroom(-2L);

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK),
                lectureService.setLectureToOffline(0L));

        Assertions.assertTrue(lectures.get(0).isMovedOnline());
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
