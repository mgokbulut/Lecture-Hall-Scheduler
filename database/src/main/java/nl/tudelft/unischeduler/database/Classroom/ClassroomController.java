package nl.tudelft.unischeduler.database.Classroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassroomController {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient ClassroomService classroomService;

    @GetMapping(path = "/classrooms")
    public @ResponseBody
    List<Classroom> getAllClassrooms() {
        return classroomService.getAllClassrooms();
    }

    @GetMapping(path = "/classroom/{classroomId}")
    public @ResponseBody
    Classroom getClassroom(@PathVariable Long classroomId) {
        return classroomService.getClassroom(classroomId);
    }
}
