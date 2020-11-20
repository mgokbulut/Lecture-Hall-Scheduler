import RuleSet;

public class RulesModule {

    private int[][] thresholds;
    private long breakTime;
    private int maxDays;

    public void Rules() {
        Ruleset rules = rulesTable.getRulesFromDataBase();

    }

    public int getMaxDays() {
        return maxDays;
    }

    public int getCapacity(int init) {

        int index = 0;
        for (int i = 1; i < thresholds[][0].length; i++) {

            if (init < thresholds[i][0]) break;

            index = i;
        }

        return (init * thresholds[init][1]);

    }

    public Timestamp getNextStartTime(Lecture lecture) {

        long endTime = lecture.startTime.getTime + lecture.duration
        return new Timestamp(endTime + break);

    }

    public boolean availableForSignUp(Lecture lecture) {
        int maxStudentsInRoom = calculateCapacity(lecture.room.maxCapacity);
        return lecture.attendingStudents < maxStudentsInRoom
    }

    public boolean overlap(Lecture l1, Lecture l2) {
        long start1 = l1.startTime.getTime;
        long start2 = l2.startTime.getTime;
        if (start1 < start2){
            return (getNextStartTime(l1) > start2);
        } else {
            return (getNextStartTime(l2) > start1);
        }

    }

    public void getNewRules() {
        this.rules = rulesTable.getRulesFromDataBase();
        //Tells the scheduler that the rules have changed and should update the schedule when there is time.
        scheduler.reschedule();
    }

    public void updateRules() {
        Ruleset rules = new RuleSet(thresholds, breakTime, maxDays);
        rulesTable.updateRules(rules);
    }

}
