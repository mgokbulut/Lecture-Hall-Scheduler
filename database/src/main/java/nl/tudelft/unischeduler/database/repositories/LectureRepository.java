package nl.tudelft.unischeduler.database.repositories;

import java.sql.Timestamp;
import java.util.Optional;

import nl.tudelft.unischeduler.database.entities.Lecture;
import nl.tudelft.unischeduler.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findById(Long id);

    //Iterable<Lecture> getLectureByClassroom_IdAndStartTimeDate(Long classroomId, Timestamp t);
}
