package nl.tudelft.unischeduler.database.lecture;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findById(Long id);

    List<Lecture> findAllByClassroom(Long classroomId);

    List<Lecture> findAllByCourse(Long course);

    List<Lecture> findAll();
}
