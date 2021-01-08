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
    public boolean moved_online(int lectureId, String actor) throws IOException {
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
    public boolean date_change(int lectureId, String actor) throws IOException {
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
    public boolean time_change(int lectureId, String actor) throws IOException {
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
    public boolean moved_on_campus(int lectureId, String actor) throws IOException {
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

}
