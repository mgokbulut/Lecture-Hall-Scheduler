package nl.tudelft.unischeduler.rules.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.unischeduler.rules.entities.Lecture;
import nl.tudelft.unischeduler.rules.entities.Room;
import nl.tudelft.unischeduler.rules.entities.Ruleset;
import nl.tudelft.unischeduler.rules.entities.Student;
import nl.tudelft.unischeduler.rules.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
public class RulesModule {

    private int[][] thresholds;
    private long breakTime;
    private int maxDays;

    private Ruleset rules;

    @Autowired
    private DatabaseService databaseService;

    /**
     * Sets the ruleset as the standard rules for the rulesModule.
     *
     * @param rules The new rules to set.
     */
    public void setRules(Ruleset rules) {
        this.rules = rules;
        setBreakTime(rules.getBreakTime());
        setMaxDays(rules.getMaxDays());
        setThresholds(rules.getThresholds());
    }

    /**
     * calculates the capacity of a room, given the
     * government restrictions in place.
     *
     * @param init the maximum capacity of the room
     * @return the allowed capacity of the room
     */
    public int getCapacity(int init) {

        int index = 0;
        while (index < thresholds.length) {

            if (init < thresholds[index][0]) {
                index--;
                break;
            }
            if (index + 1 >= thresholds.length) {
                break;
            }
            index++;
        }

        return (init * thresholds[index][1] / 100);

    }

    public int getCapacityOfRoom(int roomId) {
        Room room = databaseService.getClassroom(roomId);
        return getCapacity(room.getCapacity());
    }

    /**
     * calculates the earliest time another lecture can start after the provided lecture,
     * given the government restrictions in place.
     *
     * @param lecture the prior lecture
     * @return the time at which another lecture can start
     */
    public Timestamp getNextStartTime(Lecture lecture) {

        long endTime = lecture.getStartTime().getTime() + lecture.getDuration().getTime();
        return new Timestamp(endTime + breakTime);

    }


    /**
     * returns whether or not a lecture is at maximum capacity yet.
     *
     * @param lecture they lecture in question
     * @return true if the current number of students is less than the allowed capacity,
     *     false otherwise
     */
    public boolean availableForSignUp(Lecture lecture) {
        int maxStudentsInRoom = getCapacity(lecture.getRoom().getCapacity());
        return lecture.getAttendance() < maxStudentsInRoom;
    }

    /**
     * returns whether two lectures take place at the same time.
     *
     * @param l1 the first lecture
     * @param l2 the second lecture
     * @return true if one lecture starts before the other ends, else false.
     */
    public boolean overlap(Lecture l1, Lecture l2) {
        long start1 = l1.getStartTime().getTime();
        long start2 = l2.getStartTime().getTime();

        return (start1 < start2 && getNextStartTime(l1).getTime() > start2)
                || (start1 > start2 && getNextStartTime(l2).getTime() > start1)
                || (start1 == start2);
    }

    /**
     * Tests to see if any of the lectures on the database overlap with the current one.
     *
     * @param lecture The lecture to test for.
     * @return true if there is space else false.
     */
    public boolean overlap(Lecture lecture) {
        Lecture[] lectures = databaseService.getLectures();

        for (Lecture lectureOnDay : lectures) {
            if (Objects.equals(lecture.getRoom(), lectureOnDay.getRoom())
                    && overlap(lecture, lectureOnDay)) {
                return false;
            }
        }
        return true;
    }

    public boolean canBeScheduled(String studentNetId) {
        Student student = databaseService.getStudent(studentNetId);
        return student.isInterested() && student.isRecovered();
    }

    /**
     * iterates through all lectures to ensure that the social distancing guidelines
     * are followed, if not then that lecture will be cleared
     * (room removed and students deregistered).
     *
     * @param lectures list of all lectures
     * @return whether or not any lectures violated the rules.
     */
    public Lecture[] verifyLectures(Lecture[] lectures) {
        ArrayList<Lecture> ret = new ArrayList<>();
        Arrays.sort(lectures);
        for (int i = 0; i < lectures.length; i++) {
            if (lectures[i].getRoom() == null) {
                continue;
            }

            int attendance = lectures[i].getAttendance();
            boolean overCapacity = getCapacity(lectures[i].getRoom().getCapacity()) < attendance;
            boolean overlapping = (lectures[i].getRoom().equals(lectures[(i + 1)
                    % lectures.length].getRoom())
                    && overlap(lectures[i], lectures[(i + 1) % lectures.length]));
            if (overCapacity
                    || overlapping) {
                ret.add(lectures[i]);
            }
        }
        Lecture[] ret2 = new Lecture[0];
        ret2 = ret.toArray(ret2);
        return ret2;

    }

    /**
     * Verifies if all the lectures currently stored on the database
     * are still adhering to the rules.
     *
     * @return true iff this is still te case.
     */
    public boolean verifyLectures() {

        Lecture[] lectures = databaseService.getLectures();
        Lecture[] toRemove = verifyLectures(lectures);

        for (int i = 0; i < toRemove.length; i++) {
            databaseService.removeLectureFromSchedule(toRemove[i].getId());
            databaseService.removeRoomFromLecture(toRemove[i].getId());
        }
        return toRemove.length == 0;
    }
}
