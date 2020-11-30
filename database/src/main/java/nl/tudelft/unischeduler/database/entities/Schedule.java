package nl.tudelft.unischeduler.database.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "schedule", schema = "schedulingDB")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //something wrong, infinite recursive call
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user", referencedColumnName = "net_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "lecture_schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id"))
    private Set<Lecture> lectures;

    /**
     * This method initialises the schedule object.
     */
    public Schedule() {

    }

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
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return id.equals(schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, lectures);
    }
}
