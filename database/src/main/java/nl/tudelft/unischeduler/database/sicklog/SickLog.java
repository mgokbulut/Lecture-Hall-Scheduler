package nl.tudelft.unischeduler.database.sicklog;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table(name = "sick_log", schema = "schedulingDB")
public class SickLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String user;

    @Column(name = "report_sick")
    private Date reportSick;

    @Column(name = "finished")
    private boolean finished;

    /**
     * This method initialises the SickLog object.
     */
    public SickLog() {

    }

    /**
     * This method initialises the SickLog object with specified parameters, Id is autogenerated.
     *
     * @param user user ID
     * @param reportSick the day user reported they were sick
     * @param finished is user no longer sick (for example user has tested negative)
     */
    public SickLog(String user, Date reportSick, boolean finished) {
        this.user = user;
        this.reportSick = reportSick;
        this.finished = finished;
    }
}
