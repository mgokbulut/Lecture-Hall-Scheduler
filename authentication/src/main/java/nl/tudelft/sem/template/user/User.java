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
    private String netId;
    @Column(name = "hashed_password", nullable = false)
    private String password;
    @Column(name = "type")
    private String type;

    /***
     * <p>This method initialises the user object.</p>
     *
     * @param netId the net id of the user
     * @param password the password of the user
     * @param type the type of the user
     */
    public User(String netId, String password, String type) {
        this.netId = netId;
        this.password = password;
        this.type = type;
    }

    /***
     * <p>This method initialises the user object.</p>
     */
    public User() {

    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
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

    /***
     * <p>This method return the role of the user for authetication purposes.</p>
     *
     * @return returns a string role.
     */
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(netId, user.netId)
            && Objects.equals(password, user.password)
            && Objects.equals(type, user.type);

    }

    @Override
    public int hashCode() {
        return Objects.hash(netId, password, type);
    }
}