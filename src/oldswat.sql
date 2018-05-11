#drop database swat;

#create database swat;

use swat;



#Users Table
drop table if exists users;

create table users (
user_id  bigint not null  primary key auto_increment,
email_id varchar(30) not null unique,
password varchar(64) not null,
name char(30) not null,
authenticated tinyint,
token varchar(100)
);
#Users Table End

# regions table

DROP TABLE IF EXISTS regions;

create table regions (
region varchar(30) NOT NULL PRIMARY KEY,
states text
);


# INSERT STATEMENTS

insert into regions values ('cent_plains','IOWA,NEBRASKA,MISSOURI,KANSAS,OKLAHOMA,ARKANSAS');

insert into regions values ('north_plains','NORTH+DAKOTA,SOUTH+DAKOTA,NEBRASKA,MINNESOTA,IOWA');

insert into regions values ('south_plains','OKLAHOMA,TEXAS,NEW+MEXICO');

insert into regions values ('upper_missvly','IOWA,MISSOURI,ILLINOIS,WISCONSIN,INDIANA,KENTUCKY,MICHIGAN');

insert into regions values ('cent_missvly','IOWA,MISSOURI,ILLINOIS,INDIANA,PENNSYLVANIA,KENTUCKY,WEST+VIRGINIA,TENNESSEE');

insert into regions values ('south_missvly','ARKANSAS,MISSISSIPPI,ALABAMA,GEORGIA,LOUISIANA,TENNESSEE');

insert into regions values ('new_england','MAINE,NEW+HAMPSHIRE,VERMONT,NEW+YORK,DELAWARE,NEW+JERSEY,PENNSYLVANIA,MASSACHUSETTS,RHODE+ISLAND,CONNECTICUT');

insert into regions values ('mid_atlantic','MARYLAND,NEW+JERSEY,OHIO,NORTH+CAROLINA,DELAWARE,VIRGINIA,WEST+VIRGINIA,RHODE+ISLAND,CONNECTICUT,NEW+YORK');

insert into regions values ('southeast','ALABAMA,GEORGIA,SOUTH+CAROLINA,NORTH+CAROLINA,VIRGINIA');



#DELETE FROM regions LIMIT 10;

#alter table regions MODIFY states text; 

#select * from regions;

# regions table end


#Latitude and Longitudes
DROP TABLE IF EXISTS latlong;

create table latlong (
latlong_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
begin_lat double,
end_lat double,
begin_long double,
end_long double,
region varchar(30),
start_date date,
end_date date,
foreign key (region)
	references regions(region)
		on delete cascade
);

#Latitude and Longitudes End





	


# States table

drop table if exists states;

create table states(
state_name VARCHAR(20) NOT NULL PRIMARY KEY,
ncdc_int_id int
);


# Big Insert into states table that maynot be used.

insert into states(state_name,ncdc_int_id) values	('ALABAMA',1),
		('ALASKA',2),		
		('ARIZONA',4),
		('ARKANSAS',5),
		('CALIFORNIA',6),		
		('COLORADO',8),
		('CONNECTICUT',9),
		('DELAWARE',10),
		('DISTRICT+OF+COLUMBIA',11),
		('FLORIDA',12),
		('GEORGIA',13),
		('HAWAII',14),		
		('IDAHO',16),
		('ILLINOIS',17),
		('INDIANA',18),
		('IOWA',19),
		('KANSAS',20),
		('KENTUCKY',21),
		('LOUSIANA',22),
		('MAINE',23),
		('MARYLAND',24),
		('MASSACHUSETTS',25),
		('MICHIGAN',26),
		('MINNESOTA',27),
		('MISSISSIPPI',28),
		('MISSOURI',29),
		('MONTANA',30),
		('NEBRASKA',31),
		('NEVADA',32),
		('NEW+HAMPSHIRE',33),
		('NEW+JERSEY',34),
		('NEW+MEXICO',35),
		('NEW+YORK',36),
		('NORTH+CAROLINA',37),
		('NORTH+DAKOTA',38),
		('OHIO',39),
		('OKLAHOMA',40),
		('OREGON',41),
		('PENNSYLVANIA',42),
		('RHODE+ISLAND',43),
		('SOUTH+CAROLINA',44),
		('SOUTH+DAKOTA',46),
		('TENNESSEE',47),
		('TEXAS',48),
		('UTAH',49),
		('VERMONT',50),
		('VIRGINIA',51),
		('WASHINGTON',53),
		('WEST+VIRGINIA',54),
		('WISCONSIN',55),
		('WYOMING',56);


# Big insert Ends here

		
# States table end

		
#Information Table

drop table if exists information;
		
create table information (
info_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
session_id varchar(40) NOT NULL,
start_time varchar(25),
end_time varchar(25),
user_selected_date text,
region varchar(30),
foreign key(region)
	references regions(region)
		on delete cascade,
foreign key (email_id)
	references users(email_id)
		on delete cascade
);

#Information Table end

#Rectangles Table
DROP TABLE IF EXISTS rectangles;



create table rectangles (
rectangle_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
region varchar(30),
info_id BIGINT,
date date,
centre_img_time varchar(4),
top_lat double,
bottom_lat double,
left_long double,
right_long double,
foreign key (region)
	references regions(region)
		on delete cascade,
foreign key (info_id)
	references information(info_id)
		on delete cascade
);
#Rectangles Table End




		
#Classification Table
drop table if exists classification;

create table classification(
classification_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
info_id BIGINT,
classification text,
start_time varchar(25),
end_time varchar(25),
foreign key(info_id)
	references information(info_id)
		on delete cascade
);

#alter table classification drop column count;

#Classification Table End

#Hail Table


drop table if exists hail;

create table hail (
hail_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
classification_id BIGINT,
info_id BIGINT,
magnitude double,
foreign key (classification_id)
	references classification(classification_id)
		on delete cascade,
foreign key (info_id)
	references information(info_id)
		on delete cascade
);

#Hail Table EnD

#thunderstorm Table
drop table if exists thunderstorm;

create table thunderstorm (
thunderstorm_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
classification_id BIGINT,
info_id BIGINT,
magnitude double,
foreign key(classification_id)
	references classification(classification_id)
		on delete cascade,
foreign key (info_id)
	references information(info_id)
		on delete cascade
);

#thunderstorm Table EnD


#tornado Table
drop table if exists tornado;

create table tornado (
tornado_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
classification_id BIGINT,
info_id BIGINT,
magnitude varchar(4),
foreign key(classification_id)
	references classification(classification_id)
		on delete cascade,
foreign key (info_id)
	references information(info_id)
		on delete cascade
);

#tornado Table EnD

#flashflood Table
drop table if exists flashflood;

create table flashflood (
flashflood_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
classification_id BIGINT,
info_id BIGINT,
foreign key(classification_id)
	references classification(classification_id)
		on delete cascade,
foreign key (info_id)
	references information(info_id)
		on delete cascade
);

#thunderstorm Table EnD


		
		
#Pivots

		
#Hail Table

drop view if exists hail_extended;

create view hail_extended as (
  select
    (select classification from classification,hail where hail.classification_id=classification.classification_id) as classification,
    case when magnitude >= 0.75 AND magnitude < 1 then magnitude end as hailhalf,
    case when magnitude >= 1 AND magnitude < 2 then magnitude end as hailone,
    case when magnitude >= 2 then magnitude end as hailaboveone
  from hail
);

#select * from hail_extended;


drop view if exists hail_extended_Pivot;

create view hail_extended_Pivot as (
  select
    classification,
    count(hailhalf) as hailhalf,
    count(hailone) as hailone,
    count(hailaboveone) as hailaboveone 
  from hail_extended
  group by classification
);


drop view if exists hail_final;

create view hail_final as (
  select 
    classification, 
    coalesce(hailhalf, 0) as hailhalf, 
    coalesce(hailone, 0) as hailone, 
    coalesce(hailaboveone, 0) as hailaboveone
  from hail_extended_Pivot
);




#Hail Table ENd

		
#Thunderstorm Table
#select * from thunderstorm;
drop view if exists thunderstorm_extended;

create view thunderstorm_extended as (
  select
    (select classification from classification,thunderstorm where thunderstorm.classification_id=classification.classification_id) as classification,
    case when magnitude >= 50 AND magnitude < 65 then magnitude end as thunderstorm50to65,
    case when magnitude >= 65 then magnitude end as thunderstorm65
  from thunderstorm
);




drop view if exists thunderstorm_extended_Pivot;

create view thunderstorm_extended_Pivot as (
  select
    classification,
    count(thunderstorm50to65) as thunderstorm50to65,
    count(thunderstorm65) as thunderstorm65
  from thunderstorm_extended
  group by classification
);


drop view if exists thunderstorm_final;

create view thunderstorm_final as (
  select 
    classification, 
    coalesce(thunderstorm50to65, 0) as thunderstorm50to65, 
    coalesce(thunderstorm65, 0) as thunderstorm65
  from thunderstorm_extended_Pivot
);





		
#THunderstorm Table End		

		
#FlashFlood Table
drop view if exists flashfloodview;

create view flashfloodview as(
SELECT (select classification from classification,flashflood where flashflood.classification_id=classification.classification_id) as classification,COUNT(*) AS EventCount
    FROM flashflood,classification
GROUP BY classification);




#FlashFlood Table End

#TOrnado Table

drop view if exists tornado_extended;

create view tornado_extended as (
  select
    (select classification from classification,tornado where tornado.classification_id=classification.classification_id) as classification,
    case when magnitude = 'EF1' then magnitude end as level1,
    case when magnitude = 'EF2' then magnitude end as level2,
    case when magnitude = 'EF3' then magnitude end as level3,
    case when magnitude = 'EF4' then magnitude end as level4,
    case when magnitude = 'EF5' then magnitude end as level5
  from tornado
);



drop view if exists tornado_extended_Pivot;

create view tornado_extended_Pivot as (
  select
    classification,
    count(level1) as level1,
    count(level2) as level2,
    count(level3) as level3,
    count(level4) as level4,
    count(level5) as level5
  from tornado_extended
  group by classification
);


drop view if exists tornado_final;

create view tornado_final as (
  select 
    classification, 
    coalesce(level1, 0) as level1, 
    coalesce(level2, 0) as level2,
    coalesce(level3, 0) as level3,
    coalesce(level4, 0) as level4,
    coalesce(level5, 0) as level5
  from tornado_extended_Pivot
);



#Tornado Table End


#Overall View

drop view if exists overallview;

create view overallview as 
	(select hail_final.classification,
    hailhalf,hailone,hailaboveone,
    thunderstorm50to65,thunderstorm65,
    level1,level2,level3,level4,level5,
	eventcount
	
    from hail_final,thunderstorm_final,tornado_final,flashfloodview  group by hail_final.classification) 
    
    ;

#Overall View End
    

#Pie Chart
drop view if exists piechart;

create view piechart as 
( select 
coalesce(sum(case when classification = 'IC' then count end), 0) as IC,
coalesce(sum(case when classification = 'CC' then count end), 0) as CC,
coalesce(sum(case when classification = 'BL' then count end), 0) as BL,
coalesce(sum(case when classification = 'BE' then count end), 0) as BE,
coalesce(sum(case when classification = 'NS' then count end), 0) as NS,
coalesce(sum(case when classification = 'PS' then count end), 0) as PS,
coalesce(sum(case when classification = 'TS' then count end), 0) as TS,
coalesce(sum(case when classification = 'LS' then count end), 0) as LS,
coalesce(sum(case when classification = 'MC' then count end), 0) as MC,
coalesce(sum(case when classification = 'NL' then count end), 0) as NL from classification);





#Bugs Table
drop table if exists bugs;

create table bugs (
bug_id bigint not null primary key auto_increment,
description text
);


