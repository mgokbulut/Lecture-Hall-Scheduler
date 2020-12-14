-- Table: app_user
CREATE TABLE app_user (
    net_id varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    interested bool NOT NULL,
    last_time_on_campus date NOT NULL,
    CONSTRAINT app_user_pk PRIMARY KEY (net_id)
);

-- Table: classroom
CREATE TABLE classroom (
    id int NOT NULL,
    full_capacity int NOT NULL,
    name varchar(255) NOT NULL,
    building_name varchar(255) NOT NULL,
    floor int NOT NULL,
    CONSTRAINT classroom_pk PRIMARY KEY (id)
);

-- Table: course
CREATE TABLE course (
    id int NOT NULL,
    name varchar(255) NOT NULL,
    year int NOT NULL,
    CONSTRAINT course_pk PRIMARY KEY (id)
);

-- Table: lecture
CREATE TABLE lecture (
    id int NOT NULL,
    classroom_id int NOT NULL,
    course_id int NOT NULL,
    teacher varchar(255) NOT NULL,
    start_time_date timestamp NOT NULL,
    duration time NOT NULL,
    moved_online bool NOT NULL,
    CONSTRAINT lecture_pk PRIMARY KEY (id)
);

-- Table: lecture_schedule
CREATE TABLE lecture_schedule (
    lecture_id int NOT NULL,
    schedule_id int NOT NULL,
    CONSTRAINT lecture_schedule_pk PRIMARY KEY (lecture_id,schedule_id)
);

-- Table: schedule
CREATE TABLE schedule (
    id int NOT NULL,
    app_user varchar(255) NOT NULL,
    CONSTRAINT schedule_pk PRIMARY KEY (id)
);

-- Table: user_course
CREATE TABLE user_course (
    user_id varchar(255) NOT NULL,
    course_id int NOT NULL,
    CONSTRAINT user_course_pk PRIMARY KEY (user_id,course_id)
);

-- foreign keys
-- Reference: Classroom_Lecture (table: lecture)
ALTER TABLE lecture ADD CONSTRAINT Classroom_Lecture FOREIGN KEY Classroom_Lecture (classroom_id)
    REFERENCES classroom (id);

-- Reference: Course_Lecture (table: lecture)
ALTER TABLE lecture ADD CONSTRAINT Course_Lecture FOREIGN KEY Course_Lecture (course_id)
    REFERENCES course (id);

-- Reference: LectureSchedule_Lecture (table: lecture_schedule)
ALTER TABLE lecture_schedule ADD CONSTRAINT LectureSchedule_Lecture FOREIGN KEY LectureSchedule_Lecture (lecture_id)
    REFERENCES lecture (id);

-- Reference: LectureSchedule_Schedule (table: lecture_schedule)
ALTER TABLE lecture_schedule ADD CONSTRAINT LectureSchedule_Schedule FOREIGN KEY LectureSchedule_Schedule (schedule_id)
    REFERENCES schedule (id);

-- Reference: Lecture_User (table: lecture)
ALTER TABLE lecture ADD CONSTRAINT Lecture_User FOREIGN KEY Lecture_User (teacher)
    REFERENCES app_user (net_id);

-- Reference: UserCourse_AppUser (table: user_course)
ALTER TABLE user_course ADD CONSTRAINT UserCourse_AppUser FOREIGN KEY UserCourse_AppUser (user_id)
    REFERENCES app_user (net_id);

-- Reference: UserCourse_Course (table: user_course)
ALTER TABLE user_course ADD CONSTRAINT UserCourse_Course FOREIGN KEY UserCourse_Course (course_id)
    REFERENCES course (id);

-- Reference: User_Schedule (table: schedule)
ALTER TABLE schedule ADD CONSTRAINT User_Schedule FOREIGN KEY User_Schedule (app_user)
    REFERENCES app_user (net_id);
