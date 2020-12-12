package nl.tudelft.unischeduler.database.Classroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassroomController {

    @Autowired
    private transient ClassroomService classroomService;

    @GetMapping(path = "/classrooms/all")
    public @ResponseBody
    List<Classroom> getAllClassrooms() {
        return classroomService.getAllClassrooms();
    }

//    @GetMapping(path = "/classrooms")
//    public @ResponseBody
//    ResponseEntity<?> getAllClassrooms() {
//        return ResponseEntity.ok(classroomService.getAllClassrooms());
//    }

    @GetMapping(path = "/classrooms/{classroomId}")
    public @ResponseBody
    Classroom getClassroom(@PathVariable Long classroomId) {
        return classroomService.getClassroom(classroomId);
    }
}
