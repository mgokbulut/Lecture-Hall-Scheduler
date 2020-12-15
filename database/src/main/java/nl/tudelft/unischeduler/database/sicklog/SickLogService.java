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

    public List<SickLog> getAllSickLogs() {
        return sickLogRepository.findAll();
    }

    public ResponseEntity<?> setUserSick(String netId, Timestamp reportSick) {
        try{
            Optional<SickLog> optionalSickLog = sickLogRepository
                    .findAllByUserAndReportSickAndFinished(
                            netId, new Date(reportSick.getTime()),false);
            if(optionalSickLog.isPresent()){
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
