package nl.tudelft.unischeduler.viewer.entities;

import java.util.Objects;
import java.util.Set;

public class Schedule {
    private Long id;
    private User user;
    private Set<Lecture> lectures;

    public Schedule(Long id, User user, Set<Lecture> lectures) {
        this.id = id;
        this.user = user;
        this.lectures = lectures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return id.equals(schedule.id) &&
                Objects.equals(user, schedule.user) &&
                Objects.equals(lectures, schedule.lectures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, lectures);
    }
}
