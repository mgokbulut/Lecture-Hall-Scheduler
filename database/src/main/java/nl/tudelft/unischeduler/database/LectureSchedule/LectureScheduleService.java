package nl.tudelft.unischeduler.database.LectureSchedule;

import nl.tudelft.unischeduler.database.Schedule.Schedule;
import nl.tudelft.unischeduler.database.Schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LectureScheduleService {

    @Autowired
    private transient LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private transient ScheduleRepository scheduleRepository;

    public ResponseEntity<?> removeLectureFromSchedule(Long lectureId){
        try{
            lectureScheduleRepository.deleteLectureSchedulesByLectureId(lectureId);
            return ResponseEntity.noContent().build();
            //return "Lecture successfully deleted from all the schedules";
        }
        catch (Exception a){
            a.printStackTrace();
            return ResponseEntity.notFound().build();
            //return "Issue with deletion of a lecture from schedules";
        }
    }

    public ResponseEntity<?> assignLectureToSchedule(String netId, Long lectureId){
        Optional<Schedule> sTemp = scheduleRepository.findByUser(netId);
        if (sTemp.isEmpty()) {
            System.out.println("Schedule with such netId does not exist");
            return ResponseEntity.notFound().build();
        }
        Long scheduleId = sTemp.get().getId();
        Optional<LectureSchedule> temp = lectureScheduleRepository.findByLectureIdAndScheduleId(lectureId, scheduleId);
        if (temp.isPresent()) {
            System.out.println("The lecture already exists in this schedule");
            return ResponseEntity.notFound().build();
        }
        else {
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
