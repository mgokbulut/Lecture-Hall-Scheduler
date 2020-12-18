package nl.tudelft.unischeduler.rules.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    private int id;
    private int capacity;
    private String name;


}
