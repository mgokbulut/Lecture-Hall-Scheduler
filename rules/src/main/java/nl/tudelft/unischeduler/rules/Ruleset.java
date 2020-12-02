package nl.tudelft.unischeduler.rules;

import java.util.Arrays;
import java.util.Objects;

public class Ruleset {

    private int[][] thresholds;
    private long breakTime;
    private int maxDays;

    public Ruleset() {
        this.breakTime = 0;
        this.maxDays = 0;
    }

    /**
     * constructor for ruleset.
     *
     * @param thresholds a 2d array of thresholds, the first value being the the
     *                   capacity of a room, and the second value being the % of the max
     *                   capacity allowed for rooms of a size between this threshold and the next.
     * @param breakTime the minimum time between two lectures being hosted in the same room
     * @param maxDays the maximum amount of time a student should go
     *                without being offered an on-campus activity
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ruleset ruleset = (Ruleset) o;
        return breakTime == ruleset.breakTime
                && maxDays == ruleset.maxDays
                && Arrays.deepEquals(thresholds, ruleset.thresholds);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(breakTime, maxDays);
        result = 31 * result + Arrays.deepHashCode(thresholds);
        return result;
    }

    @Override
    public String toString() {
        return "Ruleset{"
                + "thresholds=" + Arrays.deepToString(thresholds)
                + ", breakTime=" + breakTime
                + ", maxDays=" + maxDays
                + '}';
    }
}
