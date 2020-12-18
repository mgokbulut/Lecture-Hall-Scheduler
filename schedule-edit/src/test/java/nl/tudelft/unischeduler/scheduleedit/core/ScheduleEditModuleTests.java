package nl.tudelft.unischeduler.scheduleedit.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import lombok.Data;
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

    private final ZoneId zoneId = ZoneId.of("UTC+00:00");
    private final String testId = "testId";
    private final String testCourseName = "test course";
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
        fixedClock = Clock.fixed(instant, zoneId);
        teacherService = mock(TeacherService.class);
        studentService = mock(StudentService.class);
        courseService = mock(CourseService.class);
        module = new ScheduleEditModule(fixedClock, teacherService, studentService, courseService);
    }

    @Test
    public void reportTeacherTest() throws IOException {
        LocalDateTime start = LocalDateTime.ofInstant(instant, zoneId);
        LocalDateTime until = LocalDateTime.parse("2000-01-15T12:00:00");

        module.reportTeacherSick(testId);

        verify(teacherService, times(1)).cancelLectures(testId, start, until);
    }

    @Test
    public void cancelPastTest() throws IOException {
        LocalDateTime until = LocalDate.parse("1999-12-31").atStartOfDay();

        Assertions.assertThrows(IllegalDateException.class, () -> {
            module.reportTeacherSick(testId, until);
        });

        verify(teacherService, Mockito.never()).cancelLectures(any(), any(), any());
    }

    @Test
    public void singleDayTest() throws IOException {
        LocalDateTime start = LocalDateTime.ofInstant(instant, zoneId);
        LocalDateTime until = LocalDate.parse("2000-01-15").atStartOfDay();

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
    public void reportStudentTest() throws IOException {
        LocalDateTime start = LocalDateTime.ofInstant(instant, zoneId);
        LocalDateTime until = LocalDate.parse("2000-01-15").atTime(12, 0);

        module.reportStudentSick(testId);

        verify(studentService, times(1)).cancelStudentAttendance(testId, start, until);
    }

    @Test
    public void studentPastTest() throws IOException {
        LocalDateTime until = LocalDate.parse("1999-12-31").atStartOfDay();

        Assertions.assertThrows(IllegalDateException.class, () -> {
            module.reportStudentSick(testId, until);
        });

        verify(studentService, Mockito.never()).cancelStudentAttendance(any(), any(), any());
    }

    @Test
    public void singleDayStudentTest() throws IOException {
        LocalDateTime start = LocalDateTime.ofInstant(instant, zoneId);
        LocalDateTime until = LocalDate.parse("2000-01-01").atTime(23, 59, 59);

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
    public void createCourseTest() throws IOException {
        Mockito.when(courseService.createCourse(testCourseName, 2000)).thenReturn(1L);
        assertEquals(1L, module.createCourse(testCourseName, 2000));
    }

    @Test
    public void createLectureTest() throws IOException {
        LocalDateTime firstDayOfWeek = LocalDateTime.of(1999, 12, 27, 0, 0);
        Duration duration = Duration.ofHours(1);
        Mockito.when(courseService.createLecture(eq(1L), eq(testId), any(), any()))
                .thenReturn(1L);


        assertEquals(1L, module.createLecture(1L, testId, 2000, 1, duration));

        Mockito.verify(courseService, times(1)).createLecture(
                eq(1L),
                eq(testId),
                eq(firstDayOfWeek),
                eq(duration)
        );
        Mockito.verifyNoMoreInteractions(courseService);
    }

    @Test
    public void addStudentTest() throws IOException {
        List<String> testIds = Collections.singletonList(testId);

        module.addStudentGroupLecture(testIds, 1L);

        verify(courseService, times(1)).addStudentToCourse(any(String.class), eq(1L));
        verifyNoMoreInteractions(courseService);
    }

    @Test
    public void calculateAlreadyStartOfWeekTest() {
        LocalDate monday = LocalDate.parse("2000-01-03");
        LocalDate result = module.calculateStartOfWeek(2000, 2);

        assertEquals(DayOfWeek.MONDAY, monday.getDayOfWeek());
        assertEquals(DayOfWeek.MONDAY, result.getDayOfWeek());
        assertEquals(monday, result);
    }

    @Test
    public void illegalTime() {
        assertThrows(IllegalDateException.class, () -> module.calculateStartOfWeek(2000, -1));
    }

    @Test
    public void yearWith53Weeks() {
        LocalDate expected = LocalDate.parse("2004-12-27");

        assertEquals(DayOfWeek.MONDAY, expected.getDayOfWeek());
        assertEquals(expected, module.calculateStartOfWeek(2004, 53));
    }
}
