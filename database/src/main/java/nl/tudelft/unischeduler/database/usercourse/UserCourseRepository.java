package nl.tudelft.unischeduler.database.usercourse;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {
    List<UserCourse> findAllByCourseId(Long courseId);
}
