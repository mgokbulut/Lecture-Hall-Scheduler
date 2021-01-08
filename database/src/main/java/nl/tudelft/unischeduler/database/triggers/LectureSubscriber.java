package nl.tudelft.unischeduler.database.triggers;

import org.bouncycastle.jcajce.provider.symmetric.TEA;
import org.springframework.stereotype.Service;

@Service
public interface LectureSubscriber {

    public static String TEACHER = "caused by a teacher";
    public static String SYSTEM = "caused by the system";
    public static String STUDENT = "caused by a student";

    public static String MOVED_ONLINE = "100 lecture was moved online";
    public static String TIME_CHANGE = "101 lecture was scheduled to another time";
    public static String DATE_CHANGE = "102 lecture was moved to another day";
    public static String MOVED_ON_CAMPUS = "103 lecture was moved on campus";
    public static String STUDENT_ASSIGNED_TO_COURSE = "104 student was added to course";

    /**
     * Update method from the Observer design pattern.
     * Notifies for any number of changes made to a lecture.
     *
     * @param lectureId the id of the concerned lecture
     * @param actions the actions to be notified for
     * @param actor the cause of the update
     * @return whether the operation succeeded
     */
    public boolean update(long lectureId, String[] actions, String actor);

    /**
     * Same method, but for single actions.
     *
     * @param lectureId the id of the concerned lecture
     * @param action the actions to be notified for
     * @param actor the cause of the update
     * @return whether the operation succeeded
     */
    public boolean update(long lectureId, String action, String actor);

}
