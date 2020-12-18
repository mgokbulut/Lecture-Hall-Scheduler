package nl.tudelft.unischeduler.rules.entities;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Room {

    private int id;
    private int capacity;
    private String name;


}
