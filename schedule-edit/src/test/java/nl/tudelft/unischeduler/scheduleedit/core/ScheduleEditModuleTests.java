package nl.tudelft.unischeduler.scheduleedit.core;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.Data;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import nl.tudelft.unischeduler.scheduleedit.exception.IllegalDateException;
import nl.tudelft.unischeduler.scheduleedit.services.CourseService;
import nl.tudelft.unischeduler.scheduleedit.services.StudentService;
import nl.tudelft.unischeduler.scheduleedit.services.TeacherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Data
public class ScheduleEditModuleTests {

    private final String testId = "testId";
    Clock fixedClock;
    Instant instant;
    TeacherService teacherService;
    StudentService studentService;
    CourseService courseService;
    ScheduleEditModule module;


    /**
     * sets up all the standard mocks and is run before every test.
     */
    @BeforeEach
    public void setUp() {
        instant = Instant.parse("2000-01-01T12:00:00.00Z");
        fixedClock = Clock.fixed(instant, ZoneId.systemDefault());
        teacherService = mock(TeacherService.class);
        studentService = mock(StudentService.class);
        courseService = mock(CourseService.class);
        module = new ScheduleEditModule(fixedClock, teacherService, studentService, courseService);
    }

    @Test
    public void reportTeacherTest() throws IOException {
        LocalDate start = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        LocalDate until = LocalDate.parse("2000-01-14");

        module.reportTeacherSick(testId);

        verify(teacherService, times(1)).cancelLectures(testId, start, until);
    }

    @Test
    public void cancelPastTest() throws IOException {
        LocalDate until = LocalDate.parse("1999-12-31");

        Assertions.assertThrows(IllegalDateException.class, () -> {
            module.reportTeacherSick(testId, until);
        });

        verify(teacherService, Mockito.never()).cancelLectures(any(), any(), any());
    }

    @Test
    public void singleDayTest() throws IOException {
        LocalDate start = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        LocalDate until = LocalDate.parse("2000-01-15");

        module.reportTeacherSick(testId, until);

        verify(teacherService, times(1)).cancelLectures(testId, start, until);
    }

    @Test
    public void nullTeacherDateTest() throws IOException {
        Assertions.assertThrows(IllegalDateException.class, () -> {
            module.reportTeacherSick(testId, null);
        });

        verify(studentService, never()).cancelStudentAttendance(any(), any(), any());
    }

    @Test
    public void databaseTeacherThrowsTest() throws IOException {
        Mockito.doThrow(new IOException())
                .when(teacherService).cancelLectures(any(), any(), any());

        Assertions.assertThrows(ConnectionException.class, () -> {
            module.reportTeacherSick(testId);
        });
    }

    @Test
    public void reportStudentTest() throws IOException {
        LocalDate start = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        LocalDate until = LocalDate.parse("2000-01-14");

        module.reportStudentSick(testId);

        verify(studentService, times(1)).cancelStudentAttendance(testId, start, until);
    }

    @Test
    public void studentPastTest() throws IOException {
        LocalDate until = LocalDate.parse("1999-12-31");

        Assertions.assertThrows(IllegalDateException.class, () -> {
            module.reportStudentSick(testId, until);
        });

        verify(studentService, Mockito.never()).cancelStudentAttendance(any(), any(), any());
    }

    @Test
    public void singleDayStudentTest() throws IOException {
        LocalDate start = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        LocalDate until = LocalDate.parse("2000-01-01");

        module.reportStudentSick(testId, until);

        verify(studentService, times(1)).cancelStudentAttendance(testId, start, until);
    }

    @Test
    public void nullStudentDateTest() throws IOException {
        Assertions.assertThrows(IllegalDateException.class, () -> {
            module.reportStudentSick(testId, null);
        });

        verify(studentService, never()).cancelStudentAttendance(any(), any(), any());
    }

    @Test
    public void databaseStudentThrowsTest() throws IOException {
        Mockito.doThrow(new IOException())
                .when(studentService).cancelStudentAttendance(any(), any(), any());

        Assertions.assertThrows(ConnectionException.class, () -> {
            module.reportStudentSick(testId);
        });
    }
}
