package nl.tudelft.unischeduler.database.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "schedule", schema = "schedulingDB")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user", referencedColumnName = "net_id")
    @JsonManagedReference//
    private User user;

//    @ManyToMany
//    @JoinTable(name = "lecture_schedule",
//            joinColumns = @JoinColumn(name = "schedule_id"),
//            inverseJoinColumns = @JoinColumn(name = "lecture_id"))
//    //@JsonManagedReference//
//    private Set<Lecture> lectures;

    /**
     * This method initialises the schedule object.
     */
    public Schedule() {

    }

    /**
     * TODO a.
     *
     * @param id a
     * @param user a
     * @param lectures a
     */
    public Schedule(Long id, User user, Set<Lecture> lectures) {
        this.id = id;
        this.user = user;
        //this.lectures = lectures;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        return id.equals(schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }
}
