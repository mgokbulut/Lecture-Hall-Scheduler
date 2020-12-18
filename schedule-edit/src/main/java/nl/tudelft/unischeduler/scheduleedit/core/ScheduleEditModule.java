package nl.tudelft.unischeduler.scheduleedit.core;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import nl.tudelft.unischeduler.scheduleedit.exception.IllegalDateException;
import nl.tudelft.unischeduler.scheduleedit.services.CourseService;
import nl.tudelft.unischeduler.scheduleedit.services.StudentService;
import nl.tudelft.unischeduler.scheduleedit.services.TeacherService;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class ScheduleEditModule {

    private Clock clock;
    private TeacherService teacherService;
    private StudentService studentService;
    private CourseService courseService;


    private LocalDateTime checkBeforeNow(LocalDateTime future) throws IllegalDateException {
        if (future == null) {
            throw new IllegalDateException("There was no date specified");
        }
        LocalDateTime now = LocalDateTime.now(clock);
        if (future.isBefore(now)) {
            throw new IllegalDateException("the supplied date is before the current date");
        }
        return now;
    }

    /**
     * Reports the teacher sick and notifies the students that their class will be cancelled.
     *
     * @param teacherNetId The netId of the teacher that is sick.
     * @param until The LocalDate until the teacher is sick (inclusive).
     */
    public void reportTeacherSick(String teacherNetId, LocalDateTime until)
            throws IllegalDateException, IOException {
        LocalDateTime now = checkBeforeNow(until);
        teacherService.cancelLectures(teacherNetId, now, until);

    }

    public void reportTeacherSick(String teacherNetId) throws IOException {
        LocalDateTime until = LocalDateTime.now(clock).plus(14, ChronoUnit.DAYS);
        reportTeacherSick(teacherNetId, until);
    }

    /**
     * Reports the student sick and reduces the amount of students attending the lecture.
     *
     * @param studentNetId The netId of the student that is sick.
     * @param until The LocalDate until the teacher is sick (inclusive).
     */
    public void reportStudentSick(String studentNetId, LocalDateTime until)
            throws IllegalDateException, IOException {
        LocalDateTime now = checkBeforeNow(until);
        studentService.cancelStudentAttendance(studentNetId, now, until);
        studentService.setUserSick(studentNetId, now);

    }

    public void reportStudentSick(String studentNetId) throws IOException {
        LocalDateTime until = LocalDateTime.now(clock).plus(14, ChronoUnit.DAYS);
        reportStudentSick(studentNetId, until);
    }

    /**
     * Adds a course to the database this course does not have any lectures to it assigned yet.
     *
     * @param courseName The name of the course.
     * @param year The year the course would be thought.
     * @return the id of the newly created course.
     * @throws ConnectionException When the connection with the database fails.
     */
    public long createCourse(String courseName, int year) throws IOException {
        return courseService.createCourse(courseName, year);
    }

    /**
     * Calculates the LocalDate of the monday of week in year.
     *
     * @param year The year for which to calculate.
     * @param week The week for which to calculate.
     * @return The LocalDate which is the first day of the week in the year.
     */
    public LocalDate calculateStartOfWeek(int year, int week) {
        try {
            LocalDate temp = LocalDate.ofYearDay(year, (week - 1) * 7 + 1);
            while (!temp.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                temp = temp.minusDays(1);
            }
            return temp;
        } catch (DateTimeException e) {
            throw new IllegalDateException("The week "
                    + week
                    + " does not exist in year "
                    + year);
        }
    }

    /**
     * Adds a new lecture to the database using the courseService.
     *
     * @param courseId The id of the course to which to add the lecture.
     * @param teacherNetId The netId of the teacher for which to add a course.
     * @param year The year in which the course should be added.
     * @param week the week in the year that the course should be in.
     * @param duration The duration of the lecture.
     * @return The id of the new lecture.
     * @throws ConnectionException When the connection with the database fails.
     */
    public long createLecture(long courseId,
                              String teacherNetId,
                              int year,
                              int week,
                              Duration duration)
            throws IOException {
        LocalDateTime startWeek = calculateStartOfWeek(year, week).atStartOfDay();
        return courseService.createLecture(courseId, teacherNetId, startWeek, duration);
    }

    /**
     * Adds a group of students to a course.
     *
     * @param students A list of netIds of students that should be added to a course.
     * @param courseId The id of the course to add the students to.
     * @throws ConnectionException When the connection with the database fails.
     */
    public void addStudentGroupLecture(List<String> students, long courseId)
            throws IOException {
        courseService.addStudentsToCourse(students, courseId);
    }
}
