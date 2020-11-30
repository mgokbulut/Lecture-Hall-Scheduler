package nl.tudelft.unischeduler.database.repositories;

import nl.tudelft.unischeduler.database.entities.Lecture;
import nl.tudelft.unischeduler.database.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    void assignRoomToLecture(int lectureId, int classroomId);
    void setLectureTime(int lectureId, Timestamp t);
    List<Lecture> getLecturesInRoomOnDay(Classroom c, Timestamp t);
}
