package nl.tudelft.unischeduler.database.Schedule;

import nl.tudelft.unischeduler.database.Lecture.Lecture;

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

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "app_user", referencedColumnName = "net_id")
//    //@JsonManagedReference//
    @Column(name = "app_user")
    private String user;

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
    public Schedule(Long id, String user, Set<Lecture> lectures) {
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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
