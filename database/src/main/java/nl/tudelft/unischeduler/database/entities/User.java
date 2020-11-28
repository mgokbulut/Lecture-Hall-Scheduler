package nl.tudelft.unischeduler.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "app_user", schema = "schedulingDB")
public class User {
    @Id
    @Column(name = "net_id", nullable = false, unique = true)
    private String netId;
    @Column(name = "type")
    private String type;

    /***
     * <p>This method initialises the user object.</p>
     *
     * @param netId the net id of the user
     * @param type the type of the user
     */
    public User(String netId, String type) {
        this.netId = netId;
        this.type = type;
    }

    /***
     * <p>This method initialises the user object.</p>
     */
    public User() {

    }

    public String getNetId() { return netId; }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(netId, user.netId)
            && Objects.equals(type, user.type);

    }

    @Override
    public int hashCode() {
        return Objects.hash(netId , type);
    }
}