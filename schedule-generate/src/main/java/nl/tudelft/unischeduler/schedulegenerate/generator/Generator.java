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

  private List<List<Lecture>> createTimeTable(ArrayList<Lecture> lectures,
                                                   Timestamp currentTime, int nOfDays) { // lectures
    List<List<Lecture>> timeTable = new ArrayList<>(nOfDays);
    // initialize
    for (int i = 0; i < timeTable.size(); i++) {
      timeTable.add(new ArrayList<Lecture>());
    }
    // distribute the lectures
    for (int i = 0; i < lectures.size(); i++) {
      Lecture l = lectures.get(i);
      // now get which day compared to currentTime, as an int
      int lecDay = calDistance(l.getStartTime(), currentTime);
      timeTable.get(lecDay).add(l);
    }
    return timeTable;
  }
  /**
   * Calculates the distance in days between two dates excluding
   * weekends.
   *
   * @param c1 the timestamp of the first date
   * @param c2 the timestamp of the second date
   * @return the number of days difference between them
   */
  private int calDistance(Timestamp c1, Timestamp c2) {
    long long1 = c1.getTime();
    long long2 = c2.getTime();
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();

    cal1.setTimeInMillis(long1);
    cal2.setTimeInMillis(long2);

    int nDays = 0;

    while (cal1.before(cal2)) {
      if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
          &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
        nDays++;
      }
      cal1.add(Calendar.DATE,1);
    }

    return nDays;
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

