package nl.tudelft.sem.template.user;

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
    @Column(name = "type")
    private String type;

    public User(String netID, String password, String type) {
        this.netID = netID;
        this.password = password;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String authenticationRole() {
        if (this.type.equals("STUDENT")) {
            return "ROLE_STUDENT";
        } else if (this.type.equals("TEACHER")) {
            return "ROLE_TEACHER";
        } else if (this.type.equals("FACULTY_MEMBER")) {
            return "ROLE_ADMIN";
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(netID, user.netID) &&
                Objects.equals(password, user.password) &&
                Objects.equals(type, user.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netID, password, type);
    }
}