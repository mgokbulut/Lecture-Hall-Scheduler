package nl.tudelft.unischeduler.database.Schedule;

import nl.tudelft.unischeduler.database.Lecture.Lecture;
import nl.tudelft.unischeduler.database.Lecture.LectureRepository;
import nl.tudelft.unischeduler.database.Schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient ScheduleRepository scheduleRepository;

    public Schedule getScheduleOfUser(String user) {
        return scheduleRepository.getByUser(user);
    }
}
