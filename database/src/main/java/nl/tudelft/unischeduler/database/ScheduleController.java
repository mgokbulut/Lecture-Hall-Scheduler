package nl.tudelft.unischeduler.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(path = "/schedules")
    public @ResponseBody
    void assignStudentToLecture(int studentId, int lectureId) {
        scheduleService.assignStudentToLecture(studentId, lectureId);
    }
}
