package nl.tudelft.unischeduler.database.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import nl.tudelft.unischeduler.database.lectureschedule.LectureSchedule;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleRepository;
import nl.tudelft.unischeduler.database.schedule.Schedule;
import nl.tudelft.unischeduler.database.schedule.ScheduleRepository;
import nl.tudelft.unischeduler.database.sicklog.SickLog;
import nl.tudelft.unischeduler.database.sicklog.SickLogRepository;
import nl.tudelft.unischeduler.database.user.User;
import nl.tudelft.unischeduler.database.user.UserRepository;
import nl.tudelft.unischeduler.database.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class UserServiceTest {

    private transient List<User> users;

    private transient List<SickLog> sickLogs;

    private final transient UserRepository userRepository =
            Mockito.mock(UserRepository.class);

    private final transient SickLogRepository sickLogRepository =
            Mockito.mock(SickLogRepository.class);

    private final transient LectureScheduleRepository lectureScheduleRepository =
            Mockito.mock(LectureScheduleRepository.class);

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

        sickLogs = new ArrayList<>(
                List.of(
                        new SickLog(0L, "a.kuba@student.tudelft.nl",
                                new Date(timestamp.getTime()), true),
                        new SickLog(1L, "a.kuba@student.tudelft.nl",
                                new Date(timestamp.getTime() + 10000), true),
                        new SickLog(2L, "a.kuba@student.tudelft.nl",
                                new Date(timestamp.getTime() + 100000000), true)
                ));
    }

    @Test
    public void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(users);
        UserService userService = new UserService(userRepository, sickLogRepository,
                lectureScheduleRepository, scheduleRepository);

        Assertions.assertEquals(userService.getAllUsers(), users);

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void setUserSickTest() {
        when(sickLogRepository.findAllByUser("a.kuba@student.tudelft.nl")).thenReturn(sickLogs);
        when(userRepository.findByNetId("a.kuba@student.tudelft.nl"))
                .thenReturn(java.util.Optional.ofNullable(users.get(1)));

        UserService userService = new UserService(userRepository, sickLogRepository,
                lectureScheduleRepository, scheduleRepository);

        Assertions.assertTrue(Arrays.equals(new Object[]{users.get(1), true},
                userService.getUser("a.kuba@student.tudelft.nl")));

        verify(sickLogRepository, times(1)).findAllByUser("a.kuba@student.tudelft.nl");
        verify(userRepository, times(1)).findByNetId("a.kuba@student.tudelft.nl");
        verifyNoMoreInteractions(sickLogRepository);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void setUserSickTest1() {
        sickLogs.add(0, new SickLog(0L, "a.kuba@student.tudelft.nl",
                new Date(timestamp.getTime() + 112209600000L), true));
        when(sickLogRepository.findAllByUser("a.kuba@student.tudelft.nl")).thenReturn(sickLogs);
        when(userRepository.findByNetId("a.kuba@student.tudelft.nl"))
                .thenReturn(java.util.Optional.ofNullable(users.get(1)));

        UserService userService = new UserService(userRepository, sickLogRepository,
                lectureScheduleRepository, scheduleRepository);

        Assertions.assertTrue(Arrays.equals(new Object[]{users.get(1), false},
                userService.getUser("a.kuba@student.tudelft.nl")));

        verify(sickLogRepository, times(1)).findAllByUser("a.kuba@student.tudelft.nl");
        verify(userRepository, times(1)).findByNetId("a.kuba@student.tudelft.nl");
        verifyNoMoreInteractions(sickLogRepository);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void setUserSickTest2() {
        when(sickLogRepository.findAllByUser("a.kuba@student.tudelft.nl"))
                .thenReturn(new ArrayList<>());
        when(userRepository.findByNetId("a.kuba@student.tudelft.nl"))
                .thenReturn(java.util.Optional.ofNullable(users.get(1)));

        UserService userService = new UserService(userRepository, sickLogRepository,
                lectureScheduleRepository, scheduleRepository);

        Assertions.assertTrue(Arrays.equals(new Object[]{users.get(1), true},
                userService.getUser("a.kuba@student.tudelft.nl")));

        verify(sickLogRepository, times(1)).findAllByUser("a.kuba@student.tudelft.nl");
        verify(userRepository, times(1)).findByNetId("a.kuba@student.tudelft.nl");
        verifyNoMoreInteractions(sickLogRepository);
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    public void setUserSickErrorTest() {
        doThrow(new NullPointerException()).when(sickLogRepository).findAllByUser(any());

        UserService userService = new UserService(userRepository, sickLogRepository,
                lectureScheduleRepository, scheduleRepository);

        Object [] responseEntity = userService.getUser("a.baran@student.tudelft.nl");

        Assertions.assertNull(responseEntity);
    }

    @Test
    public void getStudentsInLectureTest() {
        when(userRepository.findByNetId("a.kuba@student.tudelft.nl"))
                .thenReturn(java.util.Optional.ofNullable(users.get(1)));

        LectureSchedule lectureSchedule = new LectureSchedule(1L, 2L);

        when(lectureScheduleRepository.findAllByLectureId(1L))
                .thenReturn(List.of(lectureSchedule));

        when(scheduleRepository.findById(2L))
                .thenReturn(Optional.of(new Schedule(2L, "a.kuba@student.tudelft.nl")));

        UserService userService = new UserService(userRepository, sickLogRepository,
                lectureScheduleRepository, scheduleRepository);

        Assertions.assertTrue(Arrays.equals(new Object[]{users.get(1), true},
                userService.getStudentsInLecture(1L).get(0)));

        verify(lectureScheduleRepository, times(1)).findAllByLectureId(1L);
        verify(userRepository, times(2)).findByNetId("a.kuba@student.tudelft.nl");
        verifyNoMoreInteractions(userRepository);
    }



    @Test
    public void getStudentsInLectureErrorTest() {
        doThrow(new NullPointerException()).when(lectureScheduleRepository)
                .findAllByLectureId(any());

        UserService userService = new UserService(userRepository, sickLogRepository,
                lectureScheduleRepository, scheduleRepository);

        List<Object[]> responseEntity = userService.getStudentsInLecture(1L);

        Assertions.assertNull(responseEntity);
    }
}
