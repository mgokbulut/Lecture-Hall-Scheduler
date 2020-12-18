package nl.tudelft.unischeduler.database.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import nl.tudelft.unischeduler.database.sicklog.SickLog;
import nl.tudelft.unischeduler.database.sicklog.SickLogRepository;
import nl.tudelft.unischeduler.database.sicklog.SickLogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class SickLogServiceTest {

    private transient List<SickLog> sickLogs;

    private final transient SickLogRepository sickLogRepository =
            Mockito.mock(SickLogRepository.class);

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 10, 0, 0).getTimeInMillis());

    @BeforeEach
    void setup() {
        sickLogs = new ArrayList<>(
                List.of(
                        new SickLog(0L, "test-user1", new Date(timestamp.getTime()), true),
                        new SickLog(1L, "test-user2", new Date(timestamp.getTime() + 10000), true),
                        new SickLog(2L, "test-user3",
                                new Date(timestamp.getTime() + 100000000), true)
                ));
    }

    @Test
    public void getAllSickLogsTest() {
        when(sickLogRepository.findAll()).thenReturn(sickLogs);
        SickLogService sickLogService = new SickLogService(sickLogRepository);

        Assertions.assertEquals(sickLogService.getAllSickLogs(), sickLogs);

        verify(sickLogRepository, times(1)).findAll();
        verifyNoMoreInteractions(sickLogRepository);
    }

    @Test
    public void setUserSickTest() {
        when(sickLogRepository.save(Mockito.any(SickLog.class)))
                .thenAnswer(x -> x.getArguments()[0]);
        SickLogService sickLogService = new SickLogService(sickLogRepository);

        Assertions.assertEquals(new ResponseEntity<>(
                new SickLog(null, "Test", new Date(timestamp.getTime()), false), HttpStatus.OK),
                sickLogService.setUserSick("Test", timestamp));

        verify(sickLogRepository, times(1)).save(Mockito.any(SickLog.class));
        verify(sickLogRepository, times(1))
                .findAllByUserAndReportSickAndFinished("Test",
                        new Date(timestamp.getTime()), false);
        verifyNoMoreInteractions(sickLogRepository);
    }

}
