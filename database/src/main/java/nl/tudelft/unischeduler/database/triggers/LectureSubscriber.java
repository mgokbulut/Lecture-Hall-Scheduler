package nl.tudelft.unischeduler.database.triggers;

public interface LectureSubscriber {

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
