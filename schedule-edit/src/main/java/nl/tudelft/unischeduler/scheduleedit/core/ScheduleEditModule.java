package nl.tudelft.unischeduler.scheduleedit.core;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import nl.tudelft.unischeduler.scheduleedit.exception.IllegalDateException;
import nl.tudelft.unischeduler.scheduleedit.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEditModule {

    private Clock clock;
    private TeacherService teacherService;

    public ScheduleEditModule(@Autowired Clock clock, @Autowired TeacherService teacherService) {
        this.clock = clock;
        this.teacherService = teacherService;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public TeacherService getDataBaseService() {
        return teacherService;
    }

    public void setDataBaseService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * Reports the teacher sick and notifies the students that their class will be cancelled.
     *
     * @param teacherNetId The netId of the teacher that is sick.
     * @param until The LocalDate until the teacher is sick (inclusive).
     */
    public void reportTeacherSick(String teacherNetId, LocalDate until)
            throws IllegalDateException, ConnectionException {
        LocalDate now = LocalDate.now(clock);
        if (until.isBefore(now)) {
            throw new IllegalDateException("the supplied date is before the current date");
        }
        try {
            teacherService.cancelLectures(teacherNetId, now, until);
        } catch (IOException e) {
            throw new ConnectionException("The connection with the database failed", e);
        }
    }

    public void reportTeacherSick(String teacherNetId) throws ConnectionException {
        LocalDate until = LocalDate.now(clock).plus(2, ChronoUnit.WEEKS);
        reportTeacherSick(teacherNetId, until);
    }

}
