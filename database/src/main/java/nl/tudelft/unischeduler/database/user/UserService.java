package nl.tudelft.unischeduler.database.user;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.tudelft.unischeduler.database.sicklog.SickLog;
import nl.tudelft.unischeduler.database.sicklog.SickLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private transient UserRepository userRepository;

    @Autowired
    private transient SickLogRepository sickLogRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Object [] getUser(String netId){
        try{
            List<SickLog> sickLogs = sickLogRepository.findAllByUser(netId);
            sickLogs.sort((x,y) -> y.getReportSick().compareTo(x.getReportSick()));
            Date lastOne;
            if (sickLogs.size() > 0){
                lastOne = sickLogs.get(0).getReportSick();
            }
            else{
                lastOne = null;
            }
            return new Object[]{userRepository.findByNetId(netId), lastOne};
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
