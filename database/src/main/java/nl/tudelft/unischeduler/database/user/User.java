package nl.tudelft.unischeduler.database.user;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table(name = "app_user", schema = "schedulingDB")
public class User {
    public static String TEACHER = "TEACHER";
    public static String STUDENT = "STUDENT";

    @Id
    @Column(name = "net_id", nullable = false, unique = true)
    private String netId;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "interested", nullable = false)
    private boolean interested;
    @Column(name = "last_time_on_campus", nullable = false)
    private Date lastTimeOnCampus;

    /**
     * This method initialises the User object.
     */
    public User() {

    }
}