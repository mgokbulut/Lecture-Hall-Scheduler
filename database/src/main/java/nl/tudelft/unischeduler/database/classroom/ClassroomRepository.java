package nl.tudelft.unischeduler.database.classroom;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findAll();

    Optional<Classroom> findById(Long classroomId);
}
