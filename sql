DROP TABLE IF EXISTS task_status;
CREATE TABLE task_status(
status_id int NOT NULL auto_increment,
status_name varchar(32),
PRIMARY KEY (status_id)) ENGINE=InnoDB;


DROP TABLE IF EXISTS task;
CREATE TABLE task(
task_id int NOT NULL auto_increment,
task_name varchar(128),
task_status_id int,
task_time_start datetime,
task_time_finish datetime,
task_length int,
PRIMARY KEY (task_id),
FOREIGN KEY (task_status_id) REFERENCES task_status (status_id)) ENGINE=InnoDB;

INSERT INTO task_status set status_name='RUNNING';
INSERT INTO task_status set status_name='FINISHED';
INSERT INTO task_status set status_name='WAITING';
INSERT INTO task_status set status_name='ERROR';

	SELECT task_id, task_name, status_name, task_time_start, task_time_finish, task_length from (task join task_status on task_status_id=status_id) order by task_id desc limit 0,10;

INSERT INTO task (task_name, task_status_id, task_time_start, task_time_finish, task_length) values ('task1', 2, now(), now(), 1);

UPDATE task SET task_status_id=1 WHERE task_id=1;
UPDATE task SET task_status_id=2, task_time_finish=now() WHERE task_id=1;
