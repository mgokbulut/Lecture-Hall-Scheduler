package nl.tudelft.unischeduler.database.repositories;

import java.util.List;
import nl.tudelft.unischeduler.database.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAll();
}
