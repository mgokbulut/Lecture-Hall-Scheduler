package nl.tudelft.unischeduler.database.sicklog;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SickLogRepository extends JpaRepository<SickLog, Long> {
    List<SickLog> findAll();
}
