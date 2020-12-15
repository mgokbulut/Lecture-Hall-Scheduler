package nl.tudelft.unischeduler.scheduleedit.core;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import nl.tudelft.unischeduler.scheduleedit.exception.IllegalDateException;
import nl.tudelft.unischeduler.scheduleedit.services.TeacherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ScheduleEditModuleTests {

    Clock fixedClock;
    Instant instant;

    public Clock getFixedClock() {
        return fixedClock;
    }

    public void setFixedClock(Clock fixedClock) {
        this.fixedClock = fixedClock;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    @BeforeEach
    public void setUp() {
        instant = Instant.parse("2000-01-01T12:00:00.00Z");
        fixedClock = Clock.fixed(instant, ZoneId.systemDefault());
    }

    @Test
    public void reportTeacherTest() throws IOException {
        TeacherService teacherService = mock(TeacherService.class);
        ScheduleEditModule module = new ScheduleEditModule(fixedClock, teacherService);
        final String teacherNetiD = "testId";
        LocalDate start = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        LocalDate until = LocalDate.parse("2000-01-19");

        module.reportTeacherSick(teacherNetiD, until);

        verify(teacherService, times(1)).cancelLectures(teacherNetiD, start, until);
    }

    @Test
    public void cancelPastTest() throws IOException {
        TeacherService teacherService = mock(TeacherService.class);
        ScheduleEditModule module = new ScheduleEditModule(fixedClock, teacherService);
        final String teacherNetiD = "testId";
        LocalDate until = LocalDate.parse("1999-12-31");

        Assertions.assertThrows(IllegalDateException.class, () -> {
            module.reportTeacherSick(teacherNetiD, until);
        });

        verify(teacherService, Mockito.never()).cancelLectures(any(), any(), any());
    }

    @Test
    public void singleDayTest() throws IOException {
        TeacherService teacherService = mock(TeacherService.class);
        ScheduleEditModule module = new ScheduleEditModule(fixedClock, teacherService);
        final String teacherNetiD = "testId";
        LocalDate start = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        LocalDate until = LocalDate.parse("2000-01-01");

        module.reportTeacherSick(teacherNetiD, until);

        verify(teacherService, times(1)).cancelLectures(teacherNetiD, start, until);
    }
}
