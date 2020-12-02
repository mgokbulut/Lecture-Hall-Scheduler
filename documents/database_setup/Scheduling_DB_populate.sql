INSERT INTO app_user
VALUES ('a.baran@student.tudelft.nl', 'STUDENT', true, '2020-12-01');
INSERT INTO app_user
VALUES ('a.kuba@student.tudelft.nl', 'STUDENT', true, '2020-12-01');
INSERT INTO app_user
VALUES ('a.mert@student.tudelft.nl', 'STUDENT', true, '2020-12-01');
INSERT INTO app_user
VALUES ('a.nathan@student.tudelft.nl', 'STUDENT', true, '2020-12-01');
INSERT INTO app_user
VALUES ('a.jop@student.tudelft.nl', 'STUDENT', true, '2020-12-01');
INSERT INTO app_user
VALUES ('a.louis@student.tudelft.nl', 'STUDENT', false, '2020-12-01');
INSERT INTO app_user
VALUES ('teacher@tudelft.nl', 'TEACHER', true, '2020-12-01');
INSERT INTO app_user
VALUES ('sanders@tudelft.nl', 'TEACHER', false, '2020-12-01');
INSERT INTO app_user
VALUES ('anotherteacher@tudelft.nl', 'TEACHER', true, '2020-12-01');
INSERT INTO app_user
VALUES ('faculty@tudelft.nl', 'FACULTY_MEMBER', false, '2020-12-01');

INSERT INTO schedule
VALUES (0, 'a.baran@student.tudelft.nl');
INSERT INTO schedule
VALUES (1, 'a.kuba@student.tudelft.nl');
INSERT INTO schedule
VALUES (2, 'a.mert@student.tudelft.nl');
INSERT INTO schedule
VALUES (3, 'a.jop@student.tudelft.nl');
INSERT INTO schedule
VALUES (4, 'a.louis@student.tudelft.nl');
INSERT INTO schedule
VALUES (5, 'a.nathan@student.tudelft.nl');
INSERT INTO schedule
VALUES (6, 'teacher@tudelft.nl');
INSERT INTO schedule
VALUES (7, 'anotherteacher@tudelft.nl');
INSERT INTO schedule
VALUES (8, 'sanders@tudelft.nl');

INSERT INTO classroom
VALUES (0, 50, 'Amper Hall', 'EWI', 1);
INSERT INTO classroom
VALUES (1, 100, 'Boole Hall', 'EWI', 2);
INSERT INTO classroom
VALUES (2, 250, 'Pi Hall', 'EWI', 1);
INSERT INTO classroom
VALUES (-1, 0, 'EMPTY', 'EMPTY', 0);
INSERT INTO classroom
VALUES (-2, 0, 'ONLINE', 'ONLINE', 0);

INSERT INTO course
VALUES (0, 'ADS', 1);
INSERT INTO course
VALUES (1, 'SEM', 2);
INSERT INTO course
VALUES (2, 'AD', 2);
INSERT INTO course
VALUES (3, 'ML', 1);
INSERT INTO course
VALUES (4, 'CG', 2);

INSERT INTO lecture
VALUES (0, 0, 0, 'sanders@tudelft.nl', '2020-12-10 09:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (1, 1, 1, 'sanders@tudelft.nl', '2020-12-10 12:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (2, 2, 2, 'sanders@tudelft.nl', '2020-12-10 15:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (3, 1, 3, 'a.baran@student.tudelft.nl', '2020-12-11 10:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (4, 0, 2, 'anotherteacher@tudelft.nl', '2020-12-11 09:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (5, 1, 4, 'anotherteacher@tudelft.nl', '2020-12-11 12:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (7, 2, 3, 'anotherteacher@tudelft.nl', '2020-12-12 15:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (8, 0, 1, 'anotherteacher@tudelft.nl', '2020-12-12 09:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (9, 1, 2, 'anotherteacher@tudelft.nl', '2020-12-12 12:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (10, 2, 3, 'anotherteacher@tudelft.nl', '2020-12-13 15:00:00', '02:00:00', true);
INSERT INTO lecture
VALUES (11, 0,  4, 'anotherteacher@tudelft.nl', '2020-12-13 11:00:00', '02:00:00', false);
INSERT INTO lecture
VALUES (12, 1, 0, 'anotherteacher@tudelft.nl', '2020-12-13 09:00:00', '02:00:00', true);

INSERT INTO user_course
VALUES ('a.baran@student.tudelft.nl', 0);
INSERT INTO user_course
VALUES ('a.baran@student.tudelft.nl', 1);
INSERT INTO user_course
VALUES ('a.baran@student.tudelft.nl', 2);
INSERT INTO user_course
VALUES ('a.kuba@student.tudelft.nl', 3);
INSERT INTO user_course
VALUES ('a.kuba@student.tudelft.nl', 4);
INSERT INTO user_course
VALUES ('a.kuba@student.tudelft.nl', 0);
INSERT INTO user_course
VALUES ('a.mert@student.tudelft.nl', 1);
INSERT INTO user_course
VALUES ('a.mert@student.tudelft.nl', 2);
INSERT INTO user_course
VALUES ('a.mert@student.tudelft.nl', 3);
INSERT INTO user_course
VALUES ('a.nathan@student.tudelft.nl', 4);
INSERT INTO user_course
VALUES ('a.nathan@student.tudelft.nl', 0);
INSERT INTO user_course
VALUES ('a.nathan@student.tudelft.nl', 1);
INSERT INTO user_course
VALUES ('a.jop@student.tudelft.nl', 2);
INSERT INTO user_course
VALUES ('a.jop@student.tudelft.nl', 3);
INSERT INTO user_course
VALUES ('a.jop@student.tudelft.nl', 4);
INSERT INTO user_course
VALUES ('a.louis@student.tudelft.nl', 0);

INSERT INTO lecture_schedule
VALUES(0, 0);
INSERT INTO lecture_schedule
VALUES(1, 1);
INSERT INTO lecture_schedule
VALUES(2, 2);
INSERT INTO lecture_schedule
VALUES(3, 3);
INSERT INTO lecture_schedule
VALUES(4, 4);
INSERT INTO lecture_schedule
VALUES(5, 5);
INSERT INTO lecture_schedule
VALUES(2, 0);
INSERT INTO lecture_schedule
VALUES(7, 1);
INSERT INTO lecture_schedule
VALUES(8, 2);
INSERT INTO lecture_schedule
VALUES(9, 0);
INSERT INTO lecture_schedule
VALUES(10, 1);
INSERT INTO lecture_schedule
VALUES(11, 2);
INSERT INTO lecture_schedule
VALUES(12, 3);
INSERT INTO lecture_schedule
VALUES(0, 4);
INSERT INTO lecture_schedule
VALUES(1, 5);
INSERT INTO lecture_schedule
VALUES(3, 4);
INSERT INTO lecture_schedule
VALUES(3, 5);
INSERT INTO lecture_schedule
VALUES(4, 1);

INSERT INTO lecture_schedule
VALUES(0, 8);
INSERT INTO lecture_schedule
VALUES(1, 8);
INSERT INTO lecture_schedule
VALUES(2, 8);
INSERT INTO lecture_schedule
VALUES(3, 8);
INSERT INTO lecture_schedule
VALUES(5, 7);
INSERT INTO lecture_schedule
VALUES(7, 7);
INSERT INTO lecture_schedule
VALUES(8, 7);
INSERT INTO lecture_schedule
VALUES(9, 7);
INSERT INTO lecture_schedule
VALUES(10, 7);
INSERT INTO lecture_schedule
VALUES(11, 7);
INSERT INTO lecture_schedule
VALUES(12, 7);

