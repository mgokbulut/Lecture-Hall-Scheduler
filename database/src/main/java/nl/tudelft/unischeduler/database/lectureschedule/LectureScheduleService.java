package nl.tudelft.unischeduler.database.lectureschedule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import nl.tudelft.unischeduler.database.classroom.ClassroomRepository;
import nl.tudelft.unischeduler.database.lecture.Lecture;
import nl.tudelft.unischeduler.database.lecture.LectureRepository;
import nl.tudelft.unischeduler.database.schedule.Schedule;
import nl.tudelft.unischeduler.database.schedule.ScheduleRepository;
import nl.tudelft.unischeduler.database.user.UserRepository;
import nl.tudelft.unischeduler.database.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class LectureScheduleService {

    @Autowired
    private transient LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private transient ScheduleRepository scheduleRepository;

    @Autowired
    private transient LectureRepository lectureRepository;

    @Autowired
    private transient ClassroomRepository classroomRepository;

    /**
     * Constructor.
     *
     * @param lectureScheduleRepository input repository
     * @param scheduleRepository input repository
     * @param lectureRepository input repository
     * @param classroomRepository input repository
     */
    public LectureScheduleService(LectureScheduleRepository lectureScheduleRepository,
                                  ScheduleRepository scheduleRepository,
                                  LectureRepository lectureRepository,
                                  ClassroomRepository classroomRepository) {
        this.lectureScheduleRepository = lectureScheduleRepository;
        this.scheduleRepository = scheduleRepository;
        this.lectureRepository = lectureRepository;
        this.classroomRepository = classroomRepository;
    }

    /**
     *  Removes the Lecture from all the LectureSchedules
     *  (essentially removes lecture from all the schedules that included it).
     *
     * @param lectureId lecture ID
     * @return Response Entity with result of this operation
     */
    @Transactional
    public ResponseEntity<?> removeLectureFromSchedule(Long lectureId) {
        try {
            lectureScheduleRepository.deleteLectureSchedulesByLectureId(lectureId);
            return ResponseEntity.ok().build();
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Assigns the user to a specified lecture.
     *
     * @param netId user ID
     * @param lectureId lecture ID
     * @return Response Entity with result of this operation
     */
    public ResponseEntity<?> assignLectureToSchedule(String netId, Long lectureId) {
        Optional<Schedule> tempSchedule = scheduleRepository.findByUser(netId);
        if (tempSchedule.isEmpty()) {
            System.out.println("Schedule with such netId does not exist");
            return ResponseEntity.notFound().build();
        }
        Long scheduleId = tempSchedule.get().getId();
        Optional<LectureSchedule> temp = lectureScheduleRepository
                .findByLectureIdAndScheduleId(lectureId, scheduleId);
        if (temp.isPresent()) {
            System.out.println("The lecture already exists in this schedule");
            return ResponseEntity.notFound().build();
        } else {
            try {
                LectureSchedule lectureSchedule = new LectureSchedule(lectureId, scheduleId);
                lectureScheduleRepository.save(lectureSchedule);
                return ResponseEntity.ok(lectureSchedule);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Something went wrong in assignLectureToSchedule method");
                return ResponseEntity.badRequest().build();
            }
        }
    }

    /**
     * Removes all lectures between start and end from the schedule of a user.
     *
     * @param netId user
     * @param start start of the remove window
     * @param end end of the remove window
     * @return a list of lecture id that were deleted
     */
    @Transactional
    public ResponseEntity<?> cancelStudentAttendance(String netId,
                                                     Timestamp start, Timestamp end) {
        try {
            Optional<Schedule> schedule = scheduleRepository.findByUser(netId);
            if (schedule.isEmpty()) {
                System.out.println("Schedule with such netId does not exist");
                return ResponseEntity.notFound().build();
            }
            List<Long> lectureIds = lectureRepository
                    .findAllByStartTimeDateBetween(start, end)
                    .stream()
                    .map(Lecture::getId)
                    .collect(Collectors.toList());

            List<Long> lectureSchedulesToDelete = lectureScheduleRepository
                    .findAllByScheduleId(schedule.get().getId())
                    .stream()
                    .map(LectureSchedule::getLectureId)
                    .filter(lectureIds::contains)
                    .collect(Collectors.toList());

            for (Long id : lectureSchedulesToDelete) {
                lectureScheduleRepository
                        .deleteByLectureIdAndScheduleId(id, schedule.get().getId());
            }

            return ResponseEntity.ok(lectureSchedulesToDelete);
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Returns a List of Object arrays of size 2,
     * arr[0] = lectures in a student's schedule,
     * arr[1] = corresponding classroom object.
     *
     * @param student the student Id
     * @return List of all the lectures in user's schedule
     */
    public List<Object []> getStudentSchedule(String student) {
        try {
            Schedule schedule = scheduleRepository.findByUser(student).get();
            return  lectureScheduleRepository
                    .findAllByScheduleId(schedule.getId())
                    .stream()
                    .map(x -> lectureRepository
                            .findById(x.getLectureId())
                            .get())
                    .map(x -> new Object[]
                        {x, classroomRepository.findById(x.getClassroom()).get()})
                    .collect(Collectors.toList());

        } catch (Exception a) {
            System.err.println("No such object in DB");
            a.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a List of Object arrays of size 2,
     * arr[0] = lectures in a teacher's schedule,
     * arr[1] = corresponding classroom object.
     *
     * @param teacher the teacher Id
     * @return List of all the lectures in user's schedule
     */
    public List<Object []> getTeacherSchedule(String teacher) {
        try {
            return lectureRepository
                    .findAllByTeacher(teacher)
                    .stream()
                    .map(x -> new Object[]
                        {x, classroomRepository.findById(x.getClassroom()).get()})
                    .collect(Collectors.toList());
        } catch (Exception a) {
            System.err.println("No such object in DB");
            a.printStackTrace();
            return null;
        }
    }



    /**
     * Removes a user from a lecture.
     *
     * @param netId the user Id
     * @param lectureId lecture to be removed from
     * @return ResponseEntity with result of the operation
     */
    @Transactional
    public ResponseEntity<?> removeStudentFromLecture(String netId, Long lectureId) {
        try {
            Optional<Schedule> schedule = scheduleRepository.findByUser(netId);
            if (schedule.isEmpty()) {
                System.out.println("Schedule with such netId does not exist");
                return ResponseEntity.noContent().build();
            }
            lectureScheduleRepository
                    .deleteByLectureIdAndScheduleId(lectureId, schedule.get().getId());
            return ResponseEntity.ok().build();
        } catch (Exception a) {
            a.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Returns a List of Object arrays of size 2,
     * arr[0] = lectures in a course,
     * arr[1] = corresponding classroom object.
     *
     * @param courseId the courseId
     * @return List of all the lectures in a course
     */
    public List<Object []> getAllLecturesInCourse(Long courseId) {
        try {
            return lectureRepository
                    .findAllByCourse(courseId)
                    .stream()
                    .map(x -> new Object[]
                        {x, classroomRepository.findById(x.getClassroom()).get()})
                    .collect(Collectors.toList());
        } catch (Exception a) {
            System.err.println("No such object in DB");
            a.printStackTrace();
            return null;
        }
    }
}
