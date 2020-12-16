package nl.tudelft.unischeduler.scheduleedit.controller;

import java.time.LocalDate;
import nl.tudelft.unischeduler.scheduleedit.core.ScheduleEditModule;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student/")
public class StudentController {
    private ScheduleEditModule core;

    public ScheduleEditModule getCore() {
        return core;
    }

    public void setCore(ScheduleEditModule core) {
        this.core = core;
    }

    public StudentController(@Autowired ScheduleEditModule core) {
        this.core = core;
    }

    /**
     * Sets all the lectures until the until date to no longer be attended by the student.
     * <p>Use this method if you want to specify a specific date
     *  on until which to cancel attendances for all courses</p>
     *
     * @param studentNetId The netId of the student which is sick.
     *                     This should be the same id as in the token
     *                     or the token should belong to a faculty member.
     * @param until The LocalDate until the student is sick (inclusive).
     * @throws ConnectionException When the connection to the database service fails.
     */
    @PutMapping(value = "/{studentNetId}/sick", params = {"until"})
    public void cancelAttendance(@PathVariable String studentNetId, @RequestParam LocalDate until)
            throws ConnectionException {
        core.reportStudentSick(studentNetId, until);
    }


    @PutMapping("/{studentNetId}/sick")
    public void cancelLecturesStandard(@PathVariable String studentNetId)
            throws ConnectionException {
        core.reportStudentSick(studentNetId);
    }
}
