package nl.tudelft.unischeduler.database.UserCourse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {
    List<UserCourse> findAllByCourseId(Long courseId);
}
