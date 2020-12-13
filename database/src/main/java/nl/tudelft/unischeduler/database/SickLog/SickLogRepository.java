package nl.tudelft.unischeduler.database.SickLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SickLogRepository extends JpaRepository<SickLog, Long> {
    List<SickLog> findAll();
}
