package nl.tudelft.unischeduler.database.triggers;

import java.util.HashSet;
import java.util.List;

public abstract class UserNotifier implements LectureSubscriber {

  private String netId;

  private HashSet<Integer> lectureIds;

  public HashSet<Integer> getLectureIds;

  public String getNetId() { return this.netId; }

  public void setNetId(String netId) { this.netId = netId; }

  public void setLectureIds(HashSet<Integer> lectureIds) { this.lectureIds = lectureIds; }

  public void addLectureIds(List<Integer> ids) {
    for (int i = 0; i < ids.size(); i++) {
      int id = ids.get(i);
      if (!lectureIds.contains(id)) {
        lectureIds.add(id);
      }
    }
  }

  public boolean update(int lectureId, String[] actions) {
    boolean worked = true;
    for (int i = 0; i < actions.length; i++) {
      String a = actions[i];
      worked = worked && update(lectureId, a);
    }
    return worked;
  }

  // meant to be extended by subclasses where need be
  public abstract boolean update(int lectureId, String action);

}
