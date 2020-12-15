package nl.tudelft.unischeduler.database.schedule;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "schedule", schema = "schedulingDB")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "app_user")
    private String user;

    /**
     * This method initialises the Schedule object.
     */
    public Schedule() {

    }

    /**
     * This method initialises the Schedule object with specified parameters.
     *
     * @param user user ID
     */
    public Schedule(String user) {
        this.user = user;
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
