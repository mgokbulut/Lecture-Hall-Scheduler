package nl.tudelft.unischeduler.database.Lecture;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import nl.tudelft.unischeduler.database.Classroom.ClassroomService;
import nl.tudelft.unischeduler.database.Schedule.Schedule;
import nl.tudelft.unischeduler.database.Schedule.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LectureController {
    //not sure if should be transient but checkstyle complaints without it...
    @Autowired
    private transient LectureService lectureService;
    @Autowired
    private transient ScheduleService scheduleService;

    @GetMapping(path = "/lectures/{classroomId}/{date}")
    public @ResponseBody
    List<Lecture> getLecturesInRoomOnDay(@PathVariable Long classroomId, @PathVariable Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyMMdd");
        //date comparison
        List<Lecture> lectures = lectureService.getLecturesInRoomOnDay(classroomId);
        return lectures.stream().filter(x-> new Date(x.getStartTimeDate().getTime()).equals(date)).collect(Collectors.toList());
    }

    @PutMapping(path = "/lectures/setTime/{lectureId}/{t}")
    public @ResponseBody
    String setLectureTime(@PathVariable Long lectureId, @PathVariable Timestamp t){
        return lectureService.setTime(lectureId, t);
    }

    @PutMapping(path = "/lectures/setClassroom/{lectureId}/{classroomId}")
    public @ResponseBody
    String assignRoomToLecture(@PathVariable Long lectureId, @PathVariable Long classroomId){
        return lectureService.setClassroom(lectureId, classroomId);
    }

//    @PutMapping(path = "/lectures/assignStudent/{netId}/{lectureId}")
//    public @ResponseBody
//    void assignStudentToLecture(@PathVariable String netId, @PathVariable Long lectureId){
//        Schedule schedule = scheduleService.getScheduleOfUser(netId);
//
//
//    }
}
