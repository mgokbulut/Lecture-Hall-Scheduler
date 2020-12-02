package nl.tudelft.unischeduler.database.Course;

import java.util.List;
import java.util.Optional;

import nl.tudelft.unischeduler.database.Course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAll();
    Course getById(Long courseId);
}
