package nl.tudelft.unischeduler.database.SickLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SickLogService {

    @Autowired
    private transient SickLogRepository sickLogRepository;

    public List<SickLog> getAllSickLogs(){
        return sickLogRepository.findAll();
    }
}
