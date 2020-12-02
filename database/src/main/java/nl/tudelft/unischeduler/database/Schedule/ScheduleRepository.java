package nl.tudelft.unischeduler.database.Schedule;

import nl.tudelft.unischeduler.database.Schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule getByUser(String user);
}
