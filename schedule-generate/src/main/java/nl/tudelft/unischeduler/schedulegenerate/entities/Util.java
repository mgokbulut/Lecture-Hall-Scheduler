package nl.tudelft.unischeduler.schedulegenerate.entities;

import java.sql.Timestamp;
import java.util.Calendar;

public class Util {

    /**
     * Calculates the distance in days between two dates excluding
     * weekends.
     *
     * @param c1 the timestamp of the first date
     * @param c2 the timestamp of the second date
     * @return the number of days difference between them
     */
    public static int calDistance(Timestamp c1, Timestamp c2) {
        int maxIterations = 100; // just to never get stuck in the while loop
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(c1.getTime());

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(c2.getTime());
        int numDays = 0;
        int iteration = 0;
        while (cal1.before(cal2) && iteration < maxIterations) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                    && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                numDays = numDays + 1;
            }
            cal1.add(Calendar.DATE, 1);
            iteration++;
        }

        return numDays;
    }

    /**
     * method that increases the timestamp by one day,
     * but only for working days. So if it's friday,
     * the day will be increased to the next monday.
     *
     * @param t the timestamp you want to add a day to
     * @return a new timestamp that is set to one day later
     */
    public static Timestamp nextDay(Timestamp t) {
        Calendar cal1 = Calendar.getInstance();

        cal1.setTimeInMillis(t.getTime());

        if (Calendar.FRIDAY == cal1.get(Calendar.DAY_OF_WEEK)) {
            cal1.add(Calendar.DAY_OF_YEAR, 3);
        } else {
            cal1.add(Calendar.DAY_OF_YEAR, 1);
        }
        return new Timestamp(cal1.getTime().getTime());
    }

    /**
     * Helper method that tells us if a lecture and its potential
     * start time overlaps with an already-scheduled lecture.
     *
     * @param lecture the lecture we are trying to schedule
     * @param potentialStartTime the start time we want to give to this lecture
     * @param scheduledLecture the already-scheduled lecture we want to check against
     * @return whether there would be overlap should the lecture be scheduled at this time
     */
    public static boolean overlap(Lecture lecture, long interval,
                                  Timestamp potentialStartTime, Lecture scheduledLecture) {
        Timestamp scheduledLectureEndTime = scheduledLecture.computeEndTime();
        Timestamp schLecEndTiWithInterval = new Timestamp(scheduledLectureEndTime.getTime()
                + interval);
        // if the start time is during the other lecture
        if (potentialStartTime.after(scheduledLecture.getStartTime())
                && potentialStartTime.before(schLecEndTiWithInterval)) {
            return true;
        }
        // if the end time is during the other lecture
        Timestamp potentialEndTime = new Timestamp(potentialStartTime.getTime()
                + lecture.getDuration().getTime());
        if (potentialEndTime.after(scheduledLecture.getStartTime())
                && potentialStartTime.before(schLecEndTiWithInterval)) {
            return true;
        }
        // if the start and end times are before and after the scheduled lecture
        if (potentialStartTime.before(scheduledLecture.getStartTime())
                && potentialEndTime.after(schLecEndTiWithInterval)) {
            return true;
        }
        return false;
    }


}
