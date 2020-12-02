package nl.tudelft.unischeduler.database.LectureSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureScheduleRepository extends JpaRepository<LectureSchedule, LectureScheduleId> {

    Optional<LectureSchedule> findByLectureIdAndScheduleId(Long lectureId, Long scheduleId);
}
