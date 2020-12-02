package nl.tudelft.unischeduler.database.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient ScheduleRepository scheduleRepository;

    public Schedule getScheduleOfUser(String user) {
        Optional<Schedule> temp = scheduleRepository.findByUser(user);
        if(temp.isEmpty()){
            System.out.println("No schedule for such user exists");
            return null;
        }
        return temp.get();
    }
}
