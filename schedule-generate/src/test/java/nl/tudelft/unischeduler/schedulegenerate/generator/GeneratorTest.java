
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
package nl.tudelft.unischeduler.schedulegenerate.generator;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.ArgumentMatchers.anyInt;
    import static org.mockito.ArgumentMatchers.anyString;
    import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.stubbing.Answer;

public class GeneratorTest {

 @Mock         private nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator mockApiCommunicator;

    private nl.tudelft.unischeduler.schedulegenerate.generator.Generator generatorUnderTest;

@Before
public void setUp() throws Exception {
 initMocks(this);
                                generatorUnderTest = new Generator(mockApiCommunicator) ;
}
                
    @Test
    public void testScheduleGenerate() throws Exception {
    // Setup
                final java.sql.Timestamp currentTime = new java.sql.Timestamp(0L);
                
            // Configure ApiCommunicator.getCourses(...).
        final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Course> courses = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Course(0L, "name", java.util.Set.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime())), java.util.Set.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021)), 2021)));
            when( mockApiCommunicator .getCourses()).thenReturn(courses);
 
                             when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );
                
            // Configure ApiCommunicator.getRooms(...).
        final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Room> rooms = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
            when( mockApiCommunicator .getRooms()).thenReturn(rooms);

    // Run the test
 generatorUnderTest.scheduleGenerate(currentTime);

        // Verify the results
        verify( mockApiCommunicator ).assignRoomToLecture(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
        verify( mockApiCommunicator ).setLectureTime(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new java.sql.Timestamp(0L)));
        verify( mockApiCommunicator ).assignStudentToLecture(eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime())),any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class));
    }
                                                                                    
    @Test
    public void testCreateTimeTable() throws Exception {
    // Setup
                final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Lecture> lectures = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021)));
        final java.sql.Timestamp currentTime = new java.sql.Timestamp(0L);

    // Run the test
 final java.util.List<java.util.List<nl.tudelft.unischeduler.schedulegenerate.entities.Lecture>> result =  generatorUnderTest.createTimeTable(lectures,currentTime,0);

        // Verify the results
    }
                                                                                    
    @Test
    public void testScheduling() throws Exception {
    // Setup
                final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Lecture> lectures = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021)));
        final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Room> rooms = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
        final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Course> courses = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Course(0L, "name", java.util.Set.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime())), java.util.Set.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021)), 2021)));
                            when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );

    // Run the test
 generatorUnderTest.scheduling(lectures,rooms,courses);

        // Verify the results
        verify( mockApiCommunicator ).assignRoomToLecture(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
        verify( mockApiCommunicator ).setLectureTime(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new java.sql.Timestamp(0L)));
        verify( mockApiCommunicator ).assignStudentToLecture(eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime())),any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class));
    }
                                                                                    
    @Test
    public void testSchedulingYears() throws Exception {
    // Setup
                final java.util.List<java.util.List<nl.tudelft.unischeduler.schedulegenerate.entities.Course>> coursesPerYear = java.util.List.of(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Course(0L, "name", java.util.Set.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime())), java.util.Set.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021)), 2021)));
        final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Room> rooms = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
                            when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );

    // Run the test
 final boolean result =  generatorUnderTest.schedulingYears(0,coursesPerYear,rooms);

        // Verify the results
 assertThat(result).isTrue() ;
        verify( mockApiCommunicator ).assignRoomToLecture(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
        verify( mockApiCommunicator ).setLectureTime(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new java.sql.Timestamp(0L)));
        verify( mockApiCommunicator ).assignStudentToLecture(eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime())),any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class));
    }
                                                                                    
    @Test
    public void testSchedulingLectures() throws Exception {
    // Setup
                final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Lecture> lectures = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021)));
        final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Room> rooms = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
        final java.util.Set<nl.tudelft.unischeduler.schedulegenerate.entities.Student> courseStudents = java.util.Set.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime()));
        final java.util.PriorityQueue<nl.tudelft.unischeduler.schedulegenerate.entities.Student> studentsQueue = new java.util.PriorityQueue<>();
                            when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );

    // Run the test
 final boolean result =  generatorUnderTest.schedulingLectures(lectures,rooms,courseStudents,studentsQueue);

        // Verify the results
 assertThat(result).isTrue() ;
        verify( mockApiCommunicator ).assignRoomToLecture(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
        verify( mockApiCommunicator ).setLectureTime(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new java.sql.Timestamp(0L)));
        verify( mockApiCommunicator ).assignStudentToLecture(eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime())),any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class));
    }
                                                                                    
    @Test
    public void testSchedulingLecture() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.schedulegenerate.entities.Lecture l = new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021);
        final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Room> rooms = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
        final java.util.Set<nl.tudelft.unischeduler.schedulegenerate.entities.Student> courseStudents = java.util.Set.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime()));
        final java.util.PriorityQueue<nl.tudelft.unischeduler.schedulegenerate.entities.Student> studentsQueue = new java.util.PriorityQueue<>();
                            when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );

    // Run the test
 final boolean result =  generatorUnderTest.schedulingLecture(l,rooms,courseStudents,studentsQueue);

        // Verify the results
 assertThat(result).isTrue() ;
        verify( mockApiCommunicator ).assignRoomToLecture(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
        verify( mockApiCommunicator ).setLectureTime(any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class),eq(new java.sql.Timestamp(0L)));
        verify( mockApiCommunicator ).assignStudentToLecture(eq(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime())),any(nl.tudelft.unischeduler.schedulegenerate.entities.Lecture.class));
    }
                                                                                    
    @Test
    public void testMoveOnline() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.schedulegenerate.entities.Lecture l = new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021);
        final nl.tudelft.unischeduler.schedulegenerate.entities.Room room = new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name");
        final nl.tudelft.unischeduler.schedulegenerate.entities.Room onlineRoom = new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name");
        final nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator apiCom = new nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator();
        final java.util.Set<nl.tudelft.unischeduler.schedulegenerate.entities.Student> courseStudents = java.util.Set.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime()));
                            when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );

    // Run the test
 final boolean result =  generatorUnderTest.moveOnline(l,room,onlineRoom,apiCom,courseStudents);

        // Verify the results
 assertThat(result).isTrue() ;
    }
                                                                                    
    @Test
    public void testFindRoom() throws Exception {
    // Setup
                final java.util.ArrayList<nl.tudelft.unischeduler.schedulegenerate.entities.Room> rooms = new java.util.ArrayList<>(java.util.List.of(new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name")));
        final nl.tudelft.unischeduler.schedulegenerate.entities.Lecture lecture = new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021);
        final nl.tudelft.unischeduler.schedulegenerate.entities.Room expectedResult = new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name");
                            when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );

    // Run the test
 final nl.tudelft.unischeduler.schedulegenerate.entities.Room result =  generatorUnderTest.findRoom(rooms,lecture);

        // Verify the results
 assertThat(result).isEqualTo(expectedResult ) ;
    }
                                                                                    
    @Test
    public void testGetEarliestTime() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.schedulegenerate.entities.Room room = new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name");
        final nl.tudelft.unischeduler.schedulegenerate.entities.Lecture lecture = new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021);
        final java.sql.Timestamp expectedResult = new java.sql.Timestamp(0L);
                            when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );

    // Run the test
 final java.sql.Timestamp result =  generatorUnderTest.getEarliestTime(room,lecture);

        // Verify the results
 assertThat(result).isEqualTo(expectedResult ) ;
    }
                                                                                    
    @Test
    public void testIsFree() throws Exception {
    // Setup
                final java.sql.Timestamp timeslot = new java.sql.Timestamp(0L);
        final nl.tudelft.unischeduler.schedulegenerate.entities.Room room = new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name");
        final nl.tudelft.unischeduler.schedulegenerate.entities.Lecture lecture = new nl.tudelft.unischeduler.schedulegenerate.entities.Lecture(0, 0, new java.sql.Time(0L), false, 2021);
        final java.sql.Timestamp expectedResult = new java.sql.Timestamp(0L);
                            when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );

    // Run the test
 final java.sql.Timestamp result =  generatorUnderTest.isFree(timeslot,room,lecture,1);

        // Verify the results
 assertThat(result).isEqualTo(expectedResult ) ;
    }
                                                                                    
    @Test
    public void testGetCapacity() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.schedulegenerate.entities.Room room = new nl.tudelft.unischeduler.schedulegenerate.entities.Room(0, 0, "name");

    // Run the test
 final int result =  generatorUnderTest.getCapacity(room);

        // Verify the results
 assertThat(result).isEqualTo( 0 ) ;
    }
                                                                                    
    @Test
    public void testGetIntervalBetweenLectures() throws Exception {
    // Setup
                                    when( mockApiCommunicator .getIntervalBetweenLectures()).thenReturn( 0L );

    // Run the test
 final long result =  generatorUnderTest.getIntervalBetweenLectures();

        // Verify the results
 assertThat(result).isEqualTo( 0L ) ;
    }
                                                                                    
    @Test
    public void testGetApiCommunicator() throws Exception {
    // Setup
        
    // Run the test
 final nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator result =  generatorUnderTest.getApiCommunicator();

        // Verify the results
    }
                                                                                    
    @Test
    public void testEquals() throws Exception {
    // Setup
        
    // Run the test
 final boolean result =  generatorUnderTest.equals("o");

        // Verify the results
 assertThat(result).isTrue() ;
    }
                                                                                    
    @Test
    public void testHashCode() throws Exception {
    // Setup
        
    // Run the test
 final int result =  generatorUnderTest.hashCode();

        // Verify the results
 assertThat(result).isEqualTo( 0 ) ;
    }
                                                                                    
    @Test
    public void testToString() throws Exception {
    // Setup
        
    // Run the test
 final java.lang.String result =  generatorUnderTest.toString();

        // Verify the results
 assertThat(result).isEqualTo( "result" ) ;
    }
                                                                    }

