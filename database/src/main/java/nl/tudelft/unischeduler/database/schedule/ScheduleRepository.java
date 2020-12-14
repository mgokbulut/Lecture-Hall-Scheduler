package nl.tudelft.unischeduler.database.schedule;

import java.util.Optional;

import nl.tudelft.unischeduler.database.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByUser(String user);
    Optional<Schedule> findById(Long id);
}
