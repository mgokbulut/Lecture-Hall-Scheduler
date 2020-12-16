package nl.tudelft.unischeduler.scheduleedit.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Service;

/**
 * This package acts as a placeholder until the communication Part is worked out.
 */
@Service
public class TeacherService {

    /**
     * Marks all the lectures of the lecturer during the timeslot as online.
     *
     * @param teacherNetId The netId of the lecturer for which to cancel the lectures.
     * @param start The date for which to start canceling lectures (inclusive).
     * @param end the date for which to end canceling lectures (inclusive).
     */
    public void cancelLectures(String teacherNetId, LocalDate start, LocalDate end)
            throws IOException {
        //TODO: this is a stub, and in the future should actually send the data.
    }
}
