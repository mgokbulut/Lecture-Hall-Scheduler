package nl.tudelft.unischeduler.schedulegenerate.generator;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import nl.tudelft.unischeduler.schedulegenerate.entities.Course;
import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Room;
import nl.tudelft.unischeduler.schedulegenerate.entities.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Generator {

  @Autowired
  private transient ApiCommunicator apiCommunicator;

  /**
   * Generates a full schedule by adding it to the database using API calls.
   *
   * @param currentTime Time at which to start scheduling
   */
  public void scheduleGenerate(Timestamp currentTime) {

    int numOfDays = 10; // placeholder
    ArrayList<Course> courses = apiCommunicator.getCourses();
    ArrayList<Lecture> lectures = new ArrayList<>();

    // populate the lectures array
    for (int i = 0; i < courses.size(); i++) {
      Course course = courses.get(i);
      ArrayList<Lecture> toAdd =
          apiCommunicator.getLecturesInCourse(course.getId(), currentTime, numOfDays);
      lectures.addAll(toAdd);
    }

    // sort by end time
    Collections.sort(lectures);

    // Now we need to know which lectures are on which days in an
    // easy-to-access datastructure.
    List<List<Lecture>> timeTable = createTimeTable(lectures, currentTime, numOfDays);

    scheduling(lectures, timeTable, currentTime);
  }

  private List<List<Lecture>> createTimeTable(ArrayList<Lecture> lectures,
                                              Timestamp currentTime,
                                              int numOfDays) {

    List<List<Lecture>> timeTable = new ArrayList<>(numOfDays);
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
    int maxIterations = 100; // just to never get stuck in the while loop
    long long1 = c1.getTime();
    long long2 = c2.getTime();
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();

    cal1.setTimeInMillis(long1);
    cal2.setTimeInMillis(long2);

    int numDays = 0;
    int iteration = 0;
    while (cal1.before(cal2) && iteration < maxIterations) {
      if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
          && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
        numDays = numDays + 1;
      }
      cal1.add(Calendar.DATE,1);
      iteration++;
    }

    return numDays;
  }

  /**
   * Assigns and schedules courses that are not assigned yet.
   * This is tested based on whether they are assigned a room or not.
   * @param lectures list containing every lecture there is
   * @param timeTable list of lists containing all lectures, per day
   */
  private void scheduling(ArrayList<Lecture> lectures,
                          List<List<Lecture>> timeTable, Timestamp currentTime) {
    // TODO change this to a proper template online room, ask Kuba
    Room onlineRoom = new Room(0, Integer.MAX_VALUE, "online_room");
    // this value is placeholder until we find a better solution, should work
    int maxIterationMultiplier = 2;
    // get all the rooms available on campus
    ArrayList<Room> rooms = apiCommunicator.getRooms();

    // get all the courses
    ArrayList<Course> courses = apiCommunicator.getCourses();
    // and separate them per year
    List<List<Course>> coursesPerYear = new ArrayList<>();
    for (int i = 0; i < courses.size(); i++) {
      Course c = courses.get(i);
      coursesPerYear.get(c.getYear()).add(c);
    }

    // for every uni year
    for (int i = 0; i < coursesPerYear.size(); i++) {
      // for each of its courses
      List<Course> coursesIthYear = coursesPerYear.get(i);
      for (int j = 0; j < coursesIthYear.size(); j++) {
        Course c = coursesIthYear.get(j);
        // get its students
        Set<Student> courseStudents = c.getStudents();
        // sort them per when they were last on campus in a priority queue
        PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
        studentsQueue.addAll(courseStudents);

        // for each lecture in the course
        ArrayList<Lecture> lecturesCurrentCourse = new ArrayList<Lecture>(c.getLectures());
        for (int k = 0; k < lecturesCurrentCourse.size(); k++) {
          Lecture l = lecturesCurrentCourse.get(k);
          // if the lecture is not assigned in the schedule yet
          if (l.getRoom() == null && !(l.getIsOnline())) {
            // then we want to assign it a room
            Room room = findRoom(rooms, currentTime, l, timeTable, currentTime);
            // if no room was found (no space or bug)
            if (room == null) {
              // then we want to move it online
              // so we set its time, its room, and its isOnline
              l.setStartTime(getEarliestTime(room, l, timeTable));
              l.setRoom(onlineRoom);
              l.setIsOnline(true);
              continue;
            }
            // if a room was found
            // we assign it
            int capacity = getCapacity(room);
            apiCommunicator.assignRoomToLecture(l.getId(), room.getId());
            apiCommunicator.setLectureTime(l.getId(), l.getStartTime());

            // then we want to add students to it
            // first we have to figure out which students to add, without duplicates
            Set<Student> studentsToAdd = new HashSet<>();
            List<Student> notSelected = new ArrayList<>();
            int iteration = 0;
            while (studentsToAdd.size() < capacity
                && iteration < maxIterationMultiplier * capacity) {
              try {
                Student prioStudent = studentsQueue.remove();
                // if student wasn't added already, add him
                if (!studentsToAdd.contains(prioStudent)) {
                  studentsToAdd.add(prioStudent);
                  prioStudent.setLastTimeOnCampus(new Date(l.getStartTime().getTime()));
                } else {
                  notSelected.add(prioStudent);
                }
              } catch (Exception e) {
                System.out.println("there was an error scheduling students to lectures...");
                System.out.println(e.toString());
              }
              iteration++;
            }
            // we add back the ones that weren't selected
            studentsQueue.addAll(notSelected);

            // now we can add the students that were selected
            List<Student> addTheseStudents = new ArrayList<>(studentsToAdd);
            for (int a = 0; a < addTheseStudents.size(); a++) {
              Student s = addTheseStudents.get(a);
              apiCommunicator.assignStudentToLecture(s.getNetId(), l.getId());
              s.setLastTimeOnCampus(l.getStartTime());
              studentsQueue.add(s);
            }
          }
        }
      }
    }
  }

  private Room findRoom(ArrayList<Room> rooms, Timestamp date,
                        Lecture lecture, List<List<Lecture>> timeTable,
                        Timestamp currentTime) {
    // sort in descending order
    Collections.sort(rooms);
    Collections.reverse(rooms);
    // for each room
    for (int i = 0; i < rooms.size(); i++) {
      Room currRoom = rooms.get(i);
      // get their latest available time
      Timestamp time = getEarliestTime(currRoom, lecture, timeTable);
      // if there is none, return null
      if (time == null) {
        continue;
      }
      // otherwise update the relevant values
      lecture.setStartTime(time);
      lecture.setRoom(currRoom);
      int day = calDistance(currentTime, time);
      timeTable.get(day).add(lecture);
      return currRoom;
    }
    return null;
  }

  private Timestamp getEarliestTime(Room room, Lecture lecture, List<List<Lecture>> timeTable) {
    return null;
  }

  private Timestamp isFree(Timestamp timeslot, Room room,
                           Lecture lecture, List<List<Lecture>> timeTable) {
    return null;
  }

  private int getCapacity(Room room) {
    // TODO make an API call here to find out the rule for capacity
    return room.getCapacity();
  }


}

