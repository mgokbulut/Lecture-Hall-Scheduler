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
  public boolean update(int lectureId, String action) {
    if (lectureIds.contains(lectureId)) {
      // add here for any new one
      switch (action) {

        case LectureSubscriber.MOVED_ONLINE: this.moved_online(lectureId);

        case LectureSubscriber.DATE_CHANGE: this.date_change(lectureId);

        case LectureSubscriber.TIME_CHANGE: this.time_change(lectureId);

        case LectureSubscriber.MOVED_ON_CAMPUS: this.moved_on_campus(lectureId);
      }
    }
    return true;
  };

  public abstract boolean moved_online(int lectureId);

  public abstract boolean date_change(int lectureId);

  public abstract boolean time_change(int lectureId);

  public abstract boolean moved_on_campus(int lectureId);

}
