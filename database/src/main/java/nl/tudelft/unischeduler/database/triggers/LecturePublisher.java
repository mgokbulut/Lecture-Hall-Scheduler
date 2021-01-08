package nl.tudelft.unischeduler.database.triggers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class LecturePublisher {

    private transient Set<LectureSubscriber> subscribers;

    public LecturePublisher() {
        this.subscribers = new HashSet<>();
    }

    /**
     * Adds a subscriber to the list, if not already there.
     *
     * @param sub the concerned subscriber
     */
    public void addSubscriber(LectureSubscriber sub) {
        if (!this.subscribers.contains(sub)) {
            this.subscribers.add(sub);
        }
    }

    public void removeSubscriber(LectureSubscriber sub) {
        this.subscribers.remove(sub);
    }

    /**
     * Sends update to all subscribers.
     *
     * @param lectureId the concerned lecture
     * @param actions the actions to be notified for
     * @param author the cause of the actions
     */
    public void notifySubscribers(long lectureId, String[] actions, String author) {
        Iterator<LectureSubscriber> it = this.subscribers.iterator();
        while (it.hasNext()) {
            LectureSubscriber sub = it.next();
            sub.update(lectureId, actions, author);
        }
    }

}
