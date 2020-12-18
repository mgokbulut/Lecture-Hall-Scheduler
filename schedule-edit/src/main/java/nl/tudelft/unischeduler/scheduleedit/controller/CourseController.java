package nl.tudelft.unischeduler.scheduleedit.controller;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.scheduleedit.core.ScheduleEditModule;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/course/")
public class CourseController {

    private ScheduleEditModule core;

    @PostMapping
    public long createCourse(@RequestParam String courseName, @RequestParam int year)
            throws IOException {
        return core.createCourse(courseName, year);
    }

    @PostMapping("lecture")
    public long createLecture(@RequestParam long courseId,
                              @RequestParam String teacherNetId,
                              @RequestParam int year, @RequestParam int week,
                              @RequestParam Duration duration) throws IOException {
        return core.createLecture(courseId, teacherNetId, year, week, duration);
    }

    @PutMapping
    public void addStudentToLecture(@RequestBody List<String> studentNetId,
                                    @RequestParam long courseId) throws IOException {
        core.addStudentGroupLecture(studentNetId, courseId);
    }
}
