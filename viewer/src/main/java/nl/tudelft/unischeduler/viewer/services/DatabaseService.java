package nl.tudelft.unischeduler.viewer.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.unischeduler.viewer.entities.Classroom;
import nl.tudelft.unischeduler.viewer.entities.Lecture;
import nl.tudelft.unischeduler.viewer.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class DatabaseService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    protected WebClient webClient;

    public DatabaseService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostConstruct
    public void setUp() {
        webClientBuilder.baseUrl("http://database-service");
        webClient = webClientBuilder.build();
    }

    public ResponseEntity<Lecture[]> getStudentSchedule(String netId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return ResponseEntity.ok(getLectures(result));
    }

    public ResponseEntity<Lecture[]> getTeacherSchedule(String netId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/teacher/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return ResponseEntity.ok(getLectures(result));
    }

    public ResponseEntity<Lecture[]> getPossibleLectures(String netId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("userCourseService/possibleLectures/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return ResponseEntity.ok(getLectures(result));
    }

    public ResponseEntity<Lecture[]> getLecturesInCourse(int courseId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/course/" + String.valueOf(courseId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return ResponseEntity.ok(getLectures(result));
    }

    public ResponseEntity<User[]> getStudentsInLecture(int lectureId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/studentsLecture/" + String.valueOf(lectureId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return ResponseEntity.ok(getUsers(result));
    }

    public User[] getUsers(List<Object[]> result) {
        List<User> ret = new ArrayList<>();

        for(Iterator<Object[]> it = result.iterator(); it.hasNext();) {
            Object[] ob = it.next();
            User user = (User) ob[0];
            ret.add(user);
        }
        return ret.toArray(new User[1]);
    }

    public Lecture[] getLectures(List<Object[]> result) {
        List<Lecture> ret = new ArrayList<>();
        for(Iterator<Object[]> it = result.iterator(); it.hasNext();) {
            Object[] ob = it.next();
            Lecture lect = (Lecture) ob[0];
            lect.setClassroom((Classroom) ob[1]);
            ret.add(lect);
        }
        return ret.toArray(new Lecture[result.size()]);
    }
}
