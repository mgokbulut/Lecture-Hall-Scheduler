package nl.tudelft.unischeduler.database.repositories;

import nl.tudelft.unischeduler.database.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    List<Classroom> findAll();
}
