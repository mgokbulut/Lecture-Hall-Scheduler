package nl.tudelft.unischeduler.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "app_user", schema = "schedulingDB")
public class User {
    @Id
    @Column(name = "net_id", nullable = false, unique = true)
    private String netId;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "interested", nullable = false)
    private boolean interested;
    @Column(name = "last_time_on_campus", nullable = false)
    private Date lastTimeOnCampus;

    /***
     * <p>This method initialises the user object.</p>
     */
    public User() {

    }

    /**
     * User constructor.
     * @param netId the net id of the user
     * @param type the type of user
     * @param interested interested in attending physical classes
     * @param lastTimeOnCampus last time a user was on campus
     */
    public User(String netId, String type, boolean interested, Date lastTimeOnCampus) {
        this.netId = netId;
        this.type = type;
        this.interested = interested;
        this.lastTimeOnCampus = lastTimeOnCampus;
    }

    public String getNetId() {
        return netId;
    }

    public String getType() {
        return type;
    }

    public boolean isInterested() {
        return interested;
    }

    public Date getLastTimeOnCampus() {
        return lastTimeOnCampus;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInterested(boolean interested) {
        this.interested = interested;
    }

    public void setLastTimeOnCampus(Date lastTimeOnCampus) {
        this.lastTimeOnCampus = lastTimeOnCampus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isInterested() == getNetId().equals(user.getNetId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNetId(), getType(), isInterested(), getLastTimeOnCampus());
    }
}