package nl.tudelft.unischeduler.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "app_user", schema = "loginDB")
public class User {
    @Id
    @Column(name = "net_id", nullable = false, unique = true)
    private String netId;
    @Column(name = "hashed_password", nullable = false)
    private String password;
    @Column(name = "type")
    private String type;

    public static String ROLE_STUDENT = "STUDENT";
    public static String ROLE_TEACHER = "TEACHER";
    public static String ROLE_FAC_MEMBER = "FACULTY_MEMBER";

    /***
     * <p>This method initialises the user object.</p>
     */
    public User() {

    }

    /***
     * <p>This method return the role of the user for authentication purposes.</p>
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
}