package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @GetMapping(path = "/classrooms")
    public @ResponseBody
    Iterable<Classroom> getAllClassrooms(){
        return classroomService.getAllClassrooms();
    }
}
