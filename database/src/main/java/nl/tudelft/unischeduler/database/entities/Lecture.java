package nl.tudelft.unischeduler.database.entities;


import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "lecture", schema = "schedulingDB")
public class Lecture {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(name = "classroom_id")
//    private Classroom classroom;
//
//    @Column(name = "course_id")
//    private Course course;
//
//    @Column(name = "teacher")
//    private User teacher;

    @Column(name = "start_time_date", nullable = false)
    private Timestamp startTimeDate;

    @Column(name = "duration", nullable = false)
    private Time duration;

    @Column(name = "moved_online", nullable = false)
    private boolean movedOnline;
}
