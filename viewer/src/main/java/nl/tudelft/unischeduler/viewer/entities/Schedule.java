package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
public class Schedule {
    private Long id;
    private User user;
    private Set<Lecture> lectures;


    public Schedule(Long id, User user) {
        this.id = id;
        this.user = user;
    }
}
