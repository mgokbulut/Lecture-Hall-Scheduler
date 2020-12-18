package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NonNull String netId;
    @NonNull String type;
    @NonNull Date lastTimeOnCampus;
    private Schedule schedule;
    private Set<Lecture> lectures;
    private Set<Course> courses;

    /**
     * constructor for user in the case that courses, lectures
     * and schedule are not available.
     *
     * @param netId
     * @param type
     * @param lastTimeOnCampus
     */
    public User(String netId, String type, Date lastTimeOnCampus) {
        this.netId = netId;
        this.type = type;
        this.lastTimeOnCampus = lastTimeOnCampus;
    }
}
