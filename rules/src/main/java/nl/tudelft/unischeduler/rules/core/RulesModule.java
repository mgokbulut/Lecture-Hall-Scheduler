package nl.tudelft.unischeduler.rules.core;

import java.sql.Timestamp;
import java.util.Arrays;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.entities.Ruleset;

public class RulesModule {

    private int[][] thresholds;
    private long breakTime;
    private int maxDays;

    private Ruleset rules;

    public int[][] getThresholds() {
        return thresholds;
    }

    public void setThresholds(int[][] thresholds) {
        this.thresholds = thresholds;
    }

    public long getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(long breakTime) {
        this.breakTime = breakTime;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public Ruleset getRules() {
        return rules;
    }

    public void setRules(Ruleset rules) {
        this.rules = rules;
        setBreakTime(rules.getBreakTime());
        setMaxDays(rules.getMaxDays());
        setThresholds(rules.getThresholds());
    }

    /*
    public void Rules() {

        Ruleset nl.tudelft.unischeduler.rules.rules = rulesTable.getRulesFromDataBase();

    }
    */

    /**
     * calculates the capacity of a room, given the
     * government restrictions in place.
     *
     * @param init the maximum capacity of the room
     * @return the allowed capacity of the room
     */
    public int getCapacity(int init) {

        int index = 0;
        while (index < thresholds.length) {

            if (init < thresholds[index][0]) {
                index --;
                break;
            }
            if(index + 1 >= thresholds.length) {
                break;
            }
            index++;
        }

        return (init * thresholds[index][1] / 100);

    }

    /**
     * calculates the earliest time another lecture can start after the provided lecture,
     * given the government restrictions in place.
     *
     * @param lecture the prior lecture
     * @return the time at which another lecture can start
     */
    public Timestamp getNextStartTime(Lecture lecture) {

        long endTime = lecture.getStartTime().getTime() + lecture.getDuration().getTime();
        return new Timestamp(endTime + breakTime);

    }


    /**
     * returns whether or not a lecture is at maximum capacity yet.
     *
     * @param lecture they lecture in question
     * @return true if the current number of students is less than the allowed capacity,
     *     false otherwise
     */
    public boolean availableForSignUp(Lecture lecture) {
        int maxStudentsInRoom = getCapacity(lecture.getRoom().getCapacity());
        return lecture.getAttendance() < maxStudentsInRoom;
    }

    /**
     * returns whether two lectures take place at the same time.
     *
     * @param l1 the first lecture
     * @param l2 the second lecture
     * @return true if one lecture starts before the other ends, else false.
     */
    public boolean overlap(Lecture l1, Lecture l2) {
        long start1 = l1.getStartTime().getTime();
        long start2 = l2.getStartTime().getTime();

        return (start1 < start2 && getNextStartTime(l1).getTime() > start2)
                || (start1 > start2 && getNextStartTime(l2).getTime() > start1)
                || (start1 == start2);
    }

    /**
     * retrieves the nl.tudelft.unischeduler.rules.rules from the stored file
     * and tells the scheduler that the nl.tudelft.unischeduler.rules.rules have changed.
     */
    public void getNewRules() {
        this.rules = new Ruleset(); //rulesTable.getRulesFromDataBase();
        //Tells the scheduler that the nl.tudelft.unischeduler.rules.rules have changed
        // and should update the schedule when there is time.
        //scheduler.reschedule();
    }

    /*
    public void updateRules() {
        Ruleset nl.tudelft.unischeduler.rules.rules = new Ruleset(thresholds, breakTime, maxDays);
        rulesTable.updateRules(nl.tudelft.unischeduler.rules.rules);
    }
    */

    /**
     * iterates through all lectures to ensure that the social distancing guidelines
     * are followed, if not then that lecture will be cleared
     * (room removed and students deregistered).
     *
     * @param lectures list of all lectures
     * @return whether or not any lectures violated the rules.
     */
    public boolean verifySchedule(Lecture[] lectures) {
        boolean ret = true;
        Arrays.sort(lectures);
        for (int i = 0; i < lectures.length; i++) {
            if (lectures[i].getRoom() == null) {
                continue;
            }
            int attendance = lectures[i].getAttendance();
            boolean overCapacity = (lectures[i].getRoom() != null)
                    && (getCapacity(lectures[i].getRoom().getCapacity()) <attendance);
            if (overCapacity
                    || (lectures[i].getRoom().equals(lectures[(i + 1) % lectures.length].getRoom())
                    && overlap(lectures[i], lectures[(i + 1) % lectures.length]))) {
                ret &= false;
                //remove building from lecture
                //remove lecture from schedule
            }
        }

        return ret;

    }

}
