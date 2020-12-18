package nl.tudelft.unischeduler.schedulegenerate.generator;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Iterator;
import nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator;
import nl.tudelft.unischeduler.schedulegenerate.entities.Course;
import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Room;
import nl.tudelft.unischeduler.schedulegenerate.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// These were warnings on getEarliestTime(),
// they signal an anomaly which is part of the way the method
// has to work, therefore we suppress it.
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@Service
public class Generator {

    private final transient ApiCommunicator apiCommunicator;

    public Generator(ApiCommunicator apiCommunicator) {
        this.apiCommunicator = apiCommunicator;
    }

    /**
     * Generates a full schedule by adding it to the database using API calls.
     *
     * @param currentTime Time at which to start scheduling
     */
    public void scheduleGenerate(Timestamp currentTime) {
        int range = 10;
        int numOfDays = range; // placeholder
        ArrayList<Course> courses = apiCommunicator.getCourses();
        ArrayList<Lecture> lectures = new ArrayList<>();

        // populate the lectures array
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            ArrayList<Lecture> toAdd =
                apiCommunicator.getLecturesInCourse(course.getId(), currentTime, numOfDays);
            lectures.addAll(toAdd);
        }

        // sort by end time
        Collections.sort(lectures);

        // Now we need to know which lectures are on which days in an
        // easy-to-access datastructure.
        List<List<Lecture>> timeTable = createTimeTable(lectures, currentTime, numOfDays);

        scheduling(lectures, timeTable, currentTime, numOfDays);
    }

    public List<List<Lecture>> createTimeTable(ArrayList<Lecture> lectures,
                                                Timestamp currentTime,
                                                int numOfDays) {

        List<List<Lecture>> timeTable = new ArrayList<>(numOfDays);
        // initialize
        for (int i = 0; i < numOfDays; i++) {
            timeTable.add(new ArrayList<>());
        }
        // distribute the lectures
        for (int i = 0; i < lectures.size(); i++) {
            Lecture l = lectures.get(i);

            // now get which day compared to currentTime, as an int
            if(l.getStartTime() != null) {
                int lecDay = calDistance(currentTime, l.getStartTime());
                timeTable.get(lecDay).add(l);
            }
        }
        return timeTable;
    }

    /**
     * Calculates the distance in days between two dates excluding
     * weekends.
     *
     * @param c1 the timestamp of the first date
     * @param c2 the timestamp of the second date
     * @return the number of days difference between them
     */
    public int calDistance(Timestamp c1, Timestamp c2) {
        int maxIterations = 100; // just to never get stuck in the while loop
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(c1.getTime());

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(c2.getTime());
        int numDays = 0;
        int iteration = 0;
        while (cal1.before(cal2) && iteration < maxIterations) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                numDays = numDays + 1;
            }
            cal1.add(Calendar.DATE, 1);
            iteration++;
        }

        return numDays;
    }

    /**
     * method that increases the timestamp by one day,
     * but only for working days. So if it's friday,
     * the day will be increased to the next monday.
     *
     * @param t the timestamp you want to add a day to
     * @return a new timestamp that is set to one day later
     */
    public Timestamp nextDay(Timestamp t) {
        Calendar cal1 = Calendar.getInstance();

        cal1.setTimeInMillis(t.getTime());

        if (Calendar.FRIDAY == cal1.get(Calendar.DAY_OF_WEEK)) {
            cal1.add(Calendar.DAY_OF_YEAR, 3);
        } else {
            cal1.add(Calendar.DAY_OF_YEAR, 1);
        }
        return new Timestamp(cal1.getTime().getTime());
    }

    /**
     * Assigns and schedules courses that are not assigned yet.
     * This is tested based on whether they are assigned a room or not.
     *
     * @param lectures list containing every lecture there is
     * @param timeTable list of lists containing all lectures, per day
     */
    private void scheduling(ArrayList<Lecture> lectures,
                            List<List<Lecture>> timeTable, Timestamp currentTime,
                            int numOfDays) {
        // TODO change this to a proper template online room, ask Kuba
        Room onlineRoom = new Room(0, Integer.MAX_VALUE, "online_room");
        // this value is placeholder until we find a better solution, should work
        int maxIterationMultiplier = 2;
        // get all the rooms available on campus
        ArrayList<Room> rooms = apiCommunicator.getRooms();

        // get all the courses
        ArrayList<Course> courses = apiCommunicator.getCourses();
        // and separate them per year
        List<List<Course>> coursesPerYear = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            coursesPerYear.get(c.getYear()).add(c);
        }

        // for every uni year
        for (int i = 0; i < coursesPerYear.size(); i++) {
            // for each of its courses
            List<Course> coursesIthYear = coursesPerYear.get(i);
            for (int j = 0; j < coursesIthYear.size(); j++) {
                Course c = coursesIthYear.get(j);
                // get its students
                Set<Student> courseStudents = c.getStudents();
                // sort them per when they were last on campus in a priority queue
                PriorityQueue<Student> studentsQueue = new PriorityQueue<>();
                // add a student to the queue if he's allowed to
                Iterator<Student> it = courseStudents.iterator();
                for (int k = 0; k < courseStudents.size(); k++) {
                    Student s = it.next();
                    if (apiCommunicator.allowedOnCampus(s)) {
                        studentsQueue.add(s);
                    }
                }

                // for each lecture in the course
                ArrayList<Lecture> lecturesCurrentCourse = new ArrayList<Lecture>(c.getLectures());
                for (int k = 0; k < lecturesCurrentCourse.size(); k++) {
                    Lecture l = lecturesCurrentCourse.get(k);
                    // if the lecture is not assigned in the schedule yet
                    if (l.getRoom() == null && !(l.getIsOnline())) {
                        // then we want to assign it a room
                        Room room = findRoom(rooms, currentTime, l, timeTable,
                                currentTime, numOfDays);
                        // if no room was found (no space or bug)
                        if (room == null) {
                            // then we want to move it online
                            // so we set its time, its room, and its isOnline
                            l.setStartTime(getEarliestTime(room, l, timeTable,
                                    currentTime, numOfDays));
                            l.setRoom(onlineRoom);
                            l.setIsOnline(true);
                            apiCommunicator.assignRoomToLecture(l.getId(), onlineRoom.getId());
                            // and we assign all students to it
                            Iterator<Student> its = courseStudents.iterator();
                            for(int m = 0; m < courseStudents.size(); m++) {
                                apiCommunicator.assignStudentToLecture(its.next().getNetId(),
                                        l.getId());
                            }
                            continue;
                        }
                        // if a room was found
                        // we assign it
                        int capacity = getCapacity(room);
                        apiCommunicator.assignRoomToLecture(l.getId(), room.getId());
                        apiCommunicator.setLectureTime(l.getId(), l.getStartTime());

                        // then we want to add students to it
                        // first we have to figure out which students to add, without duplicates
                        Set<Student> studentsToAdd = new HashSet<>();
                        List<Student> notSelected = new ArrayList<>();
                        int iteration = 0;
                        while (studentsToAdd.size() < capacity
                            && iteration < maxIterationMultiplier * capacity) {
                            try {
                                Student prioStudent = studentsQueue.remove();
                                // if student wasn't added already, add him
                                if (!studentsToAdd.contains(prioStudent)) {
                                    studentsToAdd.add(prioStudent);
                                    prioStudent.setLastTimeOnCampus(
                                        new Date(l.getStartTime().getTime()));
                                } else {
                                    notSelected.add(prioStudent);
                                }
                            } catch (Exception e) {
                                System.out.println("there was an error "
                                    + "scheduling students to lectures...");
                                System.out.println(e.toString());
                            }
                            iteration++;
                        }
                        // we add back the ones that weren't selected
                        studentsQueue.addAll(notSelected);

                        // now we can add the students that were selected
                        List<Student> addTheseStudents = new ArrayList<>(studentsToAdd);
                        for (int a = 0; a < addTheseStudents.size(); a++) {
                            Student s = addTheseStudents.get(a);
                            apiCommunicator.assignStudentToLecture(s.getNetId(), l.getId());
                            s.setLastTimeOnCampus(l.getStartTime());
                            studentsQueue.add(s);
                        }
                    }
                }
            }
        }
    }

    private Room findRoom(ArrayList<Room> rooms, Timestamp date,
                          Lecture lecture, List<List<Lecture>> timeTable,
                          Timestamp currentTime, int numOfDays) {
        // sort in descending order
        Collections.sort(rooms);
        Collections.reverse(rooms);
        // for each room
        for (int i = 0; i < rooms.size(); i++) {
            Room currRoom = rooms.get(i);
            // get the earliest time when it is free
            Timestamp time = getEarliestTime(currRoom, lecture, timeTable,
                    currentTime, numOfDays);
            // if there is none, return null
            if (time != null) {
                // otherwise update the relevant values
                lecture.setStartTime(time);
                lecture.setRoom(currRoom);
                int day = calDistance(currentTime, time);
                timeTable.get(day).add(lecture);
                return currRoom;
            }
        }
        return null;
    }

    /**
     * For a single lecture, tells us the earliest time of the day, and
     * the day itself, when the lecture can be scheduled, in a given room.
     *
     * @param room which room we're checking for
     * @param lecture which lecture we would like to add
     * @param timeTable the timetable with lectures listed per day
     * @param currentTime the starting time of the whole scheduling system
     * @return time when we can schedule the lecture
     */
    private Timestamp getEarliestTime(Room room, Lecture lecture,
                                      List<List<Lecture>> timeTable, Timestamp currentTime,
                                      int numOfDays) {
        // this part will need the most testing as it is complex to work with timestamps
        int day = 0;
        Calendar c = Calendar.getInstance();

        // end of the day is at 17:45, courses should not end any further than that
        Timestamp dayStartTime = new Timestamp(currentTime.getTime());
        // TODO use API to get window
        while (day < numOfDays) {
            c.setTimeInMillis(dayStartTime.getTime());
            c.set(Calendar.HOUR_OF_DAY, 17);
            c.set(Calendar.MINUTE, 45);
            Timestamp endOfDay = new Timestamp(c.getTimeInMillis());
            Timestamp nextTime = isFree(dayStartTime, room, lecture,
                                        timeTable, currentTime, day);
            Timestamp nextTimeWithDuration = new Timestamp(nextTime.getTime()
                    + lecture.getDuration().getTime());
            if (nextTimeWithDuration.after(endOfDay)) {
                dayStartTime = nextDay(dayStartTime);
                day++;
                continue;
            } else {
                return nextTime;
            }
        }
        return null;
    }

    /**
     * For a single day and room, tells us the earliest time of the day when
     * the room is not occupied. This returns the value PLUS the inter-lecture
     * interval specified by the Rules module.
     *
     * @param timeslot which day we're checking for
     * @param room which room we're checking for
     * @param lecture which lecture we would like to add
     * @param timeTable the timetable with lectures listed per day
     * @param currentTime the starting time of the whole scheduling system
     * @return earliest time when the room is not busy
     */
    private Timestamp isFree(Timestamp timeslot, Room room,
                             Lecture lecture, List<List<Lecture>> timeTable,
                             Timestamp currentTime, int day) {
        List<Lecture> lectures = timeTable.get(day);
        long intervalBetweenLectures = getIntervalBetweenLectures();

        Timestamp found = new Timestamp(timeslot.getTime());
        for (int i = 0; i < lectures.size(); i++) {
            Lecture l = lectures.get(i);
            if ((overlap(lecture, found, l) && l.getRoom().equals(room))
                || l.getYear() == lecture.getYear()) {
                found = new Timestamp(l.computeEndTime().getTime()
                        + intervalBetweenLectures);
            }
        }
        return found;
    }

    private int getCapacity(Room room) {
        // TODO make an API call here to find out the rule for capacity
        // for now we don't take the rules into account
        return room.getCapacity();
    }

    /**
     * Helper method that tells us if a lecture and its potential
     * start time overlaps with an already-scheduled lecture.
     *
     * @param lecture the lecture we are trying to schedule
     * @param potentialStartTime the start time we want to give to this lecture
     * @param scheduledLecture the already-scheduled lecture we want to check against
     * @return whether there would be overlap should the lecture be scheduled at this time
     */
    private boolean overlap(Lecture lecture,
                            Timestamp potentialStartTime, Lecture scheduledLecture) {
        Timestamp scheduledLectureEndTime = scheduledLecture.computeEndTime();
        long interval = getIntervalBetweenLectures();
        Timestamp schLecEndTiWithInterval = new Timestamp(scheduledLectureEndTime.getTime()
                + interval);
        // if the start time is during the other lecture
        if (potentialStartTime.after(scheduledLecture.getStartTime())
                && potentialStartTime.before(schLecEndTiWithInterval)) {
            return true;
        }
        // if the end time is during the other lecture
        Timestamp potentialEndTime = new Timestamp(potentialStartTime.getTime()
                + lecture.getDuration().getTime());
        if (potentialEndTime.after(scheduledLecture.getStartTime())
                && potentialStartTime.before(schLecEndTiWithInterval)) {
            return true;
        }
        // if the start and end times are before and after the scheduled lecture
        if (potentialStartTime.before(scheduledLecture.getStartTime())
                && potentialEndTime.after(schLecEndTiWithInterval)) {
            return true;
        }
        return false;
    }

    private long getIntervalBetweenLectures() {
        return apiCommunicator.getIntervalBetweenLectures();
    }


}

