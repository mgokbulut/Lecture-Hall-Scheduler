package Rules;
import java.sql.Timestamp;
import java.sql.Time;

public class RulesModule {

    class Lecture {



    }


    private int[][] thresholds;
    private long breakTime;
    private int maxDays;
    private Ruleset rules;

    public void Rules() {

        Ruleset rules = new Ruleset(); //rulesTable.getRulesFromDataBase();

    }

    public int getMaxDays() {
        return maxDays;
    }

    public int getCapacity(int init) {

        int index = 0;
        for (int i = 1; i < thresholds[0].length; i++) {

            if (init < thresholds[i][0]) break;

            index = i;
        }

        return (init * thresholds[init][1] / 100);

    }

    private Timestamp getNextStartTime(Lecture lecture) {

        long endTime = 0; //lecture.startTime.getTime + lecture.duration;
        return new Timestamp(endTime + breakTime);

    }

    public boolean availableForSignUp(Lecture lecture) {
        int maxStudentsInRoom = 0; //calculateCapacity(lecture.room.maxCapacity);
        return true; //lecture.attendingStudents < maxStudentsInRoom;
    }

    public boolean overlap(Lecture l1, Lecture l2) {
        long start1 = 0; //l1.startTime.getTime();
        long start2 = 0; //l2.startTime.getTime();
        if (start1 < start2){
            return true; //(getNextStartTime(l1).getTime() > start2);
        } else {
            return true; //(getNextStartTime(l2).getTime() > start1);
        }

    }

    public void getNewRules() {
        this.rules = new Ruleset(); //rulesTable.getRulesFromDataBase();
        //Tells the scheduler that the rules have changed and should update the schedule when there is time.
        //scheduler.reschedule();
    }

    public void updateRules() {
        Ruleset rules = new Ruleset(thresholds, breakTime, maxDays);
        //rulesTable.updateRules(rules);
    }

}
