package nl.tudelft.unischeduler.database.course;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAll();

    Optional<Course> findById(Long courseId);

    Optional<Course> findByAndNameAndYear(String name, Integer year);
}
