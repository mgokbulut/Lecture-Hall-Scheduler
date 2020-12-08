package nl.tudelft.unischeduler.scheduleedit.services;

import java.io.IOException;
import java.time.Period;
import org.springframework.stereotype.Service;

/**
 * This package acts as a placeholder until the communication Part is worked out.
 */
@Service
public class DataBaseService {

    /**
     * Marks all the lectures of the lecturer during the timeslot as online.
     *
     * @param lecturerNetId The netId of the lecturer for which to cancel the lecture.
     * @param period The date period for which to cancel the lecture.
     */
    public void cancelLectures(int lecturerNetId, Period period) throws IOException {
        //TODO: this is a stub, and in the future should actually send the data.
    }
}
