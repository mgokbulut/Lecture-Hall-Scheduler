package nl.tudelft.unischeduler.database.sicklog;

import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
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
     * This method initialises the SickLog object with specified parameters.
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

    public Date getReportSick() {
        return reportSick;
    }

    public void setReportSick(Date reportSick) {
        this.reportSick = reportSick;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SickLog)) {
            return false;
        }
        SickLog sickLog = (SickLog) o;
        return getId().equals(sickLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getReportSick(), isFinished());
    }
}
