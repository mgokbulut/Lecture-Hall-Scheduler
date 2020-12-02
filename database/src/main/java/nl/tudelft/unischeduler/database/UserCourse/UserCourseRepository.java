package nl.tudelft.unischeduler.database.UserCourse;

import nl.tudelft.unischeduler.database.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {
    List<UserCourse> findAllByCourseId(Long courseId);
}
