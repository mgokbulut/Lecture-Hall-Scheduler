package nl.tudelft.unischeduler.database.Lecture;

import nl.tudelft.unischeduler.database.Lecture.Lecture;
import nl.tudelft.unischeduler.database.Lecture.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Service
public class LectureService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient LectureRepository lectureRepository;

//    public Lecture getLecture(Long id){
//        return lectureRepository.findById(id).get();
//    }

    public List<Lecture> getLecturesInRoomOnDay(Long classroomId) {
        return lectureRepository.findAllByClassroom(classroomId);
    }

    public String setTime(Long lectureId, Timestamp t){
        Optional<Lecture> temp = lectureRepository.findById(lectureId);
        if (temp.isEmpty()) {
            return "{message:\"LectureID not present in the DB\"}";
        }
        else {
            try {
                Lecture lecture = temp.get();
                lecture.setStartTimeDate(t);
                lectureRepository.save(lecture);
            } catch (Exception e) {
                System.out.println("Something went wrong in getLecturesInRoomOnDay method");
                return null;
            }
            return "{message:\"Success!\"}";
        }
    }

    public String setClassroom(Long lectureId, Long classroomId){
        Optional<Lecture> temp = lectureRepository.findById(lectureId);
        if (temp.isEmpty()) {
            return "{message:\"LectureID not present in the DB\"}";
        }
        else {
            try {
                Lecture lecture = temp.get();
                lecture.setClassroom(classroomId);
                lectureRepository.save(lecture);
            } catch (Exception e) {
                System.out.println("Something went wrong in assignRoomToLecture method");
                return null;
            }
            return "{message:\"Success!\"}";
        }
    }
}
