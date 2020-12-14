package nl.tudelft.unischeduler.database.lectureschedule;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nl.tudelft.unischeduler.database.lecture.Lecture;
import nl.tudelft.unischeduler.database.lecture.LectureRepository;
import nl.tudelft.unischeduler.database.schedule.Schedule;
import nl.tudelft.unischeduler.database.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;


@Service
public class LectureScheduleService {

    @Autowired
    private transient LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private transient ScheduleRepository scheduleRepository;

    @Autowired
    private transient LectureRepository lectureRepository;

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
                e.printStackTrace();
                System.out.println("Something went wrong in assignLectureToSchedule method");
                return ResponseEntity.notFound().build();
            }
        }
    }

    /**
     * Removes all lectures between start and end from the schedule of a student.
     *
     * @param netId
     * @param start
     * @param end
     * @return a list of lecture id that were deleted
     */
    @Transactional
    public ResponseEntity<?> cancelStudentAttendance(String netId,
                                                     Timestamp start, Timestamp end) {
        try {
            Optional<Schedule> schedule = scheduleRepository.findByUser(netId);
            if(schedule.isEmpty()){
                System.out.println("Schedule with such netId does not exist");
                return ResponseEntity.noContent().build();
            }
            List<Long> lectureIds = lectureRepository
                    .findAllByStartTimeDateBetween(start, end)
                    .stream()
                    .map(Lecture::getId)
                    .collect(Collectors.toList());

            List<Long> lectureSchedulesToDelete = lectureScheduleRepository
                    .findAllByScheduleId(schedule.get().getId())
                    .stream()
                    .map(LectureSchedule::getLectureId)
                    .filter(lectureIds::contains)
                    .collect(Collectors.toList());

            System.out.println(lectureSchedulesToDelete.toString());

            for(Long id : lectureSchedulesToDelete){
                lectureScheduleRepository.deleteByLectureIdAndScheduleId(id,schedule.get().getId());
            }

            return ResponseEntity.ok(lectureSchedulesToDelete);
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    public List<Lecture> getStudentSchedule(String user) {
        Schedule schedule = scheduleRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("No such object in DB"));
        return  lectureScheduleRepository
                .findAllByScheduleId(schedule.getId())
                .stream()
                .map(x -> lectureRepository
                        .findById(x.getLectureId())
                        .get())
                .collect(Collectors.toList());
    }
}
