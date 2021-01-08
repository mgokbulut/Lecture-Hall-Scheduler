package nl.tudelft.unischeduler.database.triggers;

import java.io.IOException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@EqualsAndHashCode(callSuper = true)
@Service
@Data
public class TeacherNotifier extends UserNotifier {

    @Override
    protected boolean moved_online(long lectureId, String actor) throws IOException {
        // make the api call
        if (!actor.equals(LectureSubscriber.TEACHER)) {
            ResponseEntity<Void> response = webClient.post()
                    .uri("notification/moved_online"
                            + "/" + this.getNetId()
                            + "/" + lectureId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            verifyStatusCode(response);
            return true;
        }
        return true;
    }

    @Override
    protected boolean date_change(long lectureId, String actor) throws IOException {
        // make the api call
        if (!actor.equals(LectureSubscriber.TEACHER)) {
            ResponseEntity<Void> response = webClient.post()
                    .uri("notification/date_change"
                            + "/" + this.getNetId()
                            + "/" + lectureId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            verifyStatusCode(response);
            return true;
        }
        return true;
    }

    @Override
    protected boolean time_change(long lectureId, String actor) throws IOException {
        // make the api call
        if (!actor.equals(LectureSubscriber.TEACHER)) {
            ResponseEntity<Void> response = webClient.post()
                    .uri("notification/time_change"
                            + "/" + this.getNetId()
                            + "/" + lectureId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            verifyStatusCode(response);
            return true;
        }
        return true;
    }

    @Override
    protected boolean moved_on_campus(long lectureId, String actor) throws IOException {
        // make the api call
        if (!actor.equals(LectureSubscriber.TEACHER)) {
            ResponseEntity<Void> response = webClient.post()
                    .uri("notification/moved_on_campus"
                            + "/" + this.getNetId()
                            + "/" + lectureId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            verifyStatusCode(response);
            return true;
        }
        return true;
    }


    protected boolean student_assigned_to_course
            (long lectureId, String actor) throws IOException {
        return true;
    }

}
