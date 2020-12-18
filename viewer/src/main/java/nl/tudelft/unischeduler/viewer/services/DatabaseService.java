package nl.tudelft.unischeduler.viewer.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import nl.tudelft.unischeduler.viewer.entities.Classroom;
import nl.tudelft.unischeduler.viewer.entities.Lecture;
import nl.tudelft.unischeduler.viewer.entities.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@AllArgsConstructor
@Service
public class DatabaseService {

    @NonNull private WebClient.Builder webClientBuilder;

    @PostConstruct
    public void setUp() {
        webClientBuilder.baseUrl("http://database-service/");
    }


    public Lecture[] getStudentSchedule(String netId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return getLectures(result);
    }

    public Lecture[] getTeacherSchedule(String netId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/teacher/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return getLectures(result);
    }



    public Lecture[] getPossibleLectures(String netId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("userCourseService/possibleLectures/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return getLectures(result);
    }

    public Lecture[] getLecturesInCourse(int courseId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/course/" + String.valueOf(courseId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return getLectures(result);
    }

    public User[] getStudentsInLecture(int lectureId) {
        List<Object[]> result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/studentsLecture/" + String.valueOf(lectureId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object[].class)
                .collectList()
                .block();

        return getUsers(result);
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
