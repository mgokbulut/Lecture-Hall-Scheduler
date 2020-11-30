package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.Classroom;
import nl.tudelft.unischeduler.database.repositories.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public Iterable<Classroom> getAllClassrooms(){
        return classroomRepository.findAll();
    }
}
