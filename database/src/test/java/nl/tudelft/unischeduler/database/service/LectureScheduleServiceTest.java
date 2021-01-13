package nl.tudelft.unischeduler.database.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import nl.tudelft.unischeduler.database.classroom.Classroom;
import nl.tudelft.unischeduler.database.classroom.ClassroomRepository;
import nl.tudelft.unischeduler.database.lecture.Lecture;
import nl.tudelft.unischeduler.database.lecture.LectureRepository;
import nl.tudelft.unischeduler.database.lectureschedule.LectureSchedule;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleRepository;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleService;
import nl.tudelft.unischeduler.database.schedule.Schedule;
import nl.tudelft.unischeduler.database.schedule.ScheduleRepository;
import nl.tudelft.unischeduler.database.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class LectureScheduleServiceTest {
    private transient List<User> users;

    private transient List<Lecture> lectures;

    private transient List<LectureSchedule> lectureSchedules;

    private final transient LectureScheduleRepository lectureScheduleRepository =
            Mockito.mock(LectureScheduleRepository.class);

    private final transient LectureRepository lectureRepository =
            Mockito.mock(LectureRepository.class);

    private final transient ClassroomRepository classroomRepository =
            Mockito.mock(ClassroomRepository.class);

    private final transient ScheduleRepository scheduleRepository =
            Mockito.mock(ScheduleRepository.class);

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 1, 0, 0).getTimeInMillis());

    @BeforeEach
    void setup() {
        users = new ArrayList<>(
                List.of(
                        new User("a.baran@student.tudelft.nl", "STUDENT", true, timestamp),
                        new User("a.kuba@student.tudelft.nl", "STUDENT", true, timestamp),
                        new User("teacher@tudelft.nl", "TEACHER", true, timestamp)
                ));

        lectures = new ArrayList<>(
                List.of(
                        new Lecture(0L, 1L, 1L, "sanders@tudelft.nl",
                                timestamp,  new Time(7200000),  false),
                        new Lecture(1L, 1L, 1L, "teacher@tudelft.nl",
                                new Timestamp(timestamp.getTime() + 10800000),
                                new Time(7200000), false),
                        new Lecture(2L, 1L, 2L, "teacher@tudelft.nl",
                                new Timestamp(timestamp.getTime() + 21600000), new Time(7200000),
                                false)
                ));

        lectureSchedules = new ArrayList<>(
                List.of(
                        new LectureSchedule(0L, 1L),
                        new LectureSchedule(1L, 2L),
                        new LectureSchedule(1L, 3L)
                ));

    }

    @Test
    public void assignLectureToSchedule() {
        Schedule schedule = new Schedule(1L, "a.baran@student.tudelft.nl");
        when(scheduleRepository.findByUser("a.baran@student.tudelft.nl"))
                .thenReturn(java.util.Optional.of(schedule));

        when(lectureScheduleRepository.findByLectureIdAndScheduleId(2L, 1L))
                .thenReturn(Optional.empty());

        when(lectureScheduleRepository.save(any(LectureSchedule.class)))
                .thenAnswer(x -> x.getArguments()[0]);

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        Assertions.assertEquals(new ResponseEntity<>(
                        new LectureSchedule(1L, 1L), HttpStatus.OK),
                lectureScheduleService.assignLectureToSchedule("a.baran@student.tudelft.nl", 1L));
    }

    @Test
    public void assignLectureToScheduleErrorTest() {
        Schedule schedule = new Schedule(1L, "a.baran@student.tudelft.nl");
        when(scheduleRepository.findByUser("a.baran@student.tudelft.nl"))
                .thenReturn(java.util.Optional.of(schedule));

        when(lectureScheduleRepository.findByLectureIdAndScheduleId(2L, 1L))
                .thenReturn(Optional.empty());

        doThrow(new NullPointerException()).when(lectureScheduleRepository).save(any());

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);

        ResponseEntity<?> responseEntity = lectureScheduleService.assignLectureToSchedule(
                "a.baran@student.tudelft.nl", 1L);

        Assertions.assertEquals(ResponseEntity.badRequest().build(), responseEntity);
    }

    @Test
    public void assignLectureToScheduleErrorTest1() {
        when(scheduleRepository.findByUser("a.baran@student.tudelft.nl"))
                .thenReturn(Optional.empty());

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);

        ResponseEntity<?> responseEntity = lectureScheduleService.assignLectureToSchedule(
                "a.baran@student.tudelft.nl", 1L);

        Assertions.assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    public void assignLectureToScheduleErrorTest2() {
        Schedule schedule = new Schedule(1L, "a.baran@student.tudelft.nl");
        when(scheduleRepository.findByUser("a.baran@student.tudelft.nl"))
                .thenReturn(java.util.Optional.of(schedule));

        LectureSchedule lectureSchedule = new LectureSchedule(2L, 1L);

        when(lectureScheduleRepository.findByLectureIdAndScheduleId(2L, 1L))
                .thenReturn(Optional.of(lectureSchedule));

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);

        ResponseEntity<?> responseEntity = lectureScheduleService
                .assignLectureToSchedule("a.baran@student.tudelft.nl", 2L);

        Assertions.assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    public void removeLectureFromScheduleTest() {

        when(lectureScheduleRepository.findAllByLectureId(1L)).thenReturn(lectureSchedules);

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        lectureScheduleService.removeLectureFromSchedule(1L);

        verify(lectureScheduleRepository, times(1)).deleteLectureSchedulesByLectureId(1L);
        verifyNoMoreInteractions(lectureScheduleRepository);
    }

    @Test
    public void removeLectureFromScheduleErrorTest() {
        doThrow(new NullPointerException()).when(lectureScheduleRepository)
                .deleteLectureSchedulesByLectureId(null);

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);

        ResponseEntity<?> responseEntity = lectureScheduleService.removeLectureFromSchedule(null);

        Assertions.assertEquals(ResponseEntity.badRequest().build(), responseEntity);
    }

    @Test
    public void cancelStudentAttendance() {

        when(scheduleRepository.findByUser("a.baran@student.tudelft.nl"))
                .thenReturn(Optional.of(new Schedule(1L, "a.baran@student.tudelft.nl")));

        when(lectureRepository.findAllByStartTimeDateBetween(timestamp,
               new Timestamp(timestamp.getTime() + 10000))).thenReturn(List.of(lectures.get(0)));

        when(lectureScheduleRepository.findAllByScheduleId(1L))
                .thenReturn(List.of(lectureSchedules.get(0)));

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        lectureScheduleService.cancelStudentAttendance("a.baran@student.tudelft.nl", timestamp,
                new Timestamp(timestamp.getTime() + 10000));

        verify(lectureScheduleRepository, times(1))
                .deleteByLectureIdAndScheduleId(0L, 1L);
        verify(lectureScheduleRepository, times(1))
                .findAllByScheduleId(1L);
        verifyNoMoreInteractions(lectureScheduleRepository);
    }

    @Test
    public void cancelStudentAttendanceErrorTest() {
        when(scheduleRepository.findByUser("a.baran@student.tudelft.nl"))
                .thenReturn(Optional.empty());

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);

        ResponseEntity<?> responseEntity = lectureScheduleService.cancelStudentAttendance(
                "a.baran@student.tudelft.nl", timestamp,
                new Timestamp(timestamp.getTime() + 10000));

        Assertions.assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    public void cancelStudentAttendanceErrorTest2() {
        doThrow(new NullPointerException()).when(scheduleRepository).findByUser(any());

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);

        ResponseEntity<?> responseEntity = lectureScheduleService.cancelStudentAttendance(
                "a.baran@student.tudelft.nl", timestamp,
                new Timestamp(timestamp.getTime() + 10000));

        Assertions.assertEquals(ResponseEntity.badRequest().build(), responseEntity);
    }

    @Test
    public void removeStudentFromLectureTest() {

        when(scheduleRepository.findByUser("a.baran@student.tudelft.nl"))
                .thenReturn(Optional.of(new Schedule(1L, "a.baran@student.tudelft.nl")));

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        lectureScheduleService.removeStudentFromLecture("a.baran@student.tudelft.nl", 0L);

        verify(lectureScheduleRepository, times(1))
                .deleteByLectureIdAndScheduleId(0L, 1L);
        verifyNoMoreInteractions(lectureScheduleRepository);
    }

    @Test
    public void removeStudentFromLectureErrorTest() {
        when(scheduleRepository.findByUser("a.baran@student.tudelft.nl"))
                .thenReturn(Optional.empty());

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        ResponseEntity<?> responseEntity = lectureScheduleService.removeStudentFromLecture(
                "a.baran@student.tudelft.nl", 0L);

        Assertions.assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    public void removeStudentFromLectureErrorTest2() {
        doThrow(new NullPointerException()).when(scheduleRepository).findByUser(any());

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        ResponseEntity<?> responseEntity = lectureScheduleService.removeStudentFromLecture(
                "a.baran@student.tudelft.nl", 0L);

        Assertions.assertEquals(ResponseEntity.badRequest().build(), responseEntity);
    }

    @Test
    public void getStudentScheduleTest() {
        Classroom classroom = new Classroom(30, "Test", "Building", 1);
        when(scheduleRepository.findByUser("a.baran@student.tudelft.nl"))
                .thenReturn(Optional.of(new Schedule(1L, "a.baran@student.tudelft.nl")));
        when(lectureScheduleRepository.findAllByScheduleId(1L))
                .thenReturn(List.of(lectureSchedules.get(0)));
        when(lectureRepository.findById(0L))
                .thenReturn(Optional.ofNullable(lectures.get(0)));
        when(classroomRepository.findById(1L)).thenReturn(
                Optional.of(classroom));

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        Assertions.assertTrue(Arrays.equals(new Object[]{lectures.get(0), classroom},
                lectureScheduleService.getStudentSchedule("a.baran@student.tudelft.nl").get(0)));
    }

    @Test
    public void getStudentScheduleErrorTest() {
        doThrow(new NullPointerException()).when(scheduleRepository).findByUser(any());

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        List<Object []> responseEntity = lectureScheduleService
                .getStudentSchedule("a.baran@student.tudelft.nl");

        Assertions.assertNull(responseEntity);
    }

    @Test
    public void getTeacherScheduleTest() {
        Classroom classroom = new Classroom(1L, 30, "Test", "Building", 1);

        when(lectureRepository.findAllByTeacher("sanders@tudelft.nl"))
                .thenReturn(List.of(lectures.get(0)));
        when(classroomRepository.findById(1L)).thenReturn(
                Optional.of(classroom));

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        Assertions.assertTrue(Arrays.equals(new Object[]{lectures.get(0), classroom},
                lectureScheduleService.getTeacherSchedule("sanders@tudelft.nl").get(0)));
    }

    @Test
    public void getTeacherScheduleErrorTest() {
        doThrow(new NullPointerException()).when(lectureRepository).findAllByTeacher(any());

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        List<Object []> responseEntity = lectureScheduleService
                .getTeacherSchedule("sanders@tudelft.nl");

        Assertions.assertNull(responseEntity);
    }

    @Test
    public void getAllLecturesInCourseTest() {
        Classroom classroom = new Classroom(1L, 30, "Test", "Building", 1);

        when(lectureRepository.findAllByCourse(2L))
                .thenReturn(List.of(lectures.get(2)));
        when(classroomRepository.findById(1L)).thenReturn(
                Optional.of(classroom));

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        Assertions.assertTrue(Arrays.equals(new Object[]{lectures.get(2), classroom},
                lectureScheduleService.getAllLecturesInCourse(2L).get(0)));
    }

    @Test
    public void getAllLecturesInCourseTestFail() {
        when(lectureRepository.findAllByCourse(2L))
                .thenReturn(List.of(lectures.get(2)));
        when(classroomRepository.findById(1L)).thenReturn(
                Optional.empty());

        LectureScheduleService lectureScheduleService = new LectureScheduleService(
                lectureScheduleRepository, scheduleRepository,
                lectureRepository, classroomRepository);


        Assertions.assertNull(lectureScheduleService.getAllLecturesInCourse(2L));
    }

}
