package nl.tudelft.unischeduler.scheduleedit.core;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import nl.tudelft.unischeduler.scheduleedit.exception.IllegalDateException;
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
            throws IllegalDateException, ConnectionException {
        LocalDateTime now = checkBeforeNow(until);
        try {
            teacherService.cancelLectures(teacherNetId, now, until);
        } catch (IOException e) {
            throw new ConnectionException("The connection with the database failed", e);
        }
    }

    public void reportTeacherSick(String teacherNetId) throws ConnectionException {
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
            throws IllegalDateException, ConnectionException {
        LocalDateTime now = checkBeforeNow(until);
        try {
            studentService.cancelStudentAttendance(studentNetId, now, until);
            studentService.setUserSick(studentNetId, now);
        } catch (IOException e) {
            throw new ConnectionException("The connection with the database failed", e);
        }
    }

    public void reportStudentSick(String studentNetId) throws ConnectionException {
        LocalDateTime until = LocalDateTime.now(clock).plus(14, ChronoUnit.DAYS);
        reportStudentSick(studentNetId, until);
    }
}
