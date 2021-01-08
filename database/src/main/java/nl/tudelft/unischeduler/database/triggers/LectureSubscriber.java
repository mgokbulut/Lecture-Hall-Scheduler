package nl.tudelft.unischeduler.database.triggers;

public interface LectureSubscriber {

  public static String MOVED_ONLINE = "100 lecture was moved online";
  public static String TIME_CHANGE = "101 lecture was scheduled to another time";
  public static String DATE_CHANGE = "102 lecture was moved to another day";
  public static String MOVED_ON_CAMPUS = "103 lecture was moved on campus";

  /**
   * Update method from the Observer design pattern.
   * Notifies for any number of changes made to a lecture.
   *
   * @param lectureId the id of the concerned lecture
   * @param actions the actions to be notified for
   * @return whether the operation succeeded
   */
  public boolean update(int lectureId, String[] actions);

  /**
   * Same method, but for single actions.
   *
   * @param lectureId the id of the concerned lecture
   * @param action the actions to be notified for
   * @return whether the operation succeeded
   */
  public boolean update(int lectureId, String action);

}
