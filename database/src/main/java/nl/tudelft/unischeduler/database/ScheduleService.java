package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

}
