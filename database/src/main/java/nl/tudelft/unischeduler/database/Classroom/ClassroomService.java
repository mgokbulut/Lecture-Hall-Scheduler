package nl.tudelft.unischeduler.database.Classroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    @Autowired
    private transient ClassroomRepository classroomRepository;

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public Classroom getClassroom(Long classroomId){
        return classroomRepository.findById(classroomId).orElseThrow(() -> new IllegalArgumentException("No such object in DB"));
    }
}
