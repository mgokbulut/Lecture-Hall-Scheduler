package nl.tudelft.unischeduler.scheduleedit.core;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import nl.tudelft.unischeduler.scheduleedit.exception.IllegalDateException;
import nl.tudelft.unischeduler.scheduleedit.services.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEditModule {

    private Clock clock;
    private DataBaseService dataBaseService;

    public ScheduleEditModule(@Autowired Clock clock, @Autowired DataBaseService dataBaseService) {
        this.clock = clock;
        this.dataBaseService = dataBaseService;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public DataBaseService getDataBaseService() {
        return dataBaseService;
    }

    public void setDataBaseService(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
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
        Period duration = now.until(until);
        try {
            dataBaseService.cancelLectures(teacherNetId, duration);
        } catch (IOException e) {
            throw new ConnectionException("The connection with the database failed", e);
        }
    }

    public void reportTeacherSick(String teacherNetId) throws ConnectionException {
        LocalDate until = LocalDate.now(clock).plus(2, ChronoUnit.WEEKS);
        reportTeacherSick(teacherNetId, until);
    }
}
