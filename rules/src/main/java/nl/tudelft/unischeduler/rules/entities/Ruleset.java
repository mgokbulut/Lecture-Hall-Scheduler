package nl.tudelft.unischeduler.rules.entities;

import java.util.Arrays;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ruleset {
    private int[][] thresholds;
    private long breakTime;
    private int maxDays;
}
