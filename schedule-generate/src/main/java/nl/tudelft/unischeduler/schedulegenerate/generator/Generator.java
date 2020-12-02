package nl.tudelft.unischeduler.schedulegenerate.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.tudelft.unischeduler.schedulegenerate.generator.ApiCommunicator;

@Service
public class Generator {

  @Autowired
  private ApiCommunicator apiCommunicator;

  public void scheduleGenerate() {

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

