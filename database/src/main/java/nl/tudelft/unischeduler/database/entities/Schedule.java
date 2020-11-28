package nl.tudelft.unischeduler.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "schedule", schema = "schedulingDB")
public class Schedule {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "app_user")
    private String appUser;

    public Schedule(int id, String appUser) {
        this.id = id;
        this.appUser = appUser;
    }

    /***
     * <p>This method initialises the schedule object.</p>
     */
    public Schedule() {

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getAppUser() { return appUser; }

    public void setAppUser(String appUser) { this.appUser = appUser; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return id == schedule.id &&
                Objects.equals(appUser, schedule.appUser);
    }

    @Override
    public int hashCode() { return Objects.hash(id, appUser); }
}
