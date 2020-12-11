//package nl.tudelft.unischeduler.database.EntityTests;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//import nl.tudelft.unischeduler.database.LectureSchedule.LectureSchedule;
//import nl.tudelft.unischeduler.database.LectureSchedule.LectureScheduleRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@DataJpaTest
//public class LectureScheduleTest {
//    @Autowired
//    private LectureScheduleRepository lectureScheduleRepository;
//
//    @Test
//    public void saveAndRetrieve() {
//        LectureSchedule lectureSchedule = new LectureSchedule(1, 1);
//        lectureScheduleRepository.save(lectureSchedule);
//        LectureSchedule test = lectureScheduleRepository
//                .findByLectureIdAndScheduleId(lectureSchedule.getLectureId(), lectureSchedule.getScheduleId()).get();
//        assertEquals(lectureSchedule, test);
//    }
//
//    @Test
//    public void equals() {
//        LectureSchedule lectureSchedule = new LectureSchedule(1, 1);
//        LectureSchedule test = new LectureSchedule(1, 1);
//        assertEquals(lectureSchedule, test);
//    }
//}
