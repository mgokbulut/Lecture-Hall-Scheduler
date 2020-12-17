package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    @NonNull String netId;
    @NonNull String type;
    @NonNull Date lastTimeOnCampus;
    private Schedule schedule;
    private Set<Lecture> lectures;
    private Set<Course> courses;

    public User(String netId, String type, Date lastTimeOnCampus, Schedule schedule) {
        this.netId = netId;
        this.type = type;
        this.lastTimeOnCampus = lastTimeOnCampus;
        this.schedule = schedule;
    }
}
