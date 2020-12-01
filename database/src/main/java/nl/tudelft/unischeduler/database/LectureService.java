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

    //    public void assignRoomToLecture (Long lectureId, Long classroomId){
    //        lectureRepository.assignRoomToLecture(lectureId, classroomId);
    //    }
    //
    //    public void setLectureTime(Long lectureId, Timestamp t){
    //        lectureRepository.setLectureTime(lectureId, t);
    //    }

    public Iterable<Lecture> getLecturesInRoomOnDay(Long classroomId, Timestamp t) {
        return lectureRepository.getLectureByClassroom_IdAndStartTimeDate(classroomId, t);
    }
}
