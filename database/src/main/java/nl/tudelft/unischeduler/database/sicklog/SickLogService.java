package nl.tudelft.unischeduler.database.sicklog;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public class SickLogService {

    @Autowired
    private transient SickLogRepository sickLogRepository;

    public SickLogService(SickLogRepository sickLogRepository) {
        this.sickLogRepository = sickLogRepository;
    }

    public List<SickLog> getAllSickLogs() {
        return sickLogRepository.findAll();
    }

    /**
     * Creates a new sickLog.
     *
     * @param netId the user
     * @param reportSick the date of the report
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> setUserSick(String netId, Timestamp reportSick) {
        try {
            Optional<SickLog> optionalSickLog = sickLogRepository
                    .findAllByUserAndReportSickAndFinished(
                            netId, new Date(reportSick.getTime()), false);
            if (optionalSickLog.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            SickLog sickLog = new SickLog(netId, new Date(reportSick.getTime()), false);
            sickLogRepository.save(sickLog);
            return ResponseEntity.ok(sickLog);
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
