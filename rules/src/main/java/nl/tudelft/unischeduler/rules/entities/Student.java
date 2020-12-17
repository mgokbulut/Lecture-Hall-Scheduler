package nl.tudelft.unischeduler.rules.entities;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private String netId;
    private boolean interested;
    private boolean recovered;
}
