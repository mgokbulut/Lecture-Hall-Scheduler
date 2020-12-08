package nl.tudelft.unischeduler.scheduleedit.core;

import java.time.Clock;
import java.time.LocalDate;
import nl.tudelft.unischeduler.scheduleedit.exception.IllegalDateException;

public class ScheduleEditModule {

    private Clock clock;

    public ScheduleEditModule(Clock clock) {
        this.clock = clock;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    /**
     * Reports the teacher sick and notifies the students that their class will be cancelled.
     *
     * @param teacherNetId The netId of the teacher that is sick.
     * @param until The LocalDate until the teacher is sick (inclusive).
     */
    public void reportTeacherSick(int teacherNetId, LocalDate until) throws IllegalDateException {
        if (until.isBefore(LocalDate.now(clock))) {
            throw new IllegalDateException("the supplied date is before the current date");
        }
    }
}
