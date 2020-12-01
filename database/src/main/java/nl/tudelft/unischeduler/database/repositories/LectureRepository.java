package nl.tudelft.unischeduler.database.repositories;

import nl.tudelft.unischeduler.database.entities.Lecture;
import nl.tudelft.unischeduler.database.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {
//    void assignRoomToLecture(Long lectureId, Long classroomId);
//    void setLectureTime(Long lectureId, Timestamp t);
    //List<Lecture> getLecturesInRoomOnDay(Long classroomId, Timestamp t);
    Iterable<Lecture> getLectureByClassroomAndStartTimeDate(Long classroomId, Timestamp t);
}
