package nl.tudelft.unischeduler.utilentities;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.unischeduler.user.User;

@Data
@NoArgsConstructor
public class Arguments {

    private LecturePlan lecture;
    private Course course;
    private User user;

    private long courseId;
    private String netId;
    //TODO: ADD EXCEPTIONS FOW WHEN ACCESS TO A NULL ATTRIBUTE IS ATTEMPTED?

}
