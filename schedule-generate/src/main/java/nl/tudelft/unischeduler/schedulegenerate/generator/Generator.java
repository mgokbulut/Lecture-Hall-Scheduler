package nl.tudelft.unischeduler.schedulegenerate.generator;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

import lombok.Data;
import nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator;
import nl.tudelft.unischeduler.schedulegenerate.entities.Course;
import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Room;
import nl.tudelft.unischeduler.schedulegenerate.entities.Student;
import nl.tudelft.unischeduler.schedulegenerate.entities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.tuple.Tuple2;

// These were warnings on getEarliestTime(),
// they signal an anomaly which is part of the way the method
// has to work, therefore we suppress it.
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@Service
@Data
public class Generator {

    private Timestamp currentTime;

    private int numOfDays;

    private static int NUMOFDAYS = 5;

    private static String ONLINE_ROOM_NAME = "online_room";

    private int MAX_ITERATIONS = 2;

    private List<List<Lecture>> timeTable;

    @Autowired
    private final transient ApiCommunicator apiCommunicator;

    /**
     * Constructor for the generator. Takes the next monday as starting point.
     *
     * @param apiCommunicator the communicator
     */
    public Generator(ApiCommunicator apiCommunicator) {

        this.apiCommunicator = apiCommunicator;

        // take next monday, set as class attribute
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(now.getTime());
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            now = Util.nextDay(now);
            cal.setTimeInMillis(now.getTime());
        }
        this.currentTime = now;
        this.numOfDays = NUMOFDAYS;
        this.timeTable = new ArrayList<>();
    }

    /**
     * Generates a full schedule by adding it to the database using API calls.
     *
     * @param currentTime Time at which to start scheduling
     */
    public void scheduleGenerate(Timestamp currentTime) {
        ArrayList<Course> courses = apiCommunicator.getCourses();
        ArrayList<Lecture> lectures = Util.populateLectures(courses, numOfDays,
                apiCommunicator, currentTime);

        // Now we need to know which lectures are on which days in an
        // easy-to-access datastructure.
        this.timeTable = createTimeTable(lectures, currentTime, numOfDays);

        scheduling(lectures,
                apiCommunicator.getRooms(), apiCommunicator.getCourses());
    }

    /**
     * Creates a list with lectures for every day we want to make a schedule for.
     *
     * @param lectures all lectures
     * @param currentTime start time of the generator
     * @param numOfDays how many days we're scheduling for
     * @return list of lists that contain lectures for each day
     */
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
            if (l.getStartTime() != null) {
                int lecDay = Util.calDistance(currentTime, l.getStartTime());
                timeTable.get(lecDay).add(l);
            }
        }
        return timeTable;
    }



    /**
     * Assigns and schedules courses that are not assigned yet.
     * This is tested based on whether they are assigned a room or not.
     *
     * @param lectures list containing every lecture there is
     */
    public void scheduling(ArrayList<Lecture> lectures,
                           ArrayList<Room> rooms, ArrayList<Course> courses) {
        int maxNumberOfYears = 3;
        // and separate them per year
        List<List<Course>> coursesPerYear = new ArrayList<>();
        Util.populateCoursesPerYear(courses, coursesPerYear, maxNumberOfYears);
        int numYears = coursesPerYear.size();
        schedulingYears(numYears, coursesPerYear, rooms);
    }

    /**
     * For n years and a corresponding list of lectures for each year,
     * assigns rooms and students to lectures that are not already scheduled.
     *
     * @param numYears the number of years in the programme
     * @param coursesPerYear for each year, a list of its corresponding courses
     * @param rooms list of all existing rooms
     * @return whether there were any problems
     */
    public boolean schedulingYears(int numYears,
                                   List<List<Course>> coursesPerYear, ArrayList<Room> rooms) {
        for (int i = 0; i < numYears; i++) {
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
                Util.addIfAllowed(it, studentsQueue, courseStudents, apiCommunicator);

                // for each lecture in the course
                ArrayList<Lecture> lecturesCurrentCourse = new ArrayList<>(c.getLectures());
                schedulingLectures(lecturesCurrentCourse, rooms, courseStudents, studentsQueue);
            }
        }
        return true;
    }

    /**
     * Schedules all unscheduled lectures from the list.
     *
     * @param lectures the lectures to consider
     * @param rooms list of all existing rooms
     * @param courseStudents list of all students enrolled in the course
     * @param studentsQueue queue of students waiting to be scheduled
     * @return whether the operation was successful
     */
    public boolean schedulingLectures(ArrayList<Lecture> lectures, ArrayList<Room> rooms,
                                      Set<Student> courseStudents,
                                      PriorityQueue<Student> studentsQueue) {
        Boolean everythingWentWell = true;
        ArrayList<Lecture> lecturesCurrentCourse = lectures;
        for (int k = 0; k < lecturesCurrentCourse.size(); k++) {
            Lecture l = lecturesCurrentCourse.get(k);
            // if the lecture is not assigned in the schedule yet
            if (l.getRoom() == null && !(l.getIsOnline())) {
                // then we schedule it
                boolean scheduled = schedulingLecture(l, rooms, courseStudents, studentsQueue);
                everythingWentWell = everythingWentWell
                        || scheduled;
            }
        }
        return everythingWentWell;
    }

    /**
     * Schedules a single lecture, by finding a room and a time for it,
     * and assigns the appropriate students to it.
     *
     * @param l the lecture to be scheduled
     * @param rooms the list of existing rooms
     * @param courseStudents the set of students enrolled in the lecture's course
     * @param studentsQueue the queue of students waiting to be scheduled
     * @return whether the operation was successful
     */
    public boolean schedulingLecture(Lecture l, ArrayList<Room> rooms,
                                     Set<Student> courseStudents,
                                     PriorityQueue<Student> studentsQueue) {
        // TODO change this to a proper template online room, ask Kuba
        Room onlineRoom = new Room(0, Integer.MAX_VALUE, ONLINE_ROOM_NAME);
        Boolean everythingWentWell = true;
        // then we want to assign it a room
        Room room = findRoom(rooms, l);
        // if no room was found (no space or bug)
        if (room == null) {
            return moveOnline(l, room, onlineRoom, apiCommunicator, courseStudents);
        }
        // if a room was found
        // we assign it
        int capacity = getCapacity(room);
        apiCommunicator.assignRoomToLecture(l, room);
        apiCommunicator.setLectureTime(l, l.getStartTime());

        // we add back the ones that weren't selected
        List<Object> retObjects =
                Util.computeStudentsList(getCapacity(room), MAX_ITERATIONS,
                        studentsQueue, everythingWentWell, l);
        List<Student> studentsToAdd = (List<Student>) retObjects.get(0);
        Set<Student> notSelected = (Set<Student>) retObjects.get(1);

        System.out.println("=============== yahallo! ===============");
         System.out.println(studentsQueue);
        System.out.println("=============== ======== ===============");
        System.out.println("=============== yahallo! ===============");
         System.out.println(studentsToAdd);
        System.out.println("=============== ======== ===============");
        System.out.println("=============== yahallo! ===============");
         System.out.println(notSelected);
        System.out.println("=============== ======== ===============");
        studentsQueue.addAll(notSelected);
        // now we can add the students that were selected
        List<Student> addTheseStudents = new ArrayList<>(studentsToAdd);
        for (int a = 0; a < addTheseStudents.size(); a++) {
            Student s = addTheseStudents.get(a);
            apiCommunicator.assignStudentToLecture(s, l);
            s.setLastTimeOnCampus(l.getStartTime());
            studentsQueue.add(s);
        }
        return everythingWentWell;
    }

    public boolean moveOnline(Lecture l, Room room, Room onlineRoom,
                                  ApiCommunicator apiCom, Set<Student> courseStudents) {
        // then we want to move it online
        // so we set its time, its room, and its isOnline
        l.setStartTime(getEarliestTime(room, l));
        l.setRoom(onlineRoom);
        l.setIsOnline(true);
        apiCom.assignRoomToLecture(l, onlineRoom);
        // and we assign all students to it
        Iterator<Student> its = courseStudents.iterator();
        for (int m = 0; m < courseStudents.size(); m++) {
            apiCom.assignStudentToLecture(its.next(),
                    l);
        }
        return true;
    }

    /**
     * Finds a room with free time.
     *
     * @param rooms the list of available rooms
     * @param lecture the lecture we're trying to place
     * @return a room, if one was found, with free space
     */
    public Room findRoom(ArrayList<Room> rooms,
                          Lecture lecture) {
        // sort in descending order
        System.out.println(rooms);
        Collections.sort(rooms);
        Collections.reverse(rooms);
        System.out.println(rooms);
        Timestamp time = null;
        Room currRoom = null;
        // for each room
        for (int i = 0; i < rooms.size(); i++) {
            currRoom = rooms.get(i);
            // get the earliest time when it is free
            time = getEarliestTime(currRoom, lecture);
            // if there is none, return null
            if (time != null) {
                return Util.assignRoomToLecture(time, lecture, timeTable, currRoom, currentTime);
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
     * @return time when we can schedule the lecture
     */
    public Timestamp getEarliestTime(Room room, Lecture lecture) {
        // this part will need the most testing as it is complex to work with timestamps
        int day = 0;

        // end of the day is at 17:45, courses should not end any further than that
        Timestamp dayStartTime = new Timestamp(currentTime.getTime());
        while (day < numOfDays) {
            Timestamp endOfDay = Util.getEndOfDay(dayStartTime);
            Timestamp nextTime = isFree(dayStartTime, room, lecture, day);
            Timestamp nextTimeWithDuration = Util.addClassDurationAndTime(lecture, nextTime);

            if (!nextTimeWithDuration.after(endOfDay)) {
                return nextTime;
            }

            dayStartTime = Util.nextDay(dayStartTime);
            day++;

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
     * @return earliest time when the room is not busy
     */
    public Timestamp isFree(Timestamp timeslot, Room room,
                             Lecture lecture, int day) {
        List<Lecture> lectures = new ArrayList<>();
        if(timeTable.size() >= day + 1 && day >= 0) {
            lectures = timeTable.get(day);
        }
        long intervalBetweenLectures = getIntervalBetweenLectures();
        Timestamp found = Util.getStartOfDay(timeslot);
        for (int i = 0; i < lectures.size(); i++) {
            Lecture l = lectures.get(i);
            if (!Util.areLecturesConflicting(lecture, l, found,
                    room, intervalBetweenLectures)) {
                found = new Timestamp(l.computeEndTime().getTime()
                        + intervalBetweenLectures);
            }

        }
        return found;
    }

    /**
     * Returns the capacity of a room, following the corona rules regulations.
     *
     * @param room the room to check capacity for
     * @return the number of places for students
     */
    public int getCapacity(Room room) {
        // TODO make an API call here to find out the rule for capacity
        // for now we don't take the rules into account
        return room.getCapacity();
    }

    public long getIntervalBetweenLectures() {
        return apiCommunicator.getIntervalBetweenLectures();
    }


}

