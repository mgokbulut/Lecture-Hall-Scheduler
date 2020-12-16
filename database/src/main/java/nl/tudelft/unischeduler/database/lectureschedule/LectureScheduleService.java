package nl.tudelft.unischeduler.database.lectureschedule;

import java.util.Optional;
import nl.tudelft.unischeduler.database.schedule.Schedule;
import nl.tudelft.unischeduler.database.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class LectureScheduleService {

    @Autowired
    private transient LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private transient ScheduleRepository scheduleRepository;

    /**
     *  Removes the Lecture from all the LectureSchedules
     *  (essentially removes lecture from all the schedules that included it).
     *
     * @param lectureId lecture ID
     * @return Response Entity with result of this operation
     */
    public ResponseEntity<?> removeLectureFromSchedule(Long lectureId) {
        try {
            lectureScheduleRepository.deleteLectureSchedulesByLectureId(lectureId);
            return ResponseEntity.noContent().build();
            //return "Lecture successfully deleted from all the schedules";
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.notFound().build();
            //return "Issue with deletion of a lecture from schedules";
        }
    }

    /**
     * Assigns the user to a specified lecture.
     *
     * @param netId user ID
     * @param lectureId lecture ID
     * @return Response Entity with result of this operation
     */
    public ResponseEntity<?> assignLectureToSchedule(String netId, Long lectureId) {
        Optional<Schedule> tempSchedule = scheduleRepository.findByUser(netId);
        if (tempSchedule.isEmpty()) {
            System.out.println("Schedule with such netId does not exist");
            return ResponseEntity.notFound().build();
        }
        Long scheduleId = tempSchedule.get().getId();
        Optional<LectureSchedule> temp = lectureScheduleRepository
                .findByLectureIdAndScheduleId(lectureId, scheduleId);
        if (temp.isPresent()) {
            System.out.println("The lecture already exists in this schedule");
            return ResponseEntity.notFound().build();
        } else {
            try {
                LectureSchedule lectureSchedule = new LectureSchedule(lectureId, scheduleId);
                lectureScheduleRepository.save(lectureSchedule);
                return ResponseEntity.ok(lectureSchedule);
            } catch (Exception e) {
                System.out.println("Something went wrong in assignLectureToSchedule method");
                return ResponseEntity.notFound().build();
            }
        }
    }
}
