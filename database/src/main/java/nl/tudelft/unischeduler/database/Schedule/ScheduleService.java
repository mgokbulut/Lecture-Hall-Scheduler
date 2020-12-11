package nl.tudelft.unischeduler.database.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private transient ScheduleRepository scheduleRepository;

    public Schedule getScheduleOfUser(String user) {
        return scheduleRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("No such object in DB"));
    }
}
