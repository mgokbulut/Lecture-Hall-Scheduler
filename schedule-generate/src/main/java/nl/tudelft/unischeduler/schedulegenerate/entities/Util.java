package nl.tudelft.unischeduler.schedulegenerate.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator;
import org.apache.commons.lang.mutable.MutableBoolean;

public class Util {

    /**
     * Does nothing as there is nothing to do!
     * This class was not made static because
     * then we can mock it more easily.
     */
    public Util() {
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
     * Returns either an assigned room, or null
     * if the room had no free space for the lecture.
     *
     * @param time the time to schedule it at
     * @param lecture the lecture we want to assign
     * @param timeTable the timetable of lectures per day
     * @param currRoom the room to check for
     * @param currentTime the current time the system is working with
     * @return either an assigned room, or null
     */
    public Room assignRoomToLecture(Timestamp time, Lecture lecture,
                                           List<List<Lecture>> timeTable, Room currRoom,
                                           Timestamp currentTime) {
        if (time == null) {
            return null;
        }
        // otherwise update the relevant values
        lecture.setStartTime(time);
        lecture.setRoom(currRoom);
        int day = calDistance(currentTime, time);
        timeTable.get(day).add(lecture);
        return currRoom;
    }

    /**
     * Adds a student to a candidate list, if and only if the rules module says they
     * are allowed to be on campus.
     *
     * @param studentsQueue queue of students, ordered by last seen on campus
     * @param courseStudents set of students enrolled in the course
     * @return whether the operation succeeded
     */
    public boolean addIfAllowed(PriorityQueue<Student> studentsQueue,
                                Set<Student> courseStudents, ApiCommunicator apiCom) {
        for (Iterator<Student> it = courseStudents.iterator(); it.hasNext();) {
            Student s = it.next();
            if (apiCom.allowedOnCampus(s)) {
                studentsQueue.add(s);
            }
        }
        return true;
    }

    /**
     * Computes the lists of students to be added to the lectures, and
     * the ones that are not selected.
     *
     * @param capacity the capacity of the room
     * @param maxIterations the max number of iterations to go through
     * @param studentsQueue the queue of students waiting to be scheduled
     * @param everythingWentWell whether the operation was successful
     * @param l the lecture in question
     * @return list containing 1. list of students to add and 2. set of students not selected
     */
    public List<Object> computeStudentsList(int capacity,
                                            int maxIterations,
                                            PriorityQueue<Student> studentsQueue,
                                            Boolean everythingWentWell, Lecture l) {
        // first we have to figure out which students to add, without duplicates
        Set<Student> studentsToAdd = new HashSet<>();
        List<Student> notSelected = new ArrayList<>();
        int iteration = 0;
        while (studentsToAdd.size() < capacity
                && iteration < maxIterations * capacity
                && studentsQueue.size() > 0) {
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
                e.printStackTrace();
                everythingWentWell = false;
            }
            iteration++;
        }
        List<Object> li = new ArrayList<>();
        li.add(studentsToAdd);
        li.add(notSelected);
        return li;
    }

    /**
     * Simply adds a class's duration to a timestamp.
     *
     * @param lecture the lecture whose length we want to add
     * @param time the timestamp to add to
     * @return the timestamp + the lecture's duration
     */
    public Timestamp addClassDurationAndTime(Lecture lecture, Timestamp time) {
        return new Timestamp(time.getTime()
                + lecture.getDuration().getTime());
    }

    /**
     * Returns the end of the day, as per university standards.
     * Currently set to 17:45 of the timestamp's day.
     *
     * @param time which day to take the end of
     * @return a timestamp of the same day but at the right hour
     */
    public Timestamp getEndOfDay(Timestamp time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time.getTime());
        c.set(Calendar.HOUR_OF_DAY, 17);
        c.set(Calendar.MINUTE, 45);
        return new Timestamp(c.getTimeInMillis());
    }

    /**
     * Returns the start of the day, as per university standards.
     * Currently set to 09:45 of the timestamp's day.
     *
     * @param time which day to take the end of
     * @return a timestamp of the same day but at the right hour
     */
    public Timestamp getStartOfDay(Timestamp time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time.getTime());
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 45);
        c.set(Calendar.MILLISECOND, 0);
        return new Timestamp(c.getTimeInMillis());
    }

    /**
     * Populates a list of lectures from a list of courses, sorted by end time.
     *
     * @param courses list of all courses
     * @param numOfDays how many days we schedule ahead for
     * @param apiComm the communicator, to get the lectures
     * @param currentTime the start time of the algorithm
     * @return the list of lectures, sorted by endtime
     */
    public ArrayList<Lecture> populateLectures(
            List<Course> courses, int numOfDays,
            ApiCommunicator apiComm, Timestamp currentTime) {
        ArrayList<Lecture> lectures = new ArrayList<>();

        // populate the lectures array
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            ArrayList<Lecture> toAdd =
                    apiComm.getLecturesInCourse(course.getId(), currentTime, numOfDays);
            if (toAdd == null) {
                toAdd = new ArrayList<>();
            }
            lectures.addAll(toAdd);
        }

        // sort by end time
        Collections.sort(lectures);
        return lectures;
    }

    /**
     * Distributes courses into lists for every year of the programme.
     *
     * @param courses the courses to be distributed
     * @param coursesPerYear the list to populate/contain the courses
     * @param maxNumberOfYears the number of years that are possible
     */
    public void populateCoursesPerYear(List<Course> courses,
                                              List<List<Course>> coursesPerYear,
                                              int maxNumberOfYears) {
        for (int i = 0; i < maxNumberOfYears; i++) {
            coursesPerYear.add(new ArrayList<>());
        }
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            coursesPerYear.get(c.getYear() - 1).add(c);
        }
    }

    /**
     * Tells us if we can schedule the lecture at the given time.
     *
     * @param lecture1 lecture to be scheduled
     * @param lecture2 lecture already scheduled (or not)
     * @param time time at which lecture1 could be scheduled
     * @param room room for which we are checking
     * @param intervalBetweenLectures standard interval in minutes between lectures
     * @return whether lecture1 can be scheduled at the given time without conflict
     */
    public boolean areLecturesConflicting(Lecture lecture1, Lecture lecture2,
                                                 Timestamp time, Room room,
                                                 long intervalBetweenLectures) {
        return !(overlap(lecture1, intervalBetweenLectures, time, lecture2)
                && (lecture2.getRoom().equals(room)
                || lecture2.getYear() == lecture1.getYear()));
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
     * Helper method that tells us if a lecture and its potential
     * start time overlaps with an already-scheduled lecture.
     *
     * @param lecture the lecture we are trying to schedule
     * @param potentialStartTime the start time we want to give to this lecture
     * @param scheduledLecture the already-scheduled lecture we want to check against
     * @return whether there would be overlap should the lecture be scheduled at this time
     */
    public boolean overlap(Lecture lecture, long interval,
                                  Timestamp potentialStartTime, Lecture scheduledLecture) {
        Timestamp scheduledLectureEndTime = scheduledLecture.computeEndTime();
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


}
