package nl.tudelft.unischeduler.database.triggers;

public class StudentNotifier extends UserNotifier {

  @Override
  // we're going to first implement being notified when a class is moved online
  public boolean update(int lectureId, String action) {
    
    return false;
  }

}
