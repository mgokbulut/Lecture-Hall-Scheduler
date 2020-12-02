package nl.tudelft.unischeduler.database;

import java.sql.Timestamp;
import nl.tudelft.unischeduler.database.entities.Lecture;
import nl.tudelft.unischeduler.database.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LectureService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient LectureRepository lectureRepository;

    public Lecture getLecture(Long id){
        return lectureRepository.findById(id).get();
    }

    public Iterable<Lecture> getLecturesInRoomOnDay(Long classroomId, Timestamp t) {
        return lectureRepository.getLectureByClassroom_IdAndStartTimeDate(classroomId, t);
    }
}