package nl.tudelft.unischeduler.database.lecture;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import nl.tudelft.unischeduler.database.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LectureService {

    @Autowired
    private transient LectureRepository lectureRepository;

    @Autowired
    private transient CourseRepository courseRepository;

    /**
     * Returns a list containing an Array of size 2 with
     * every lecture and the course a lecture is in.
     *
     * @return List of Objects (os size 2) with Lecture and Course objects
     */
    public List<Object []>  getLecturesWithCourses() {
        var lectures = lectureRepository.findAll();
        List<Object []> lecturesWithCourses = new ArrayList<>();
        for (Lecture lecture : lectures) {
            Object [] obj = {lecture,  courseRepository.findById(lecture.getCourse())};
            lecturesWithCourses.add(obj);
        }
        return lecturesWithCourses;
    }

    /**
     * Sets the classroom of a lecture to EMPTY.
     *
     * @param lectureId lecture ID
     * @return String with result of the operation
     */
    public String setClassroomToEmpty(Long lectureId) {
        Optional<Lecture> temp = lectureRepository.findById(lectureId);
        if (temp.isEmpty()) {
            return "{message:\"LectureID not present in the DB\"}";
        } else {
            try {
                Lecture lecture = temp.get();
                lecture.setClassroom(-1L);
                lecture.setMovedOnline(true);
                lectureRepository.save(lecture);
            } catch (Exception e) {
                System.out.println("Something went wrong in setClassroomToEmpty method");
                e.printStackTrace();
                return null;
            }
            return "{message:\"Success!\"}";
        }
    }

    /**
     * Returns a list of all lectures belonging to a specific course,
     * such that it starts after ts and ends before ts+t.
     *
     * @param courseId course ID
     * @param ts Timestamp
     * @param t Time
     * @return List of Lectures fulfilling above specified requirements
     */
    public List<Lecture> getLecturesInCourse(Long courseId, Timestamp ts, Time t) {
        List<Lecture> lectures =  lectureRepository.findAllByCourse(courseId);
        return lectures
                .stream()
                .filter(x -> x.getStartTimeDate().after(ts)
                        && x.getStartTimeDate().before(new Timestamp(ts.getTime() + t.getTime())))
                .collect(Collectors.toList());
    }

    /**
     * Assigns Time t to a specific lecture as its startTime.
     *
     * @param lectureId lecture ID
     * @param t Time
     * @return String with result of the operation
     */
    public String setTime(Long lectureId, Timestamp t) {
        Optional<Lecture> temp = lectureRepository.findById(lectureId);
        if (temp.isEmpty()) {
            return "{message:\"LectureID not present in the DB\"}";
        } else {
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

    /**
     * Assigns a specified classroom to a specified lecture.
     *
     * @param lectureId lecture ID
     * @param classroomId classroom ID
     * @return String with result of the operation
     */
    public String setClassroom(Long lectureId, Long classroomId) {
        Optional<Lecture> temp = lectureRepository.findById(lectureId);
        if (temp.isEmpty()) {
            return "{message:\"LectureID not present in the DB\"}";
        } else {
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
