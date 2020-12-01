package nl.tudelft.unischeduler.database.repositories;

import java.util.List;
import nl.tudelft.unischeduler.database.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    List<Classroom> findAll();
}
