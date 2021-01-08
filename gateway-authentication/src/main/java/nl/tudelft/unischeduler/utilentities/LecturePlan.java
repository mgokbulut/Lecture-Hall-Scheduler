package nl.tudelft.unischeduler.utilentities;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LecturePlan {
    long courseId;
    int week;
    String netId;
    Duration duration;
}
