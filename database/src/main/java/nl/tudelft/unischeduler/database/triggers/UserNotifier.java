package nl.tudelft.unischeduler.database.triggers;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.unischeduler.database.exception.ConnectionException;
import nl.tudelft.unischeduler.database.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserNotifier implements LectureSubscriber {

    @Autowired
    protected WebClient.Builder webClientBuilder;

    protected WebClient webClient;

    private String netId;

    private HashSet<Long> lectureIds;

    public HashSet<Long> getLectureIds;

    public String getNetId() {
        return this.netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public void setLectureIds(HashSet<Long> lectureIds) {
        this.lectureIds = lectureIds;
    }

    @PostConstruct
    public void setUp() {
        webClientBuilder.baseUrl("http://gateway-authentication");
        webClient = webClientBuilder.build();
    }

    /**
     * This method checks whether the status code of the response is Ok
     * and if it is not will throw the appropriate exception.
     *
     * @param response the response object gotten from the webclient.
     * @throws IOException When response is null or the status code != 200.
     */
    public void verifyStatusCode(ResponseEntity<?> response) throws IOException {
        if (response == null) {
            throw new ConnectionException("the response was null");
        }
        HttpStatus status = response.getStatusCode();
        switch (status) {
            case OK:
                return;
            case NOT_FOUND:
                throw new NotFoundException();
            default:
                System.err.println("unknown response code: " + status);
                throw new ConnectionException("The connection with the database failed");
        }
    }

    /**
     * Add lecture IDs that the UserNotifier is subscribed to.
     *
     * @param ids the IDs of the lectures
     */
    public void addLectureIds(List<Long> ids) {
        for (int i = 0; i < ids.size(); i++) {
            long id = ids.get(i);
            if (!lectureIds.contains(id)) {
                lectureIds.add(id);
            }
        }
    }

    /**
     * Part of the Observer design pattern. Receive an update
     * from the publisher.
     *
     * @param lectureId the id of the concerned lecture
     * @param actions the actions to be notified for
     * @param actor the cause of the update
     * @return whether the update was successful
     */
    public boolean update(long lectureId, String[] actions, String actor) {
        boolean worked = true;
        for (int i = 0; i < actions.length; i++) {
            String action = actions[i];
            worked = worked && update(lectureId, action, actor);
        }
        return worked;
    }

    /**
     * Performs the right kind of update depending on the specific
     * action the update is for.
     *
     * @param lectureId the id of the concerned lecture
     * @param action the actions to be notified for
     * @param actor the cause of the update
     * @return whether it worked or not
     */
    public boolean update(long lectureId, String action, String actor) {
        try {
            if (lectureIds.contains(lectureId)) {
                // add here for any new one
                switch (action) {

                    case LectureSubscriber.MOVED_ONLINE:
                        this.moved_online(lectureId, actor);
                        break;

                    case LectureSubscriber.DATE_CHANGE:
                        this.date_change(lectureId, actor);
                        break;

                    case LectureSubscriber.TIME_CHANGE:
                        this.time_change(lectureId, actor);
                        break;

                    case LectureSubscriber.MOVED_ON_CAMPUS:
                        this.moved_on_campus(lectureId, actor);
                        break;

                    case LectureSubscriber.STUDENT_ASSIGNED_TO_COURSE:
                        this.student_assigned_to_course(lectureId, actor);
                        break;

                    default: return false;
                }
            }
        } catch (Exception e) {
            // will have to write this to a log file at some point
            return false;
        }
        return true;
    }

    protected abstract boolean moved_online(long lectureId, String actor) throws IOException;

    protected abstract boolean date_change(long lectureId, String actor) throws IOException;

    protected abstract boolean time_change(long lectureId, String actor) throws IOException;

    protected abstract boolean moved_on_campus(long lectureId, String actor) throws IOException;

    protected abstract boolean student_assigned_to_course
            (long lectureId, String actor) throws IOException;

}
