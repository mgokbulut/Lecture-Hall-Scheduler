package Rules;

public class Ruleset {

    private int[][] thresholds;
    private long breakTime;
    private int maxDays;

    public Ruleset() {
        this.breaktime = 0;
        this.maxDays = 0;
    }

    public Ruleset(int[][] thresholds, long breakTime, int maxDays) {
        this.thresholds = thresholds;
        this.breakTime = breakTime;
        this.maxDays = maxDays;
    }

    public int[][] getThresholds() {
        return thresholds;
    }

    public long getBreakTime() {
        return breakTime;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setThresholds(int[][] thresholds) {
        this.thresholds = thresholds;
    }

    public void setBreakTime(long breakTime) {
        this.breakTime = breakTime;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

}
