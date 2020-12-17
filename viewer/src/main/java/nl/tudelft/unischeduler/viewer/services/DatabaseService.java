package nl.tudelft.unischeduler.viewer.services;

import nl.tudelft.unischeduler.viewer.entities.Lecture;
import nl.tudelft.unischeduler.viewer.entities.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class DatabaseService {
    private WebClient.Builder webClientBuilder;

    public DatabaseService() {
        webClientBuilder = WebClient.builder();
        webClientBuilder.baseUrl("http://database-service/");
    }

    public Lecture[] getStudentSchedule(String netId) {
        Lecture[] result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Lecture[].class)
                .block();
        return result;
    }

    public Lecture[] getTeacherSchedule(String netId) {
        Lecture[] result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/teacher/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Lecture[].class)
                .block();
        return result;
    }

    public Lecture[] getPossibleLectures(String netId) {
        Lecture[] result = webClientBuilder.build()
                .get()
                .uri("userCourseService/possibleLectures/" + netId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Lecture[].class)
                .block();
        return result;
    }

    public Lecture[] getLecturesInCourse(int courseId) {
        Lecture[] result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/course/" + String.valueOf(courseId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Lecture[].class)
                .block();
        return result;
    }

    public User[] getStudentsInLecture(int lectureId) {
        User[] result = webClientBuilder.build()
                .get()
                .uri("lectureSchedules/studentsLecture/" + String.valueOf(lectureId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User[].class)
                .block();
        return result;
    }

}
