package nl.tudelft.unischeduler.database.sicklog;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SickLogRepository extends JpaRepository<SickLog, Long> {
    List<SickLog> findAll();

    List<SickLog> findAllByUser(String netId);

    Optional<SickLog> findAllByUserAndReportSickAndFinished(
            String user, Date reportSick, boolean finished);
}
