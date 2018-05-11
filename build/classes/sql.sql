create database swat;

use swat;

describe eventstorage;

select * from eventstorage;

#information
DROP TABLE IF EXISTS information;

CREATE TABLE information (
info_id BIGINT PRIMARY KEY AUTO_INCREMENT,
session_id VARCHAR(30),
user_time INT(6),
begin_time INT(4),
end_time INT(4),
begin_lat DOUBLE,
begin_long DOUBLE,
end_lat DOUBLE,
end_long DOUBLE,
region varchar(30) 
);	
#information end

#classification
DROP TABLE IF EXISTS classification;

CREATE TABLE classification(
class_id BIGINT PRIMARY KEY AUTO_INCREMENT,
info_id bigint,
begin_time int,
end_time int,
classification VARCHAR(2),
foreign key (info_id)
   references information(info_id)
   on delete cascade
);

#classification end

#STATES
DROP TABLE IF EXISTS states;

CREATE TABLE states (
region varchar(20) NOT NULL PRIMARY KEY,
states varchar(40) NOT NULL
);
#states end

#latlong
DROP TABLE IF EXISTS latlong;

CREATE TABLE latlong (
latlong_id INT PRIMARY KEY AUTO_INCREMENT,
region varchar(20),
top_lat DOUBLE,
bottom_lat DOUBLE,
left_long DOUBLE,
right_long DOUBLE,
start_date DATE,
end_date DATE,
foreign key (region)
   references states(region)
   on delete cascade
);
#latlong end

#events
DROP TABLE if exists events;

CREATE TABLE events (
event VARCHAR(20) PRIMARY KEY NOT NULL
);

INSERT INTO events values('Tornado');
INSERT INTO events values('Thunderstorm');
INSERT INTO events values('Flashflood');
INSERT INTO events values('Hail');
#events end

#imageevents
DROP TABLE if exists imageevents;

#storm events in the image
CREATE TABLE imageevents (
event_id BIGINT primary KEY AUTO_INCREMENT NOT NULL,
info_id BIGINT,
event varchar(20),
ic INT DEFAULT 0,
cc INT DEFAULT 0,
bl INT DEFAULT 0,
be INT DEFAULT 0,
ts INT DEFAULT 0,
ls INT DEFAULT 0,
ps INT DEFAULT 0,
ns INT DEFAULT 0,
nl INT DEFAULT 0,
mc INT DEFAULT 0,
magnitude double,
foreign key (event)
   references events(event)
   on delete cascade,
foreign key (info_id)
   references information(info_id)
   on delete cascade   
);

#imageevents end



#dummy values
insert into information values(null,'djfkjrflkrf',00001,1000,1300,52.29,-87.12,48.28,-77.23,'cent_plains');
insert into classification values(null,2,1000,1100,'BE');

#dummy values end

#select statements for dummy
select * from information;
SELECT LAST_INSERT_ID();
select * from classification;


select * from events;
select * from classification where info_id=2;









































insert into hail (info_id,ic) values(1,3);

select * from hail;




#OLD TABLES

DROP TABLE IF EXISTS hail;

CREATE TABLE hail (
hail_id BIGINT PRIMARY KEY AUTO_INCREMENT,
info_id BIGINT,
ic INT DEFAULT 0,
cc INT DEFAULT 0,
bl INT DEFAULT 0,
be INT DEFAULT 0,
ts INT DEFAULT 0,
ls INT DEFAULT 0,
ps INT DEFAULT 0,
ns INT DEFAULT 0,
nl INT DEFAULT 0,
mc INT DEFAULT 0,
magnitude double,
foreign key (info_id)
   references information(info_id)
   on delete cascade
);

DROP TABLE IF EXISTS thunderstorm;

CREATE TABLE thunderstorm (
thunderstorm_id BIGINT PRIMARY KEY AUTO_INCREMENT,
info_id BIGINT,
ic INT DEFAULT 0,
cc INT DEFAULT 0,
bl INT DEFAULT 0,
be INT DEFAULT 0,
ts INT DEFAULT 0,
ls INT DEFAULT 0,
ps INT DEFAULT 0,
ns INT DEFAULT 0,
nl INT DEFAULT 0,
mc INT DEFAULT 0,
magnitude double,
foreign key (info_id)
   references information(info_id)
   on delete cascade
);


DROP TABLE IF EXISTS flashflood;

CREATE TABLE flashflood (
flashflood_id BIGINT PRIMARY KEY AUTO_INCREMENT,
info_id BIGINT,
ic INT DEFAULT 0,
cc INT DEFAULT 0,
bl INT DEFAULT 0,
be INT DEFAULT 0,
ts INT DEFAULT 0,
ls INT DEFAULT 0,
ps INT DEFAULT 0,
ns INT DEFAULT 0,
nl INT DEFAULT 0,
mc INT DEFAULT 0,
magnitude double,
foreign key (info_id)
   references information(info_id)
   on delete cascade
);


DROP TABLE IF EXISTS tornado;

CREATE TABLE tornado (
tornado_id BIGINT PRIMARY KEY AUTO_INCREMENT,
info_id BIGINT,
ic INT DEFAULT 0,
cc INT DEFAULT 0,
bl INT DEFAULT 0,
be INT DEFAULT 0,
ts INT DEFAULT 0,
ls INT DEFAULT 0,
ps INT DEFAULT 0,
ns INT DEFAULT 0,
nl INT DEFAULT 0,
mc INT DEFAULT 0,
magnitude double,
foreign key (info_id)
   references information(info_id)
   on delete cascade
);
