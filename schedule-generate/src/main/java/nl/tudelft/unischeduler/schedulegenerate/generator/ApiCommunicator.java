package nl.tudelft.unischeduler.schedulegenerate.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.tudelft.unischeduler.schedulegenerate.entities.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class ApiCommunicator {

//  API calls for scheduling:

//  getCourses(): //done
//  returns a list of all courses
//  the course id and the year num is required
//      path = "/courses/all"
  protected ArrayList<Course> getCourses() {
    return null;
  }

//  getStudentsInCourse(Long courseId): //wait till the DB is changed
//  returns a list of all students who belong to the given course,
//  along with the TimeStamp of their most recent Lecture
//  students who are not interested are ignored
//      path = "/userCourse/{courseId}"
  protected ArrayList<Student> getStudentsInCourse(Long courseId) {
    return null;
  }
//  getLecturesInCourse(Long courseId, Timestamp ts, Time t): //done
//  //ts+t
//  returns a list of all lectures who belong to the given course.
//  Timestamp ts is the current time, and Time t is the window of time in which
//  lectures are being scheduled (e.g. when t = 2 only return the lectures that occur in the next 2 weeks)
//  path = "/lectures/{courseId}/{ts}/{t}"
  protected ArrayList<Lecture> getLecturesInCourse(Long courseId, Timestamp ts, int t) {
    return null;
  }
//  getRooms(): //done
//  returns a list of all rooms.
//  the attributes needed are max. capacity, classroom_id
//      path = "/classrooms"
  protected ArrayList<Room> getRooms() {
    return null;
  }
//  assignStudentToLecture(student_id, Lecture_id): //done
//  Creates an entry in lecture_schedule with the given student id
//  and Lecture_id
//  path = "/lectureSchedule/{net_id}/{lectureId}"
  protected void assignStudentToLecture(String student_id, String lecture_id) {

  }
//  assignRoomToLecture(Lecture_id, classroom_id): //done
//  Sets the classroom_id of the given lecture to the given classroom_id
//      path = "/lectures/setClassroom/{lectureId}/{classroomId}"
  protected void assignRoomToLecture(String lecture_id, String classroom_id) {

  }
//  setLectureTime(Lecture_id, TimeStamp t): //done
//  sets the start time of the given lecture to t
//  path = "/lectures/setTime/{lectureId}/{t}
  protected void setLectureTime(String lecture_id, Timestamp timeStamp) {

  }
}

