package nl.tudelft.unischeduler.database.service;

import nl.tudelft.unischeduler.database.classroom.Classroom;
import nl.tudelft.unischeduler.database.classroom.ClassroomRepository;
import nl.tudelft.unischeduler.database.classroom.ClassroomService;
import nl.tudelft.unischeduler.database.course.Course;
import nl.tudelft.unischeduler.database.course.CourseRepository;
import nl.tudelft.unischeduler.database.course.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CourseServiceTest {

    private transient List<Course> courses;

    private final transient CourseRepository courseRepository =
            Mockito.mock(CourseRepository.class);

    @BeforeEach
    void setup(){
        courses = new ArrayList<>(
                List.of(
                        new Course(0L, "ADS", 1),
                        new Course(1L, "SEM", 2),
                        new Course(2L, "AD", 2)
                ));
    }

    @Test
    public void getAllCoursesTest(){
        when(courseRepository.findAll()).thenReturn(courses);
        CourseService courseService = new CourseService(courseRepository);

        Assertions.assertEquals(courseService.getAllCourses(), courses);

        verify(courseRepository, times(1)).findAll();
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void getCourseTest(){
        when(courseRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(courses.get(1)));
        CourseService courseService = new CourseService(courseRepository);

        Assertions.assertEquals(courseService.getCourse(1L), courses.get(1));

        verify(courseRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    public void createCourseTest(){
        when(courseRepository.save(Mockito.any(Course.class))).thenAnswer(x -> x.getArguments()[0]);
        CourseService courseService = new CourseService(courseRepository);

        Assertions.assertEquals(new ResponseEntity<>(HttpStatus.OK), courseService.createCourse("Test", 5));

        verify(courseRepository, times(1)).save(Mockito.any(Course.class));
        verify(courseRepository, times(1)).findByAndNameAndYear("Test", 5);
        verifyNoMoreInteractions(courseRepository);
    }
}
