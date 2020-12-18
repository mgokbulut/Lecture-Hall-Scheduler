package nl.tudelft.unischeduler.database.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.unischeduler.database.classroom.Classroom;
import nl.tudelft.unischeduler.database.classroom.ClassroomRepository;
import nl.tudelft.unischeduler.database.classroom.ClassroomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;



public class ClassroomServiceTest {

    private transient List<Classroom> classrooms;

    private final transient ClassroomRepository classroomRepository =
            Mockito.mock(ClassroomRepository.class);

    @BeforeEach
    void setup() {
        classrooms = new ArrayList<>(
                List.of(
                    new Classroom(0L, 50, "Amper Hall", "EWI", 1),
                    new Classroom(1L, 100, "Boole Hall", "EWI", 2),
                    new Classroom(2L, 250, "Pi Hall", "EWI", 1)
                ));
    }

    @Test
    public void getAllClassroomsTest() {
        when(classroomRepository.findAll()).thenReturn(classrooms);
        ClassroomService classroomService = new ClassroomService(classroomRepository);
        Assertions.assertEquals(classroomService.getAllClassrooms(), classrooms);
    }

    @Test
    public void getClassroomByIdTest() {
        when(classroomRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(classrooms.get(1)));
        ClassroomService classroomService = new ClassroomService(classroomRepository);
        Assertions.assertEquals(classroomService.getClassroom(1L), classrooms.get(1));
    }

}
