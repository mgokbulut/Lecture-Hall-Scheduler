package nl.tudelft.unischeduler.database.service;

import nl.tudelft.unischeduler.database.sicklog.SickLog;
import nl.tudelft.unischeduler.database.sicklog.SickLogRepository;
import nl.tudelft.unischeduler.database.sicklog.SickLogService;
import nl.tudelft.unischeduler.database.user.User;
import nl.tudelft.unischeduler.database.user.UserRepository;
import nl.tudelft.unischeduler.database.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class UserServiceTest {

    private transient List<User> users;

    private transient List<SickLog> sickLogs;

    private final transient UserRepository userRepository =
            Mockito.mock(UserRepository.class);

    private final transient SickLogRepository sickLogRepository =
            Mockito.mock(SickLogRepository.class);

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

        sickLogs = new ArrayList<>(
                List.of(
                        new SickLog(0L, "a.kuba@student.tudelft.nl", new Date(timestamp.getTime()), true),
                        new SickLog(1L, "a.kuba@student.tudelft.nl", new Date(timestamp.getTime() + 10000), true),
                        new SickLog(2L, "a.kuba@student.tudelft.nl", new Date(timestamp.getTime() + 100000000), true)
                ));
    }

    @Test
    public void getAllUsersTest(){
        when(userRepository.findAll()).thenReturn(users);
        UserService userService = new UserService(userRepository, sickLogRepository);

        Assertions.assertEquals(userService.getAllUsers(), users);

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void setUserSickTest(){
        when(sickLogRepository.findAllByUser("a.kuba@student.tudelft.nl")).thenReturn(sickLogs);
        when(userRepository.findByNetId("a.kuba@student.tudelft.nl"))
                .thenReturn(java.util.Optional.ofNullable(users.get(1)));

        UserService userService = new UserService(userRepository, sickLogRepository);

        Assertions.assertTrue(Arrays.equals(new Object[]{users.get(1), true}, userService.getUser("a.kuba@student.tudelft.nl")));

        verify(sickLogRepository, times(1)).findAllByUser("a.kuba@student.tudelft.nl");
        verify(userRepository, times(1)).findByNetId("a.kuba@student.tudelft.nl");
        verifyNoMoreInteractions(sickLogRepository);
        verifyNoMoreInteractions(userRepository);
    }
}
