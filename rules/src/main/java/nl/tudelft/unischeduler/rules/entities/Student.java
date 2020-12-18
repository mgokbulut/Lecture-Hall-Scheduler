package nl.tudelft.unischeduler.rules.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String netId;
    private boolean interested;
    private boolean recovered;
}
