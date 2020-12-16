package nl.tudelft.unischeduler.database.user;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
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

    /**
     * Returns an Object array of size 2, arr[0] = the student with the given netId
     * arr[1] = latest reportSick date from their sickLog.
     *
     * @param netId student
     * @return Object array
     */
    public Object [] getUser(String netId) {
        try {
            List<SickLog> sickLogs = sickLogRepository.findAllByUser(netId);
            //Sort in descending order of reportSick
            sickLogs.sort((x, y) -> y.getReportSick().compareTo(x.getReportSick()));
            Optional<Date> lastOne;
            if (sickLogs.size() > 0) {
                lastOne = Optional.of(sickLogs.get(0).getReportSick());
            } else {
                lastOne = Optional.empty();
            }
            return new Object[]{userRepository.findByNetId(netId), lastOne};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
