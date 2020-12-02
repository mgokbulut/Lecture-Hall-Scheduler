package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.Classroom;
import nl.tudelft.unischeduler.database.entities.Lecture;
import nl.tudelft.unischeduler.database.repositories.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient ClassroomRepository classroomRepository;

    public Classroom getClassroom(Long id){
        return classroomRepository.findById(id).get();
    }

    public Iterable<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }
}
