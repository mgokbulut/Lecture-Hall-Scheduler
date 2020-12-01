package nl.tudelft.unischeduler.database.repositories;

import java.sql.Timestamp;
import nl.tudelft.unischeduler.database.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    //    void assignRoomToLecture(Long lectureId, Long classroomId);
    //    void setLectureTime(Long lectureId, Timestamp t);
    //List<Lecture> getLecturesInRoomOnDay(Long classroomId, Timestamp t);
    Iterable<Lecture> getLectureByClassroom_IdAndStartTimeDate(Long classroomId, Timestamp t);
}
