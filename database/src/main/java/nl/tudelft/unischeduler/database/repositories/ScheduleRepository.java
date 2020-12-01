package nl.tudelft.unischeduler.database.repositories;

import nl.tudelft.unischeduler.database.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
