package nl.tudelft.unischeduler.database.sicklog;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.unischeduler.database.course.CourseService;
import nl.tudelft.unischeduler.database.lectureschedule.LectureSchedule;
import nl.tudelft.unischeduler.database.lectureschedule.LectureScheduleService;
import nl.tudelft.unischeduler.database.triggers.LectureSubscriber;
import nl.tudelft.unischeduler.database.user.User;
import nl.tudelft.unischeduler.database.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public class SickLogService {

    @Autowired
    private transient SickLogRepository sickLogRepository;

    @Autowired
    private transient UserService userService;

    @Autowired
    private transient LectureSubscriber lectureSubscriber;

    @Autowired
    private transient LectureScheduleService lectureScheduleService;

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
                // notify classmates if teacher is sick
                try {
                    SickLog sickPerson = optionalSickLog.get();
                    User u = (User) userService.getUser(sickPerson.getUser())[0];
                    if (u.getType().equals(User.TEACHER)) {
                        List<Object[]> scheduleObjects =
                                lectureScheduleService.getTeacherSchedule(u.getNetId());
                        for (int i = 0; i < scheduleObjects.size(); i++) {
                            LectureSchedule lecture = (LectureSchedule) scheduleObjects.get(i)[0];
                            String[] actions = {LectureSubscriber.MOVED_ONLINE};
                            lectureSubscriber.update(lecture.getLectureId(),
                                    actions, LectureSubscriber.TEACHER);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
