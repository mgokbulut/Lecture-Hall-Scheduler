package nl.tudelft.unischeduler.database.repositories;

import java.util.List;
import java.util.Optional;

import nl.tudelft.unischeduler.database.entities.Classroom;
import nl.tudelft.unischeduler.database.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findAll();

    Optional<Classroom> findById(Long id);
}
