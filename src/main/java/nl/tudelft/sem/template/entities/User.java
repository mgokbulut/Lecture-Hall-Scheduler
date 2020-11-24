package nl.tudelft.sem.template.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_user", schema = "loginDB")
public class User {
    @Id
    @Column(name = "net_id", nullable = false, unique = true)
    private String netID;
    @Column(name = "hashed_password", nullable = false)
    private String password;

    public User(String netID, String password) {
        this.netID = netID;
        this.password = password;
    }
    public User() {

    }

    public String getNetID() {
        return netID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(netID, user.netID) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netID, password);
    }

}