package nl.tudelft.unischeduler.database.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleRepository;
import nl.tudelft.unischeduler.database.schedule.ScheduleRepository;
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

    @Autowired
    private transient LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private transient ScheduleRepository scheduleRepository;

    /**
     * Constructor.
     *
     * @param userRepository userRepository
     * @param sickLogRepository sickLogRepository
     * @param lectureScheduleRepository lectureScheduleRepository
     * @param scheduleRepository scheduleRepository
     */
    public UserService(UserRepository userRepository, SickLogRepository sickLogRepository,
                       LectureScheduleRepository lectureScheduleRepository,
                       ScheduleRepository scheduleRepository) {
        this.userRepository = userRepository;
        this.sickLogRepository = sickLogRepository;
        this.lectureScheduleRepository = lectureScheduleRepository;
        this.scheduleRepository = scheduleRepository;
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
                long a = sickLogs.get(0).getReportSick().getTime() + 1209600000L;
                long b = Calendar.getInstance().getTimeInMillis();
                if (a < b) {
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

    /**
     * Returns a list of students that are scheduled to attend the given lecture
     * with an Object array of size 2: arr[0] = the student with the given netId
     * arr[1] = boolean whether the student has finished being sick.
     *
     * @param lectureId lecture Id
     * @return a list of student scheduled for a lecture
     */
    public List<Object[]> getStudentsInLecture(Long lectureId) {
        try {
            var lectureSchedules = lectureScheduleRepository
                    .findAllByLectureId(lectureId)
                    .stream()
                    .map(x -> scheduleRepository
                            .findById(x.getScheduleId())
                            .get())
                    .map(x -> userRepository
                            .findByNetId(x.getUser())
                            .get()
                            .getNetId())
                    .collect(Collectors.toList());
            List<Object []> ans = new ArrayList<>();
            for (String netId : lectureSchedules) {
                ans.add(getUser(netId));
            }
            return ans;

        } catch (Exception a) {
            a.printStackTrace();
            return null;
        }
    }
}
