package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.Classroom;
import nl.tudelft.unischeduler.database.entities.Lecture;
import nl.tudelft.unischeduler.database.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    public void assignRoomToLecture (int lectureId, int classroomId){
        lectureRepository.assignRoomToLecture(lectureId, classroomId);
    }

    public void setLectureTime(int lectureId, Timestamp t){
        lectureRepository.setLectureTime(lectureId, t);
    }

    public Iterable<Lecture> getLecturesInRoomOnDay(Classroom c, Timestamp t){
        return lectureRepository.getLecturesInRoomOnDay(c, t);
    }
}
