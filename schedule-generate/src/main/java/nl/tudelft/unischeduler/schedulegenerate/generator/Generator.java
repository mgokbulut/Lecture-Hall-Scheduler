package nl.tudelft.unischeduler.schedulegenerate.generator;

import nl.tudelft.unischeduler.schedulegenerate.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.tudelft.unischeduler.schedulegenerate.generator.ApiCommunicator;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class Generator {

  @Autowired
  private ApiCommunicator apiCommunicator;

  public void scheduleGenerate(Timestamp currentTime) {
    Time window = new Time(24 * 10, 0, 0); // placeholder
    ArrayList<Course> courses = apiCommunicator.getCourses();
    ArrayList<Lecture> lectures = new ArrayList<Lecture>();
    // populate the lectures array
    for(int i = 0; i < courses.size(); i++) {
      Course course = courses.get(i);
      ArrayList<Lecture> toAdd =
          apiCommunicator.getLecturesInCourse(course.getId(), currentTime, window);
      lectures.addAll(toAdd);
    }
  }
  private void createTimeTable() { // lectures

  }

  private void scheduling() { // lectures, timetable

  }

  private void findRoom() { // rooms, date, numStudents, lecture, timeTable

  }

  private void getEarliestTime() { // room, lecture, timeTable

  }

  private void isFree() { // timeslot, room, lecture, timeTable

  }


}

