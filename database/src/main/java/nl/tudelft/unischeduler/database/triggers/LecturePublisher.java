package nl.tudelft.unischeduler.database.triggers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LecturePublisher {

    private Set<LectureSubscriber> subscribers;

    public LecturePublisher() {
        this.subscribers = new HashSet<>();
    }

    public void addSubscriber(LectureSubscriber sub) {
        if(!this.subscribers.contains(sub)) {
            this.subscribers.add(sub);
        }
    }

    public void removeSubscriber(LectureSubscriber sub) {
        this.subscribers.remove(sub);
    }

    public void notifySubscribers(long lectureId, String[] action, String author) {
        Iterator<LectureSubscriber> it = this.subscribers.iterator();
        while(it.hasNext()) {
            LectureSubscriber sub = it.next();
            sub.update(lectureId, action, author);
        }
    }

}
