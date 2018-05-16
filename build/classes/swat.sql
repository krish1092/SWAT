#drop database swat;

#create database swat;

use swat;



#Users Table

drop table if exists users;

create table users (
user_id  bigint not null  primary key auto_increment,
email_address varchar(30) not null unique,
password varchar(64) not null,
name char(30) not null,
authenticated tinyint,
expert tinyint
);

#Users Table End

#User Authentication table

drop table if exists user_auth;

create table user_auth (
user_auth_id bigint not null primary key auto_increment,
email_address varchar(30),
token varchar(100),
auth_purpose char(10),
foreign key (email_address)
	references users(email_address)
		on delete cascade
);

#User Authentication table end


#Result access table

drop table if exists result_access;

create table result_access (
result_access_id int not null primary key auto_increment,
email_address varchar(30),
foreign key (email_address)
	references users(email_address)
		on delete cascade
);








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

#regions table end


#Latitude and Longitudes

# This table is for the reference latitudes and longitudes - Not to be confused with the latitudes and longitudes of the rectangle boxes in information table
DROP TABLE IF EXISTS latlong;

create table latlong (
latlong_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
region_south_lat double,
region_north_lat double,
region_west_long double,
region_east_long double,
region varchar(30),
start_date datetime,
end_date datetime,
foreign key (region)
	references regions(region)
		on delete cascade
);

#Latitude and Longitudes End


#Insertion of values into latlong
insert into  latlong(region_south_lat, region_north_lat, region_west_long, region_east_long, region,start_date ,end_date)
values (33.039404,43.4398685,-106.8418,-89.104845, 'cent_plains', '2015-05-25 23:30:00','2015-07-11 00:00:00' )




#Information Table

drop table if exists information;
		
create table information (
info_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
session_id varchar(33) NOT NULL,
email_address varchar(30),
start_time datetime,
end_time datetime,
rect_west_long double,
rect_east_long double,
rect_south_lat double,
rect_north_lat double,
centre_img_time datetime,
region varchar(30),
foreign key(region)
	references regions(region)
		on delete cascade,
foreign key (email_address)
	references users(email_address)
		on delete cascade
);

#Information Table end

		
#Classification Table
drop table if exists classification;

create table classification(
classification_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
info_id BIGINT,
classification char(2),
count int,
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
state varchar(2),
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
state varchar(2),
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
state varchar(2),
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
state varchar(2),
foreign key(classification_id)
	references classification(classification_id)
		on delete cascade,
foreign key (info_id)
	references information(info_id)
		on delete cascade
);

#flashflood Table EnD


		
#Bugs Table
drop table if exists bugs;

create table bugs (
bug_id bigint not null primary key auto_increment,
description text
);
#Bugs Table End


#Null Table
drop table if exists null_events;

create table null_events(
null_events_id bigint not null primary key auto_increment,
classification_id bigint,
info_id bigint,
foreign key(classification_id)
	references classification(classification_id)
		on delete cascade,
foreign key (info_id)
	references information(info_id)
		on delete cascade
);

#Null Table End


#Users Unmodified classification

create table unmodified_user_classification(
unmodified_user_classification BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
info_id BIGINT,
classification char(2),
date_time datetime,
email_address varchar(30) not null unique,
foreign key(info_id)
	references information(info_id)
		on delete cascade,
foreign key(email_address)
	references users(email_address)
		on delete cascade

)

#Experts Classification
create table expert_classification(
expert_classification_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
classification char(2),
date_time datetime,
region varchar(30),
email_address varchar(30) not null ,
foreign key(email_address)
	references users(email_address)
		on delete cascade
)































#Main Query




select t1_morphology.classification, t1_morphology.classification_count, 
	coalesce(t2_hail.hail_below_one,0) as 'hail < 1', 
	coalesce(t2_hail.hail_above_one,0) as '1 <= hail < 2', coalesce(t2_hail.hail_above_two,0) as 'hail >= 2',
	coalesce(t3_thunderstorm_wind.thunderstorm_wind_below_65,0) as 'thunderstorm_wind < 65',
	coalesce(t3_thunderstorm_wind.thunderstorm_wind_above_65,0) as 'thunderstorm_wind >= 65',
	coalesce(t4_flashflood.flashflood_count, 0) as flashflood_count,
	coalesce(t5_tornado.EF0, 0) as EF0,
	coalesce(t5_tornado.EF1, 0) as EF1,
	coalesce(t5_tornado.EF2, 0) as EF2,
	coalesce(t5_tornado.EF3, 0) as EF3,
	coalesce(t5_tornado.EF4, 0) as EF4,
	coalesce(t5_tornado.EF5, 0) as EF5,
	coalesce(t6_null_events.null_classification_count ,0) as null_classification_count
from 
	(
		
	select 
		count(classification.classification) as classification_count, classification.classification from classification 
		where 
		(
			classification.classification_id in (select hail.classification_id from hail where hail.state = 'KS' )
			or
			classification.classification_id in (select thunderstorm.classification_id from thunderstorm where thunderstorm.state = 'KS') 
			or
			classification.classification_id in (select flashflood.classification_id from flashflood where flashflood.state = 'KS')
			or
			classification.classification_id in (select tornado.classification_id from tornado where tornado.state = 'KS')
		) 
		
		and
		classification.info_id in 
		(
			select information.info_id from information where date(information.start_time) <= '2015-06-23 ' and date(information.end_time) >= '2015-06-23 '
			select information.info_id from information where monthname(information.start_time) = 'june'
	
		) 
		
		group by classification.classification
	
	) t1_morphology
left outer join 
(
	select 
	classification.classification,
	count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
	count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
	count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two
	from classification,hail 
	where hail.classification_id = classification.classification_id and hail.state = 'KS'	
	group by classification.classification
) t2_hail
	on t2_hail.classification = t1_morphology.classification
left outer join
(
	select
	classification.classification,
	count(case when thunderstorm.magnitude < 65 then thunderstorm.magnitude end) as thunderstorm_wind_below_65,
	count(case when thunderstorm.magnitude >= 65 then thunderstorm.magnitude end) as thunderstorm_wind_above_65
	from thunderstorm,classification
	where thunderstorm.classification_id = classification. classification_id and thunderstorm.state = 'KS'	
	group by classification.classification
) t3_thunderstorm_wind
on t3_thunderstorm_wind.classification = t1_morphology.classification
left outer join
(
	select 
	classification.classification,
	count(flashflood_id) as flashflood_count
	from flashflood,classification
	where flashflood.classification_id = classification.classification_id  and flashflood.state = 'KS'
	group by classification.classification	
) t4_flashflood
on t4_flashflood.classification = t1_morphology.classification
left outer join
(
	select 
	classification.classification,
	count(case when magnitude = 'EF0' then magnitude end) as EF0,
	count(case when magnitude = 'EF1' then magnitude end) as EF1,
    count(case when magnitude = 'EF2' then magnitude end) as EF2,
    count(case when magnitude = 'EF3' then magnitude end) as EF3,
    count(case when magnitude = 'EF4' then magnitude end) as EF4,
    count(case when magnitude = 'EF5' then magnitude end) as EF5
	from tornado,classification
	where tornado.classification_id = classification.classification_id and tornado.state = 'KS'	
	group by classification.classification 
) t5_tornado
on t5_tornado.classification = t1_morphology.classification
left outer join 
(
	 select
	 classification.classification,
	 count(classification.classification) as null_classification_count
	 from classification,null_events 
	 where null_events.classification_id = classification.classification_id  and null_events.state = 'KS'
	 group by classification.classification
) t6_null_events
on t6_null_events.classification = t1_morphology.classification;





#-------------------------------------------Rough Work




select information.info_id from information where date(information.start_time) <= '2015-06-23 ' and date(information.end_time) >= '2015-06-23 ' 

and monthname(information.start_time) = 'june'







select count(classification) as classification_count,classification 
from classification 
where 
	classification_id not in (select classification_id from hail) 
	and 
	classification_id not in (select classification_id from tornado) 
	and 
	classification_id not in (select classification_id from thunderstorm) 
	and 
	classification_id not in (select classification_id from flashflood) 
	and
	info_id in (select information.info_id from information where date(information.start_time) >= '2015-06-21' and date(information.end_time) <= '2015-06-30')
group by classification;


 








# Final Results Page: New QUeries


#Final Working Query

#Hail and All morphologies

select t2_morphology.classification, t2_morphology.classification_count, t1_hail.hail_below_one, t1_hail.hail_above_one, t1_hail.hail_above_two from 
(
	select 
	classification.classification,
	count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
	count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
	count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two,
	from classification,hail 
	where hail.classification_id = classification.classification_id
	group by classification.classification
) t1_hail
left join 
	(select count(classification.classification) as classification_count,classification.classification from classification group by classification) t2_morphology
	on t1_hail.classification = t2_morphology.classification;


	
#Thunderstorm Wind and all morphologies

select 
	t2_morphology.classification, t2_morphology.classification_count, 
	coalesce(t3_thunderstorm_wind.thunderstorm_wind_below_65,0) as thunderstorm_wind_below_65,
	coalesce(t3_thunderstorm_wind.thunderstorm_wind_above_65,0) as thunderstorm_wind_above_65
	from 
(
	select
	classification.classification,
	count(case when thunderstorm.magnitude < 65 then thunderstorm.magnitude end) as thunderstorm_wind_below_65,
	count(case when thunderstorm.magnitude >= 65 then thunderstorm.magnitude end) as thunderstorm_wind_above_65
	from thunderstorm,classification
	where thunderstorm.classification_id = classification. classification_id
	group by classification.classification
) t3_thunderstorm_wind
left join 
	(select count(classification.classification) as classification_count,classification.classification from classification group by classification) t2_morphology
	on t3_thunderstorm_wind.classification = t2_morphology.classification;


#Flashflood And all morphologies

select t2_morphology.classification, t2_morphology.classification_count, coalesce(t4_flashflood.flashflood_count, 0) as flashflood_count from 
(
	select 
	classification.classification,
	count(flashflood_id) as flashflood_count
	from flashflood,classification
	where flashflood.classification_id = classification.classification_id
	group by classification.classification
) t4_flashflood
left join 
	(select count(classification.classification) as classification_count,classification.classification from classification group by classification) t2_morphology
	on t4_flashflood.classification = t2_morphology.classification;


#Tornado and All Morphologies


select t2_morphology.classification, t2_morphology.classification_count, 
	coalesce(t5_tornado.EF0, 0) as EF0,
	coalesce(t5_tornado.EF1, 0) as EF1,
	coalesce(t5_tornado.EF2, 0) as EF2,
	coalesce(t5_tornado.EF3, 0) as EF3,
	coalesce(t5_tornado.EF4, 0) as EF4,
	coalesce(t5_tornado.EF5, 0) as EF5
	from
(
	select 
	classification.classification,
	count(case when magnitude = 'EF0' then magnitude end) as EF0,
	count(case when magnitude = 'EF1' then magnitude end) as EF1,
    count(case when magnitude = 'EF2' then magnitude end) as EF2,
    count(case when magnitude = 'EF3' then magnitude end) as EF3,
    count(case when magnitude = 'EF4' then magnitude end) as EF4,
    count(case when magnitude = 'EF5' then magnitude end) as EF5
	from tornado,classification
	where tornado.classification_id = classification.classification_id
	group by classification.classification
) t5_tornado
left join 
	(select count(classification.classification) as classification_count,classification.classification from classification group by classification) t2_morphology
	on t5_tornado.classification = t2_morphology.classification;


	
	
#











#With state alone

select t2_morphology.classification, t2_morphology.classification_count, t1_hail.hail_below_one, t1_hail.hail_above_one, t1_hail.hail_above_two from 
(
	select 
	classification.classification,
	count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
	count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
	count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two
	from classification,hail 
	where hail.classification_id = classification.classification_id and hail.state = 'IA'	
	group by classification.classification
) t1_hail
left join 
	(select count(classification.classification) as classification_count,classification.classification from classification group by classification) t2_morphology
	on t1_hail.classification = t2_morphology.classification;


	
	
#With date and state;	
	
#Working!!!!!!!!!!!!
select t2_morphology.classification, t2_morphology.classification_count, t1_hail.hail_below_one, t1_hail.hail_above_one, t1_hail.hail_above_two from 
(
	select 
	classification.classification,
	count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
	count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
	count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two
	from classification,hail 
	where hail.classification_id = classification.classification_id and hail.state = 'KS'	
	
	and
	
	classification.info_id in (
	select information.info_id from information where information.region = 'cent_plains' 
	)
	
	group by classification.classification
) t1_hail
left join 
	(select count(classification.classification) as classification_count,classification.classification from classification group by classification) t2_morphology
	on t1_hail.classification = t2_morphology.classification;


	
	
	
	
	#Overall Null
	
	select count(classification.classification) as classification_count,classification from classification,null_events 
	where null_events.classification_id = classification.classification_id
	group by classification.classification;
	
	
	
	
	
	
	
	
	
	
	
	
	#Newer Attempt
	
	
	
#Maintenance nightmare!

select t1_morphology.classification, t1_morphology.classification_count, 
	coalesce(t2_hail.hail_below_one,0) as 'hail < 1', 
	coalesce(t2_hail.hail_above_one,0) as '1 <= hail < 2', coalesce(t2_hail.hail_above_two,0) as 'hail >= 2',
	coalesce(t3_thunderstorm_wind.thunderstorm_wind_below_65,0) as 'thunderstorm_wind < 65',
	coalesce(t3_thunderstorm_wind.thunderstorm_wind_above_65,0) as 'thunderstorm_wind >= 65',
	coalesce(t4_flashflood.flashflood_count, 0) as flashflood_count,
	coalesce(t5_tornado.EF0, 0) as EF0,
	coalesce(t5_tornado.EF1, 0) as EF1,
	coalesce(t5_tornado.EF2, 0) as EF2,
	coalesce(t5_tornado.EF3, 0) as EF3,
	coalesce(t5_tornado.EF4, 0) as EF4,
	coalesce(t5_tornado.EF5, 0) as EF5,
	coalesce(t6_null_events.null_classification_count ,0) as null_classification_count
from 
	(select count(classification.classification) as classification_count,classification.classification from classification group by classification) t1_morphology
left outer join 
(
	select 
	classification.classification,
	count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
	count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
	count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two
	from classification,hail 
	where hail.classification_id = classification.classification_id 
	group by classification.classification
) t2_hail
	on t2_hail.classification = t1_morphology.classification
left outer join
(
	select
	classification.classification,
	count(case when thunderstorm.magnitude < 65 then thunderstorm.magnitude end) as thunderstorm_wind_below_65,
	count(case when thunderstorm.magnitude >= 65 then thunderstorm.magnitude end) as thunderstorm_wind_above_65
	from thunderstorm,classification
	where thunderstorm.classification_id = classification. classification_id
	group by classification.classification
) t3_thunderstorm_wind
on t3_thunderstorm_wind.classification = t1_morphology.classification
left outer join
(
	select 
	classification.classification,
	count(flashflood_id) as flashflood_count
	from flashflood,classification
	where flashflood.classification_id = classification.classification_id
	group by classification.classification
) t4_flashflood
on t4_flashflood.classification = t1_morphology.classification
left outer join
(
	select 
	classification.classification,
	count(case when magnitude = 'EF0' then magnitude end) as EF0,
	count(case when magnitude = 'EF1' then magnitude end) as EF1,
    count(case when magnitude = 'EF2' then magnitude end) as EF2,
    count(case when magnitude = 'EF3' then magnitude end) as EF3,
    count(case when magnitude = 'EF4' then magnitude end) as EF4,
    count(case when magnitude = 'EF5' then magnitude end) as EF5
	from tornado,classification
	where tornado.classification_id = classification.classification_id
	group by classification.classification
) t5_tornado
on t5_tornado.classification = t1_morphology.classification
left outer join 
(
	 select
	 classification.classification,
	 count(classification.classification) as null_classification_count
	 from classification,null_events 
	 where null_events.classification_id = classification.classification_id
	 group by classification.classification
) t6_null_events
on t6_null_events.classification = t1_morphology.classification;







select information.info_id from information where date(information.start_time) <= '2015/06/23 ' and date(information.end_time) >= '2015/06/23 '





count(classification.classification) as classification_count, classification.classification



select count(classification.classification) as classification_count, classification.classification from classification 
where (classification.classification_id in (select hail.classification_id from hail where hail.state = 'IA' )
or
classification.classification_id in (select thunderstorm.classification_id from thunderstorm where thunderstorm.state = 'IA') 
or
classification.classification_id in (select flashflood.classification_id from flashflood where flashflood.state = 'IA')
or
classification.classification_id in (select tornado.classification_id from tornado where tornado.state = 'IA')
or
classification.classification_id in (select null_events.classification_id from null_events where null_events.state = '')
) and classification = 'CC';






select count(classification.classification) as classification_count, classification.classification from classification 
where 
classification.classification_id in (select null_events.classification_id from null_events) and 
(
classification.classification_id  not in (select hail.classification_id from hail )
or
classification.classification_id not in (select thunderstorm.classification_id from thunderstorm ) 
or
classification.classification_id not in (select flashflood.classification_id from flashflood )
or
classification.classification_id not in (select tornado.classification_id from tornado )
);



null events correct!j
select count(classification.classification) as classification_count, classification.classification from classification 
where 
classification.classification_id in 
(
	select null_events.classification_id from null_events
	where 
	null_events.classification_id not in (select hail.classification_id from hail )
	and
	null_events.classification_id not in (select thunderstorm.classification_id from thunderstorm ) 
	and
	null_events.classification_id not in (select flashflood.classification_id from flashflood )
	and
	null_events.classification_id not in (select tornado.classification_id from tornado )

);











select * from classification 
where (classification.classification_id NOT in (select hail.classification_id from hail)
or
classification.classification_id NOT in (select thunderstorm.classification_id from thunderstorm) 
or
classification.classification_id NOT in (select flashflood.classification_id from flashflood)
or
classification.classification_id NOT in (select tornado.classification_id from tornado)
) and classification = 'CC';





select * from classification 
where (classification.classification_id  in (select hail.classification_id from hail)
or
classification.classification_id  in (select thunderstorm.classification_id from thunderstorm) 
or
classification.classification_id in (select flashflood.classification_id from flashflood)
or
classification.classification_id in (select tornado.classification_id from tornado)
) and classification = 'CC';





































#Nightmare with states ;


select t1_morphology.classification, t1_morphology.classification_count, 
	coalesce(t2_hail.hail_below_one,0) as 'hail < 1', 
	coalesce(t2_hail.hail_above_one,0) as '1 <= hail < 2', coalesce(t2_hail.hail_above_two,0) as 'hail >= 2',
	coalesce(t3_thunderstorm_wind.thunderstorm_wind_below_65,0) as 'thunderstorm_wind < 65',
	coalesce(t3_thunderstorm_wind.thunderstorm_wind_above_65,0) as 'thunderstorm_wind >= 65',
	coalesce(t4_flashflood.flashflood_count, 0) as flashflood_count,
	coalesce(t5_tornado.EF0, 0) as EF0,
	coalesce(t5_tornado.EF1, 0) as EF1,
	coalesce(t5_tornado.EF2, 0) as EF2,
	coalesce(t5_tornado.EF3, 0) as EF3,
	coalesce(t5_tornado.EF4, 0) as EF4,
	coalesce(t5_tornado.EF5, 0) as EF5,
	coalesce(t6_null_events.null_classification_count ,0) as null_classification_count
from 
	(select count(classification.classification) as classification_count,classification.classification from classification group by classification) t1_morphology
left outer join 
(
	select 
	classification.classification,
	count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
	count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
	count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two
	from classification,hail 
	where hail.classification_id = classification.classification_id and hail.state = 'KS'	
	group by classification.classification
) t2_hail
	on t2_hail.classification = t1_morphology.classification
left outer join
(
	select
	classification.classification,
	count(case when thunderstorm.magnitude < 65 then thunderstorm.magnitude end) as thunderstorm_wind_below_65,
	count(case when thunderstorm.magnitude >= 65 then thunderstorm.magnitude end) as thunderstorm_wind_above_65
	from thunderstorm,classification
	where thunderstorm.classification_id = classification. classification_id and thunderstorm.state = 'KS'	
	group by classification.classification
) t3_thunderstorm_wind
on t3_thunderstorm_wind.classification = t1_morphology.classification
left outer join
(
	select 
	classification.classification,
	count(flashflood_id) as flashflood_count
	from flashflood,classification
	where flashflood.classification_id = classification.classification_id  and flashflood.state = 'KS'
	group by classification.classification	
) t4_flashflood
on t4_flashflood.classification = t1_morphology.classification
left outer join
(
	select 
	classification.classification,
	count(case when magnitude = 'EF0' then magnitude end) as EF0,
	count(case when magnitude = 'EF1' then magnitude end) as EF1,
    count(case when magnitude = 'EF2' then magnitude end) as EF2,
    count(case when magnitude = 'EF3' then magnitude end) as EF3,
    count(case when magnitude = 'EF4' then magnitude end) as EF4,
    count(case when magnitude = 'EF5' then magnitude end) as EF5
	from tornado,classification
	where tornado.classification_id = classification.classification_id and tornado.state = 'KS'	
	group by classification.classification 
) t5_tornado
on t5_tornado.classification = t1_morphology.classification
left outer join 
(
	 select
	 classification.classification,
	 count(classification.classification) as null_classification_count
	 from classification,null_events 
	 where null_events.classification_id = classification.classification_id  and null_events.state = 'KS'
	 group by classification.classification
) t6_null_events
on t6_null_events.classification = t1_morphology.classification;

















select count(classification.classification) as classification_count,classification from classification,null_events 
	where null_events.classification_id = classification.classification_id
	group by classification.classification;

	
	
	
	














	
	
	
	
	
	
	
	
	
	
	
	select t2_morphology.classification, t2_morphology.classification_count, t1_hail.hail_below_one, t1_hail.hail_above_one, t1_hail.hail_above_two from 
(
	select 
	classification.classification,
	count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
	count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
	count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two
	from classification,hail 
	where hail.classification_id = classification.classification_id and (hail.state = 'KS' or hail.state = 'IA')	
	
	and
	
	classification.info_id in (
	select information.info_id from information where information.region = 'cent_plains' 
	)
	
	group by classification.classification
) t1_hail
left join 
	(select count(classification.classification) as classification_count,classification.classification from classification group by classification) t2_morphology
	on t1_hail.classification = t2_morphology.classification;


	
	
	
	
	
	
	
	
select classification.classification from classification 
where classification.info_id in (select information.info_id from information where str_to_date(information.start_time,'%m/%d/%Y %H%i') <= '06/07/2015 00:00:00'  and information.info_id > 1); 
	
	
select classification.classification from classification 
where classification.info_id in (select information.info_id from information where str_to_date(information.start_time,'%m/%d/%Y %H%i') <= '06/07/2015 0000' and information.end_time >= '') 

	
	
	
	%m/%d/%Y %H%i
	
	 06/07/2015 0000
	
	
	
	select classification.classification from classification 
where classification.info_id in (select information.info_id from information where date(information.start_time) <= '06/22/2015' and date(information.end_time) >= '06/24/2015' ) ;

2011-12-10 23:00:00
	select * from information;
	
	
	
	
	
	
	
	
	#Within Dates
	select information.info_id from information where date(information.start_time) <= '2015-06-23 ' and date(information.end_time) >= '2015-06-23 ' ;
	
	#Specific Month
	select information.info_id from information where monthname(information.start_time) = 'june';
	
	
	
	
	
	
select classification.classification from classification 
where classification.info_id in (select information.info_id from information where information.start_time <= '' and information.end_time >= '') 





select classification.classification_id from classification 
where classification.info_id in (select information.info_id from information where information.region = 'cent_plains');














Version 1:

select 
classification.classification,  
case when hail.magnitude>= 0.75 AND hail.magnitude< 1 then  count(hail.magnitude) end as hailhalf,
case when hail.magnitude>= 1 AND hail.magnitude< 2 then count(hail.magnitude) end as hailone,
case when hail.magnitude>= 2 then count(hail.magnitude) end as hailaboveone
from classification,hail 
where hail.classification_id=classification.classification_id 
and classification.classification = 'PS';


Version 2:


select 
(select count(classification.classification) from classification where classification.classification = 'CC') as classification_count,
classification.classification,  
case when hail.magnitude>= 0.75 AND hail.magnitude< 1 then  count(hail.magnitude) end as  hailhalf,
case when hail.magnitude>= 1 AND hail.magnitude< 2 then count(hail.magnitude) end as hailone,
case when hail.magnitude>= 2 then count(hail.magnitude) end as hailaboveone
from classification,hail 
where hail.classification_id=classification.classification_id 
and classification.classification = 'PS';


Version 3:


select 
(select count(classification.classification) from classification,hail where classification.classification_id = hail.classification_id) as c,
classification.classification,  
case when hail.magnitude>= 0.75 AND hail.magnitude< 1 then  count(hail.magnitude) end as hailhalf,
case when hail.magnitude>= 1 AND hail.magnitude< 2 then count(hail.magnitude) end as hailone,
case when hail.magnitude>= 2 then count(hail.magnitude) end as hailaboveone
from classification,hail 
where hail.classification_id=classification.classification_id group by classification.classification;



Working on - Version 4:

insert into temp1
select 
classification.classification,  
case when hail.magnitude>= 0.75 AND hail.magnitude< 1 then  count(hail.magnitude) end as hailhalf,
case when hail.magnitude>= 1 AND hail.magnitude< 2 then count(hail.magnitude) end as hailone,
case when hail.magnitude>= 2 then count(hail.magnitude) end as hailaboveone
from classification,hail 
where hail.classification_id=classification.classification_id group by classification.classification
left join (insert into temp2 select count(classification.classification),classification.classification from classification group by classification) 
on temp

;



Final Version 2:


select 
classification.classification,
count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two
from classification,hail 
where hail.classification_id=classification.classification_id
group by classification.classification;

















 create temporary table if not exists temp1 as (select 
classification.classification,
count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two
from classification,hail 
where hail.classification_id=classification.classification_id
group by classification.classification
);

 
create temporary table if not exists temp2 
as (select count(classification.classification) as classification_count,classification.classification from classification group by classification)
;


select temp1.classification,classification_count, hail_below_one, hail_above_one, hail_above_two from temp1 left join temp2 on temp1.classification = temp2.classification;










select * from hail where hail.magnitude >= 1.0 AND hail.magnitude < 2.0;





select 
classification.classification,
count(case when hail.magnitude < 1.0 then hail.magnitude end) as hail_below_one,
count(case when (hail.magnitude >= 1.0 AND hail.magnitude < 2.0) then hail.magnitude end) as hail_above_one,
count(case when hail.magnitude >= 2 then hail.magnitude end) as hail_above_two
from classification,hail 
where hail.classification_id=classification.classification_id
group by classification.classification;










select * from latlong;


