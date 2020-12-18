package nl.tudelft.unischeduler.scheduleedit.controller;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.scheduleedit.core.ScheduleEditModule;
import nl.tudelft.unischeduler.scheduleedit.exception.ConnectionException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher/")
@Data
@AllArgsConstructor
public class TeacherController {

    private ScheduleEditModule core;

    /**
     * Sets all the lectures until the until date to no longer being on campus.
     * <p>Use this method if you want to specify a specific date
     *  on until which to cancel the lectures</p>
     *
     * @param teacherNetId The netId of the teacher which is sick.
     *                     This should be the same id as in the token
     *                     or the token should belong to a faculty member.
     * @param until The LocalDate until the teacher is sick (inclusive).
     * @throws ConnectionException When the connection to the database service fails.
     */
    @PutMapping(value = "/{teacherNetId}/sick", params = {"until"})
    public void cancelLectures(@PathVariable String teacherNetId, @RequestParam LocalDateTime until)
            throws ConnectionException {
        core.reportTeacherSick(teacherNetId, until);
    }

    /**
     * Sets all the lectures for the coming 2 weeks to no longer being on campus.
     *
     * @param teacherNetId The netId of the teacher which is sick.
     *                     This should be the same id as in the token
     *                     or the token should belong to a faculty member.
     * @throws ConnectionException When the connection to the database service fails.
     */
    @PutMapping("/{teacherNetId}/sick")
    public void cancelLecturesStandard(@PathVariable String teacherNetId)
            throws ConnectionException {
        core.reportTeacherSick(teacherNetId);
    }
}
