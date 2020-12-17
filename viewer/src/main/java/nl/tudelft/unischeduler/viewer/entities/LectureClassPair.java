package nl.tudelft.unischeduler.viewer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.PostConstruct;

@Data
@AllArgsConstructor
public class LectureClassPair {
    @NonNull Classroom classroom;
    @NonNull Lecture lecture;

    @PostConstruct
    void addClassRoom() {
        lecture.setClassroom(classroom);
    }
}
