package nl.tudelft.unischeduler.database.lecture;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LectureController {
    @Autowired
    private transient LectureService lectureService;
    //    @Autowired
    //    private transient ScheduleService scheduleService;
    //
    //    @Autowired
    //    private transient LectureRepository lectureRepository;
    //
    //    @GetMapping(path = "/lectures/all")
    //    public @ResponseBody
    //    List<Lecture> getLectures(){
    //        return lectureRepository.findAll();
    //    }

    @GetMapping(path = "/lectures/courses")
    public @ResponseBody
    List<Object []>  getLecturesWithCourses() {
        return lectureService.getLecturesWithCourses();
    }
    //doesn't lecture contain course Id anyway??

    @GetMapping(path = "/lectures/{courseId}/{ts}/{t}")
    public @ResponseBody
    List<Lecture> getLecturesInCourse(@PathVariable Long courseId,
                                      @PathVariable Timestamp ts, @PathVariable Time t) {
        return lectureService.getLecturesInCourse(courseId, ts, t);
    }

    @PutMapping(path = "/lectures/setClassroomToEmpty/{lectureId}")
    public @ResponseBody
    ResponseEntity<?> setClassroomToEmpty(@PathVariable Long lectureId) {
        return lectureService.setClassroomToEmpty(lectureId);
    }

    @PutMapping(path = "/lectures/setTime/{lectureId}/{t}")
    public @ResponseBody
    ResponseEntity<?> setLectureTime(@PathVariable Long lectureId, @PathVariable Timestamp t) {
        return lectureService.setTime(lectureId, t);
    }

    @PutMapping(path = "/lectures/setClassroom/{lectureId}/{classroomId}")
    public @ResponseBody
    ResponseEntity<?> assignRoomToLecture(@PathVariable Long lectureId,
                                          @PathVariable Long classroomId) {
        return lectureService.setClassroom(lectureId, classroomId);
    }

    @PutMapping(path = "/lectures/setToOnline/{teacherId}/{start}/{end}/{updateOnline}")
    public @ResponseBody
    ResponseEntity<?> setLectureToOnline(@PathVariable String teacherId,
                                         @PathVariable Timestamp start,
                                         @PathVariable Timestamp end,
                                         @PathVariable boolean updateOnline) {
        return lectureService.setLectureToOnline(teacherId, start, end, updateOnline);
    }

    @PutMapping(path = "/lectures/setToOnline/{lectureId}/{updateOnline}")
    public @ResponseBody
    ResponseEntity<?> setLectureToOnline(@PathVariable Long lectureId,
                                         @PathVariable boolean updateOnline) {
        return lectureService.setLectureToOnline(lectureId, updateOnline);
    }

    @PutMapping(path = "/lectures/setToOffline/{teacherId}/{start}")
    public @ResponseBody
    ResponseEntity<?> setLectureToOffline(@PathVariable String teacherId,
                                          @PathVariable Timestamp start) {
        return lectureService.setLectureToOffline(teacherId, start);
    }

    @PutMapping(path = "/lectures/setToOffline/{lectureId}")
    public @ResponseBody
    ResponseEntity<?> setLectureToOffline(@PathVariable Long lectureId) {
        return lectureService.setLectureToOffline(lectureId);
    }

    @PutMapping(path = "/lectures/create/{courseId}/{teacher}/{startTime}/{duration}/{movedOnline}")
    public @ResponseBody
    ResponseEntity<?> createLecture(@PathVariable Long courseId, @PathVariable String teacher,
                                    @PathVariable Timestamp startTime, @PathVariable Time duration,
                                    @PathVariable boolean movedOnline) {
        return lectureService.createLecture(courseId, teacher, startTime, duration, movedOnline);
    }



    //    @PutMapping(path = "/lectures/assignStudent/{netId}/{lectureId}")
    //    public @ResponseBody
    //    void assignStudentToLecture(@PathVariable String netId, @PathVariable Long lectureId){
    //        Schedule schedule = scheduleService.getScheduleOfUser(netId);
    //
    //
    //    }

    //    @GetMapping(path = "/lectures/{classroomId}/{date}")
    //    public @ResponseBody
    //    List<Lecture> getLecturesInRoomOnDay(@PathVariable Long classroomId,
    //    @PathVariable Date date) {
    //        SimpleDateFormat format = new SimpleDateFormat("yyyMMdd");
    //        //date comparison
    //        List<Lecture> lectures = lectureService
    //        .getLecturesInRoomOnDay(classroomId);
    //        return lectures
    //        .stream()
    //        .filter(x -> new Date(x.getStartTimeDate()
    //        .getTime())
    //        .equals(date))
    //        .collect(Collectors.toList());
    //    }
}
