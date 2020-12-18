package nl.tudelft.unischeduler.database.lecture;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import nl.tudelft.unischeduler.database.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
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
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> setClassroomToEmpty(Long lectureId) {
        Optional<Lecture> temp = lectureRepository.findById(lectureId);
        if (temp.isEmpty()) {
            System.out.println("LectureID not present in the DB");
            return ResponseEntity.notFound().build();
        } else {
            try {
                Lecture lecture = temp.get();
                lecture.setClassroom(-1L);
                lecture.setMovedOnline(true);
                lectureRepository.save(lecture);
            } catch (Exception e) {
                System.out.println("Something went wrong in setClassroomToEmpty method");
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().build();
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
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> setTime(Long lectureId, Timestamp t) {
        Optional<Lecture> temp = lectureRepository.findById(lectureId);
        if (temp.isEmpty()) {
            System.out.println("LectureID not present in the DB");
            return ResponseEntity.notFound().build();
        } else {
            try {
                Lecture lecture = temp.get();
                lecture.setStartTimeDate(t);
                lectureRepository.save(lecture);
            } catch (Exception e) {
                System.out.println("Something went wrong in getLecturesInRoomOnDay method");
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Assigns a specified classroom to a specified lecture.
     *
     * @param lectureId lecture ID
     * @param classroomId classroom ID
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> setClassroom(Long lectureId, Long classroomId) {
        Optional<Lecture> temp = lectureRepository.findById(lectureId);
        if (temp.isEmpty()) {
            System.out.println("LectureID not present in the DB");
            return ResponseEntity.notFound().build();
        } else {
            try {
                Lecture lecture = temp.get();
                lecture.setClassroom(classroomId);
                lectureRepository.save(lecture);
            } catch (Exception e) {
                System.out.println("Something went wrong in assignRoomToLecture method");
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Sets a lecture to online.
     *
     * @param teacherId the teacher
     * @param start startTime start window
     * @param end startTime end Window
     * @param updateOnline whether the classroom should be updated or not
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> setLectureToOnline(String teacherId,
                                                Timestamp start,
                                                Timestamp end,
                                                boolean updateOnline) {
        try {
            List<Lecture> lectures = lectureRepository
                    .findAllByTeacherAndStartTimeDateBetween(teacherId, start, end);
            for (Lecture lecture : lectures) {
                lecture.setMovedOnline(true);
                if (updateOnline) {
                    lecture.setClassroom(-2L);
                }
                lectureRepository.save(lecture);
            }
            return ResponseEntity.ok().build();
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Sets a lecture to online.
     *
     * @param lectureId the lecture
     * @param updateOnline whether the classroom should be updated or not
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> setLectureToOnline(Long lectureId, boolean updateOnline) {
        Optional<Lecture> optionalLecture = lectureRepository.findById(lectureId);
        if (optionalLecture.isEmpty()) {
            System.out.println("LectureID not present in the DB");
            return ResponseEntity.notFound().build();
        } else {
            try {
                var lecture = optionalLecture.get();
                lecture.setMovedOnline(true);
                if (updateOnline) {
                    lecture.setClassroom(-2L);
                }
                lectureRepository.save(lecture);
                return ResponseEntity.ok().build();
            } catch (Exception a) {
                a.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
    }

    /**
     * Sets lecture(found by teacher and startTime) to offline, with EMPTY classroom.
     *
     * @param teacherId the teacher
     * @param start the startTime
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> setLectureToOffline(String teacherId, Timestamp start) {
        try {
            List<Lecture> lectures = lectureRepository
                    .findAllByTeacherAndStartTimeDateGreaterThanEqual(teacherId, start);

            for (Lecture lecture : lectures) {
                if (lecture.getClassroom() > -2L) {
                    lecture.setMovedOnline(false);
                    lectureRepository.save(lecture);
                }
            }
            return ResponseEntity.ok().build();
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Sets lecture to offline, with EMPTY classroom.
     *
     * @param lectureId lecture to be set
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> setLectureToOffline(Long lectureId) {
        Optional<Lecture> optionalLecture = lectureRepository.findById(lectureId);
        if (optionalLecture.isEmpty()) {
            System.out.println("LectureID not present in the DB");
            return ResponseEntity.notFound().build();
        } else {
            try {
                var lecture = optionalLecture.get();
                if (lecture.getClassroom() > -2L) {
                    lecture.setMovedOnline(false);
                    lectureRepository.save(lecture);
                }
                return ResponseEntity.ok().build();
            } catch (Exception a) {
                a.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
    }

    /**
     * Creates a new lecture with given attributes.
     *
     * @param courseId course of this lecture
     * @param teacher a teacher of that lecture
     * @param startTime starting of the lecture
     * @param duration duration of it
     * @param movedOnline is this lecture online?
     * @return ResponseEntity with result of the operation
     */
    public ResponseEntity<?> createLecture(Long courseId, String teacher,
                                           Timestamp startTime,  Time duration,
                                           boolean movedOnline) {
        try {
            Optional<Lecture> lectureOptional = lectureRepository
                    .findAllByClassroomAndCourseAndTeacherAndStartTimeDateAndDurationAndMovedOnline(
                            -1L, courseId, teacher, startTime, duration, movedOnline);
            if (lectureOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Lecture lecture = new Lecture(-1L, courseId, teacher, startTime, duration, movedOnline);
            lectureRepository.save(lecture);
            return ResponseEntity.ok(lecture);
        } catch (Exception e) {
            System.out.println("Something went wrong in createLecture method");
            return ResponseEntity.badRequest().build();
        }
    }

}
