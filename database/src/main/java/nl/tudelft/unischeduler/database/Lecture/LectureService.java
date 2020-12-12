package nl.tudelft.unischeduler.database.Lecture;

import nl.tudelft.unischeduler.database.Course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LectureService {

    @Autowired
    private transient LectureRepository lectureRepository;

    @Autowired
    private transient CourseRepository courseRepository;

    public List<Object []>  getLecturesWithCourses(){
        var lectures = lectureRepository.findAll();
        List<Object []> lecturesWithCourses = new ArrayList<>();
        for(Lecture lecture : lectures){
            Object [] obj = {lecture,  courseRepository.findById(lecture.getCourse())};
            lecturesWithCourses.add(obj);
        }
        return lecturesWithCourses;
    }

    public String setClassroomToEmpty(Long lectureId){
        Optional<Lecture> temp = lectureRepository.findById(lectureId);
        if (temp.isEmpty()) {
            return "{message:\"LectureID not present in the DB\"}";
        }
        else {
            try {
                Lecture lecture = temp.get();
                lecture.setClassroom(-1L);
                lecture.setMovedOnline(true);
                lectureRepository.save(lecture);
            } catch (Exception e) {
                System.out.println("Something went wrong in setClassroomToEmpty method");
                return null;
            }
            return "{message:\"Success!\"}";
        }
    }

    public List<Lecture> getLecturesInCourse(Long courseId, Timestamp ts, Time t) {
        List<Lecture> lectures =  lectureRepository.findAllByCourse(courseId);
        return lectures
                .stream()
                .filter(x->x.getStartTimeDate().after(ts)
                        && x.getStartTimeDate().before(new Timestamp(ts.getTime() + t.getTime())))
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
