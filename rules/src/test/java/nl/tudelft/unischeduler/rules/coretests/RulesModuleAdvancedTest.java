package nl.tudelft.unischeduler.rules.coretests;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import nl.tudelft.unischeduler.rules.services.ClassRoomDatabaseService;
import nl.tudelft.unischeduler.rules.services.LectureDatabaseService;
import nl.tudelft.unischeduler.rules.services.StudentDatabaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

public class RulesModuleAdvancedTest {

    private transient RulesModule rulesModule;

    private transient ClassRoomDatabaseService classRoomDatabaseServiceMock;
    private transient LectureDatabaseService lectureDatabaseServiceMock;
    private transient StudentDatabaseService studentDatabaseServiceMock;

    private transient List<Lecture> lectures;

    private final transient Timestamp timestamp = new Timestamp(new GregorianCalendar(
            2020, Calendar.DECEMBER, 1, 0, 0).getTimeInMillis());

    /**
     * Generates a RulesModule class with mocks for the databaseServices.
     */
    @BeforeEach
    public void setup() {
        rulesModule = new RulesModule();
        int[] firstThreshold = new int[]{0, 10};
        int[] secondThreshold = new int[]{100, 20};
        rulesModule.setThresholds(new int[][]{firstThreshold, secondThreshold});
        rulesModule.setBreakTime(45);
        rulesModule.setMaxDays(14);
        classRoomDatabaseServiceMock = Mockito.mock(ClassRoomDatabaseService.class);
        lectureDatabaseServiceMock = Mockito.mock(LectureDatabaseService.class);
        studentDatabaseServiceMock = Mockito.mock(StudentDatabaseService.class);
        rulesModule.setClassRoomDatabaseService(classRoomDatabaseServiceMock);
        rulesModule.setLectureDatabaseService(lectureDatabaseServiceMock);
        rulesModule.setStudentDatabaseService(studentDatabaseServiceMock);
        lectures = List.of(new Lecture(1, 50, timestamp, Time.valueOf("02:00:00"), new Room(1, 300, "Room")),
                new Lecture(1, 50, timestamp, Time.valueOf("02:00:00"), new Room(1, 300, "Room")));
    }

    /**
     * Sets a new ruleset to the rulesModule
     */
    @Test
    public void setRulesTest() {
        int[] firstThreshold = new int[]{100, 10};
        int[] secondThreshold = new int[]{200, 20};
        Ruleset newRules = new Ruleset(new int[][]{firstThreshold, secondThreshold}, 120, 7);
        rulesModule.setRules(newRules);
        assertThat(rulesModule.getRules()).isEqualTo(newRules);
        assertThat(rulesModule.getThresholds()).isEqualTo(newRules.getThresholds());
        assertThat(rulesModule.getBreakTime()).isEqualTo(120);
        assertThat(rulesModule.getMaxDays()).isEqualTo(7);
    }

    @ParameterizedTest(name = "getCapacityTest")
    @CsvSource({"50, 5", "99, 9", "101, 20", "150, 30", "250, 50"})
    public void getCapacityTest(int input, int expected) {
        assertThat(rulesModule.getCapacity(input)).isEqualTo(expected);
    }

    @Test
    public void getCapacityOfRoomTest() {
        when(classRoomDatabaseServiceMock.getClassroom(anyInt())).thenReturn(new Room(1, 101, "testRoom"));

        assertThat(rulesModule.getCapacityOfRoom(1)).isEqualTo(20);
    }

    @Test
    public void verifyLecturesTest(){
        when(lectureDatabaseServiceMock.getLectures()).thenReturn(new Lecture[]{lectures.get(0)});


        Assertions.assertEquals(rulesModule.verifyLectures(), false);
    }
}
