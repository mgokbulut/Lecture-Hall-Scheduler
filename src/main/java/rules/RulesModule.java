package rules;

import java.sql.Timestamp;

public class RulesModule {

    class Lecture {
    }

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
    }

    /*
    public void Rules() {

        Ruleset rules = rulesTable.getRulesFromDataBase();

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
        for (; index < thresholds[0].length; index++) {

            if (init < thresholds[index][0]) {
                break;
            }

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
    private Timestamp getNextStartTime(Lecture lecture) {

        long endTime = 0; //lecture.startTime.getTime + lecture.duration;
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
        //int maxStudentsInRoom = calculateCapacity(lecture.room.maxCapacity);
        return true; //lecture.attendingStudents < maxStudentsInRoom;
    }

    /**
     * returns whether two lectures take place at the same time.
     *
     * @param l1 the first lecture
     * @param l2 the second lecture
     * @return true if one lecture starts before the other ends, else false.
     */
    public boolean overlap(Lecture l1, Lecture l2) {
        long start1 = 0; //l1.startTime.getTime();
        long start2 = 0; //l2.startTime.getTime();
        if (start1 < start2) {
            return true; //(getNextStartTime(l1).getTime() > start2);
        } else {
            return true; //(getNextStartTime(l2).getTime() > start1);
        }

    }

    /**
     * retrieves the rules from the stored file
     * and tells the scheduler that the rules have changed.
     */
    public void getNewRules() {
        this.rules = new Ruleset(); //rulesTable.getRulesFromDataBase();
        //Tells the scheduler that the rules have changed
        // and should update the schedule when there is time.
        //scheduler.reschedule();
    }

    /*
    public void updateRules() {
        Ruleset rules = new Ruleset(thresholds, breakTime, maxDays);
        rulesTable.updateRules(rules);
    }
    */
}
