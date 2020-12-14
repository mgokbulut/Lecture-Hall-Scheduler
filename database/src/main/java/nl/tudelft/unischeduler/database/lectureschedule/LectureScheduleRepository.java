package nl.tudelft.unischeduler.database.lectureschedule;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface LectureScheduleRepository
        extends JpaRepository<LectureSchedule, LectureScheduleId> {

    Optional<LectureSchedule> findByLectureIdAndScheduleId(
            Long lectureId, Long scheduleId);

    void deleteLectureSchedulesByLectureId(Long lectureId);

    List<LectureSchedule> findAllByScheduleId(Long scheduleId);

    void deleteByLectureIdAndScheduleId(Long lectureId, Long scheduleId);
}
