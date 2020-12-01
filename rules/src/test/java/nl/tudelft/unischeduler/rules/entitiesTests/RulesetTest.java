package nl.tudelft.unischeduler.rules.entitiesTests;

import nl.tudelft.unischeduler.rules.entities.Ruleset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RulesetTest {

    static Ruleset makeRuleset(){
        return new Ruleset();
    }

    @Test
    void getBreakTime() {
        Ruleset test = makeRuleset();
        long expected = 0;
        long actual = test.getBreakTime();
        assertEquals(expected, actual);
    }

    @Test
    void getMaxDays() {
        Ruleset test = makeRuleset();
        int expected = 0;
        int actual = test.getMaxDays();
        assertEquals(expected, actual);
    }

    @Test
    void setThresholds() {
        Ruleset test = makeRuleset();
        int[][] expected = {{1}, {1}};
        test.setThresholds(expected);
        int[][] actual = test.getThresholds();
        assertEquals(expected, actual);
    }

    @Test
    void setBreakTime() {
        Ruleset test = makeRuleset();
        long expected = 200;
        test.setBreakTime(expected);
        long actual = test.getBreakTime();
        assertEquals(expected, actual);
    }

    @Test
    void setMaxDays() {
        Ruleset test = makeRuleset();
        int expected = 200;
        test.setMaxDays(expected);
        int actual = test.getMaxDays();
        assertEquals(expected, actual);
    }
}