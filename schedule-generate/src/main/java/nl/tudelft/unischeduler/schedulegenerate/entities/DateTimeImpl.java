package nl.tudelft.unischeduler.schedulegenerate.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class DateTimeImpl {

    /**
     * Does nothing, as there is nothing to do!
     */
    public DateTimeImpl() {
    }

    /**
     * Gives a Date object, useful class
     * for testing the Generator.
     *
     * @return a Date object
     */
    public Date getDate() {
        return new Date();
    }

    /**
     * Gives a Calendar object, useful class
     * for testing the Generator.
     *
     * @return a GregorianCalendar object
     */
    public GregorianCalendar getCal() {
        return new GregorianCalendar();
    }
}