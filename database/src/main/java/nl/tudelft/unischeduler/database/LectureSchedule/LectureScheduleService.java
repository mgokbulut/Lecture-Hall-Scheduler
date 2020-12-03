package nl.tudelft.unischeduler.database.LectureSchedule;

import nl.tudelft.unischeduler.database.Schedule.Schedule;
import nl.tudelft.unischeduler.database.Schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LectureScheduleService {

    @Autowired
    private transient LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private transient ScheduleRepository scheduleRepository;

    public String removeLectureFromSchedule(Long lectureId){
        try{
            lectureScheduleRepository.deleteLectureSchedulesByLectureId(lectureId);
            return "Lecture successfully deleted from all the schedules";
        }
        catch (Exception a){
            a.printStackTrace();
            return "Issue with deletion of a lecture from schedules";
        }
    }

    public String assignLectureToSchedule(Long lectureId, String netId){
        Optional<Schedule> sTemp = scheduleRepository.findByUser(netId);
        if (sTemp.isEmpty()) {
            return "{message:\"Schedule with such netId does not exist\"}";
        }
        Long scheduleId = sTemp.get().getId();
        Optional<LectureSchedule> temp = lectureScheduleRepository.findByLectureIdAndScheduleId(lectureId, scheduleId);
        if (temp.isPresent()) {
            return "{message:\"The lecture already exists in this schedule\"}";
        }
        else {
            try {
                LectureSchedule lectureSchedule = new LectureSchedule(lectureId, scheduleId);
                lectureScheduleRepository.save(lectureSchedule);
            } catch (Exception e) {
                System.out.println("Something went wrong in assignLectureToSchedule method");
                return null;
            }
            return "{message:\"Success!\"}";
        }
    }
}
