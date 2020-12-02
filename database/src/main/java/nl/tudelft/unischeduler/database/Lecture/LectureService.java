package nl.tudelft.unischeduler.database.Lecture;

import nl.tudelft.unischeduler.database.Lecture.Lecture;
import nl.tudelft.unischeduler.database.Lecture.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LectureService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient LectureRepository lectureRepository;

    public List<Lecture> getLecturesInCourse(Long courseId, Timestamp ts, Time t) {
        List<Lecture> lectures =  lectureRepository.findAllByCourse(courseId);
        Timestamp tsPlusDuration = new Timestamp(ts.getTime() + t.getTime());
        return lectures
                .stream()
                .filter(x->x.getStartTimeDate().after(ts) && x.getStartTimeDate().before(tsPlusDuration))
                .collect(Collectors.toList());
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
