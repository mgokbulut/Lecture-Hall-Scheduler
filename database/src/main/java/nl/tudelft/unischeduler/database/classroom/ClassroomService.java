package nl.tudelft.unischeduler.database.classroom;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClassroomService {

    @Autowired
    private transient ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository){
        this.classroomRepository = classroomRepository;
    }

    /**
     * Returns all the Classrooms.
     *
     * @return Classroom object
     */
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    /**
     * Returns specified Classroom.
     *
     * @param classroomId classroom ID
     * @return Classroom object
     */
    public Classroom getClassroom(Long classroomId) {
        return classroomRepository
                .findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("No such object in DB"));
    }
}
