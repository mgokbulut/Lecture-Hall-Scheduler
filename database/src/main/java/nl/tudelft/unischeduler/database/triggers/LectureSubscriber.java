package nl.tudelft.unischeduler.database.triggers;

import org.bouncycastle.jcajce.provider.symmetric.TEA;

public interface LectureSubscriber {

    public static String TEACHER = "caused by a teacher";
    public static String SYSTEM = "caused by the system";
    public static String STUDENT = "caused by a student";

    public static String MOVED_ONLINE = "100 lecture was moved online";
    public static String TIME_CHANGE = "101 lecture was scheduled to another time";
    public static String TIME_CHANGE_TEACHER = TIME_CHANGE + TEACHER;
    public static String TIME_CHANGE_SYSTEM = TIME_CHANGE + STUDENT;
    public static String DATE_CHANGE = "1021 lecture was moved to another day";
    public static String DATE_CHANGE_TEACHER = DATE_CHANGE + TEACHER;
    public static String DATE_CHANGE_SYSTEM = DATE_CHANGE + STUDENT;
    public static String MOVED_ON_CAMPUS = "103 lecture was moved on campus";

    /**
     * Update method from the Observer design pattern.
     * Notifies for any number of changes made to a lecture.
     *
     * @param lectureId the id of the concerned lecture
     * @param actions the actions to be notified for
     * @param actor the cause of the update
     * @return whether the operation succeeded
     */
    public boolean update(int lectureId, String[] actions, String actor);

    /**
     * Same method, but for single actions.
     *
     * @param lectureId the id of the concerned lecture
     * @param action the actions to be notified for
     * @param actor the cause of the update
     * @return whether the operation succeeded
     */
    public boolean update(int lectureId, String action, String actor);

}
