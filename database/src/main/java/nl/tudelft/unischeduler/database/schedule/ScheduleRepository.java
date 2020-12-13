package nl.tudelft.unischeduler.database.schedule;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByUser(String user);
}
