package nl.tudelft.unischeduler.database.triggers;

public class TeacherNotifier extends UserNotifier {

  @Override
  public boolean update(int lectureId, String action) {

    return false;
  }
}
