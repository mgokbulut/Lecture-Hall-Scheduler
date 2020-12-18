package nl.tudelft.unischeduler.database.user;

import java.util.Calendar;
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

    public UserService(UserRepository userRepository, SickLogRepository sickLogRepository) {
        this.userRepository = userRepository;
        this.sickLogRepository = sickLogRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Returns an Object array of size 2, arr[0] = the student with the given netId
     * arr[1] = boolean whether the student has finished being sick,
     * the method also updates that boolean.
     *
     * @param netId student
     * @return Object array
     */
    public Object [] getUser(String netId) {
        try {
            List<SickLog> sickLogs = sickLogRepository.findAllByUser(netId);
            //Sort in descending order of reportSick
            sickLogs.sort((x, y) -> y.getReportSick().compareTo(x.getReportSick()));
            boolean finished;
            if (sickLogs.size() > 0) {
                //If the date of reported sick + 2 weeks is less than today.
                //Meaning has 2 weeks passed since the date of reportSick
                if (sickLogs.get(0).getReportSick()
                        .getTime() + 1209600000L < Calendar.getInstance().getTimeInMillis()) {
                    finished = true;
                    for (SickLog sickLog : sickLogs) {
                        sickLog.setFinished(true);
                    }
                } else {
                    finished = false;
                }
            } else {
                finished = true;
            }
            return new Object[]{userRepository.findByNetId(netId).get(), finished};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}