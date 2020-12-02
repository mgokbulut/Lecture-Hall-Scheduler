package nl.tudelft.unischeduler.database.Classroom;

import java.util.List;
import java.util.Optional;

import nl.tudelft.unischeduler.database.Classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findAll();
}
