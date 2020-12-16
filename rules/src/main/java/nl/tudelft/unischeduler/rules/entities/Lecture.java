package nl.tudelft.unischeduler.rules.entities;

import java.sql.Time;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Lecture implements Comparable {

    private int id;
    private int attendance;
    private Timestamp startTime;
    private Time duration;
    private Room room;

    @Override
    public int compareTo(Object o) {
        return this.startTime.compareTo(((Lecture) o).startTime);
    }
}
