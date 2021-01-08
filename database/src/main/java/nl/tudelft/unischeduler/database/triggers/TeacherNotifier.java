package nl.tudelft.unischeduler.database.triggers;

public class TeacherNotifier extends UserNotifier {

  @Override
  public boolean moved_online(int lectureId) {
    // make the api call
    return true;
  }

  @Override
  public boolean date_change(int lectureId) {
    // make the api call
    return true;
  }

  @Override
  public boolean time_change(int lectureId) {
    // make the api call
    return true;
  }

  @Override
  public boolean moved_on_campus(int lectureId) {
    // make the api call
    return true;
  }
  
}
