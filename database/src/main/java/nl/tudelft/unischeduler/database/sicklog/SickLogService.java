package nl.tudelft.unischeduler.database.sicklog;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SickLogService {

    @Autowired
    private transient SickLogRepository sickLogRepository;

    public List<SickLog> getAllSickLogs() {
        return sickLogRepository.findAll();
    }
}
