package nl.tudelft.unischeduler.database.Lecture;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import nl.tudelft.unischeduler.database.Lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findById(Long id);
    List<Lecture> findAllByClassroom(Long classroomId);
    List<Lecture> findAllByCourse(Long Course);
}
