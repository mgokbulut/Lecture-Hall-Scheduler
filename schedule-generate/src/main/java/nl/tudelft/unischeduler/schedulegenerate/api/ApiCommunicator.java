package nl.tudelft.unischeduler.schedulegenerate.api;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import nl.tudelft.unischeduler.schedulegenerate.entities.Course;
import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Room;
import nl.tudelft.unischeduler.schedulegenerate.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiCommunicator {

    //  ---- following are API calls for database module ----

    //  getCourses(): //done
    //  returns a list of all courses
    //  the course id and the year num is required
    //      path = "/courses/all"

    public ArrayList<Course> getCourses() {
        return null;
    }

    //  getStudentsInCourse(Long courseId): //wait till the DB is changed
    //  returns a list of all students who belong to the given course,
    //  along with the TimeStamp of their most recent Lecture
    //  students who are not interested are ignored
    //      path = "/userCourse/{courseId}"
    public ArrayList<Student> getStudentsInCourse(Long courseId) {
        return null;
    }
    //  getLecturesInCourse(Long courseId, Timestamp ts, Time t): //done
    //  //ts+t
    //  returns a list of all lectures who belong to the given course.
    //  Timestamp ts is the current time, and Time t is the window of time in which
    //  lectures are being scheduled (e.g. when t = 2 only return the lectures that
    //  occur in the next 2 weeks)
    //  path = "/lectures/{courseId}/{ts}/{t}"

    public ArrayList<Lecture> getLecturesInCourse(Long courseId, Timestamp ts, int t) {
        return null;
    }
    //  getRooms(): //done
    //  returns a list of all rooms.
    //  the attributes needed are max. capacity, classroom_id
    //      path = "/classrooms"

    public ArrayList<Room> getRooms() {
        return null;
    }

    //  assignStudentToLecture(student_id, Lecture_id): //done
    //  Creates an entry in lecture_schedule with the given student id
    //  and Lecture_id
    //  path = "/lectureSchedule/{net_id}/{lectureId}"

    public void assignStudentToLecture(String studentId, int lectureId) {

    }

    //  assignRoomToLecture(Lecture_id, classroom_id): //done
    //  Sets the classroom_id of the given lecture to the given classroom_id
    //      path = "/lectures/setClassroom/{lectureId}/{classroomId}"

    public void assignRoomToLecture(int lectureId, int classroomId) {

    }

    //  setLectureTime(Lecture_id, TimeStamp t): //done
    //  sets the start time of the given lecture to t
    //  path = "/lectures/setTime/{lectureId}/{t}

    public void setLectureTime(int lectureId, Timestamp timeStamp) {

    }

    // ---- these are API calls for the rules module ----

    public long getIntervalBetweenLectures() {
        // TODO API call to rules module to get the time interval between lectures
        return 0l;
    }

    public boolean allowedOnCampus(Student s) {
        // TODO API call to rules module to know if student is allowed to be on campus
        // he wouldn't be allowed to if he's had corona recently for example
        return true;
    }
}

