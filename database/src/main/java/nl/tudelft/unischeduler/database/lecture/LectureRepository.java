package nl.tudelft.unischeduler.database.lecture;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findById(Long id);

    Optional<Lecture> findAllByClassroomAndCourseAndTeacherAndStartTimeDateAndDurationAndMovedOnline(
            Long classroomId, Long courseId, String teacher,
            Timestamp startTimeDate, Time duration, boolean movedOnline);

    List<Lecture> findAllByCourse(Long course);

    List<Lecture> findAll();

    List<Lecture> findAllByStartTimeDateBetween(Timestamp start, Timestamp end);

    List<Lecture> findAllByTeacherAndStartTimeDateBetween(
            String teacherId, Timestamp start, Timestamp end);

    List<Lecture> findAllByTeacherAndStartTimeDateGreaterThanEqual(String teachId, Timestamp start);

}
