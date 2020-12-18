package nl.tudelft.unischeduler.schedulegenerate.GeneratorTests;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import nl.tudelft.unischeduler.schedulegenerate.api.ApiCommunicator;
import nl.tudelft.unischeduler.schedulegenerate.entities.Lecture;
import nl.tudelft.unischeduler.schedulegenerate.entities.Room;
import nl.tudelft.unischeduler.schedulegenerate.generator.Generator;

// a class that creates basic entities, useful for unit testing
public class Util {
    static String roomName = "testRoom";

    static Room makeRoom() {
        return new Room(1, 200, roomName);
    }

    static Timestamp makeBasicStartTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2020);
        c.set(Calendar.MONTH, Calendar.DECEMBER); // december
        c.set(Calendar.DAY_OF_MONTH, 14); // monday 14th
        c.set(Calendar.HOUR_OF_DAY, 9); // 9am
        c.set(Calendar.MINUTE, 45); // 9:45am
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        // full date value is 2020-12-14T09:45:10.430
        return new Timestamp(c.getTimeInMillis());
    }

    static Time makeTimeLength(int n) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        c.add(Calendar.HOUR_OF_DAY, n);
        return new Time(c.getTimeInMillis());
    }

    static Generator makeGenerator() {
        Generator gen = new Generator(new ApiCommunicator());
        return gen;
    }

    static ArrayList<Lecture> createListLectures() {
        Lecture l1 = new Lecture(324324, 0, null,
                makeTimeLength(2), false, 1);
        Lecture l2 = new Lecture(436546, 0, null,
                makeTimeLength(2), false, 1);
        Lecture l3 = new Lecture(4314645, 0, null,
                makeTimeLength(2), false, 2);
        ArrayList<Lecture> a = new ArrayList<>();
        a.add(l1);
        a.add(l2);
        a.add(l3);
        return a;
    }

    static ArrayList<Lecture> createListLecturesScheduled() {
        Timestamp t1 = makeBasicStartTime();
        Timestamp t2 = new Timestamp(t1.getTime() + makeTimeLength(24).getTime());
        Timestamp t3 = new Timestamp(t2.getTime() + makeTimeLength(24).getTime());
        Lecture l1 = new Lecture(543643, 0, t1,
                makeTimeLength(2), false, 1);
        Lecture l2 = new Lecture(432543645, 0, t2,
                makeTimeLength(2), false, 1);
        Lecture l3 = new Lecture(87698, 0, t3,
                makeTimeLength(2), false, 2);
        ArrayList<Lecture> a = new ArrayList<>();
        a.add(l1);
        a.add(l2);
        a.add(l3);
        return a;
    }
}
