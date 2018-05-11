-----------------------------------------------------------------------------------------------
update information set email_id = 'krish1610@gmal.com' where info_id = 6;

select * from information;







update users set users.authenticated = 1 where users.email_address = (select email_address from user_auth where user_auth.token = ''
AND user_auth.auth_purpose = 'activation');









alter table information add email_id varchar(30);
describe information;

alter table information add foreign key (email_id) references users(email_id) on delete cascade;

select classification, start_time, end_time, info_id from classification 
	where info_id = ANY (select info_id from information 
	where email_id = 'krish1610@gmail.com' ) LIMIT 10;
	
	
	
select information.start_time, information.end_time, classification.classification, classification.info_id from classification
right join  information
on information.info_id = classification.info_id and information.email_id = 'com' 
order by information.info_id limit 10;


select * from classification
                13      13 BL             06/06/2015 0430 06/06/2015 0530


insert into classification values(14,13,'MC','06/06/2015 0630','06/06/2015 0830');
	

----------------------------------------------------------------------------------------


#Scheduled Event for AutoDeleting the users if they do not acknowledge within 24 hours!

create event user_rows_delete
on schedule
	every 1 day
do
	delete  from users where users.auth_expired = true;

select * from users where users.email_id = 'k@kl.com';	
	
drop event if exists user_rows_delete;


#$2a$10$p5eNqFEOtkCkcKqIALDu8uLewZWAEwBmU21Wz4PCXsj5gCicrbQy6 - Swat@2016
insert into users values(null,'krish@krish.com','$2a$10$p5eNqFEOtkCkcKqIALDu8uLewZWAEwBmU21Wz4PCXsj5gCicrbQy6','Krishnan');


#ALTER TABLE information DROP FOREIGN KEY username;
#ALTER TABLE information drop column username;

ALTER TABLE information ADD username char(30);
ALTER TABLE information ADD CONSTRAINT username FOREIGN KEY (username) REFERENCES users(username);




#----------------------------------------------------------------------------------------



create table users (
username varchar(12) not null  primary key,
password varchar(64) not null,
email_id varchar(30) not null,
name char(30) not null
);

#Do all the dummy queries from here

#------------------------------------------------------------------------------------------------------
describe users;  


select password from users where email_id = 'k@kl.com'

select * from users ;

delete from users where email_id = 'krish@iastate.edu' 

describe swat;


update users set users.authenticated = 0 where users.token = '38b8fc11-5b2b-4441-912d-b1dbffdf6857';

select token from users;

create database swat;

insert into users values('krish1092','Swat@2016','k@kl.com','Krishnan');
update users set password = '$2a$10$mxWQcRr6c2ogjQyu3cYdauFK2rg5cuTNlPoj8aDO6VF.2Wpe.3.OC' where email_id = 'k@kl.com';
select * from users;
select exists(select 1 from users where users.email_id = 'krish@krish.com' limit 1);



#all dummy queries from here on.

describe swat.piechart;

#dummy
select count(*), (select count (*) from classification c where c.classification = 'IC') as IC from classification;
(select classification as IC, COUNT(*) from classification c where c.classification = 'IC',
select classification as CC, COUNT(*) from classification c2 where c2.classification = 'CC');

    
select * from overallview;    


select * from piechart;

select * from hail;







# dUMMY QUERIES


insert into thunderstorm (classification_id,info_id,magnitude)values (54,41,50.0);

insert into thunderstorm (classification_id,info_id,magnitude)values (56,42,50.0);



select * from overallview;

select * from hail;
select * from thunderstorm;
select * from tornado;
select * from flashflood;

select * from information;

select * from classification;





























select info_id from information where email_id = 'krish1610@gmail.com' order by info_id desc limit 10;


select distinct classification, info_id from classification where info_id IN 
(select info_id from information where email_id = 'krish1610@gmail.com' order by info_id desc
limit 10) group by info_id; 

select * from classification ;
insert into classification values(null,13,'MC','06/06/2015 0630','06/06/2015 0830');





select distinct classification.classification, information.info_id from classification 
right  join information 
on information.info_id IN (select info_id from information where information.email_id = 'krish1610@gmail.com' order by info_id desc )  
and information.info_id = classification.info_id;





latlong_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
region_south_lat double,
region_north_lat double,
region_west_long double,
region_east_long double,
region varchar(30),
start_date date,
end_date date,
foreign key (region)
	references regions(region)
		on delete cascade



delete from latlong where latlong.region_south_lat = 33.039404

insert into  latlong(region_south_lat, region_north_lat, region_west_long, region_east_long, region,start_date ,end_date)
values (33.039404,43.4398685,-106.8418,-89.104845, 'cent_plains', '2010-11-10 01:00:00','2011-12-10 23:30:00' )




select region_east_long,region_west_long,region_north_lat,region_south_lat from latlong where region = 'cent_plains' AND (start_date <= 'Sun Jun 05 17:00:00 CDT 2011' AND end_date >= 'Sun Jun 05 17:00:00 CDT 2011') OR (start_date <= 'Mon Jun 06 03:00:00 CDT 2011' AND end_date >= 'Mon Jun 06 03:00:00 CDT 2011')






select region_east_long,region_west_long,region_north_lat,region_south_lat from latlong
where region = 'cent_plains' AND 
(DATE_FORMAT(`start_date`, '%m/%d/%Y %H%i') <= '06/05/2011 2200' AND end_date >= '2011-06-05 22:00:00') OR (start_date <= '2011-06-06 08:00:00' AND end_date >= '2011-06-06 08:00:00');



select region_east_long,region_west_long,region_north_lat,region_south_lat,start_date,end_date from latlong
where region = 'cent_plains' AND 
(start_date <= '06/05/2011 2200' AND end_date >= '2011-06-05 22:00:00') OR (start_date <= '2011-06-06 08:00:00' AND end_date >= '2011-06-06 08:00:00');


select region_east_long,region_west_long,region_north_lat,region_south_lat from latlong where region = 'cent_plains' AND (start_date <= 'Sun Jun 05 17:00:00 CDT 2011' AND end_date >= 'Sun Jun 05 17:00:00 CDT 2011') OR (start_date <= 'Mon Jun 06 03:00:00 CDT 2011' AND end_date >= 'Mon Jun 06 03:00:00 CDT 2011')

insert into  latlong(region_south_lat, region_north_lat, region_west_long, region_east_long, region,start_date ,end_date)
values (35.039404,45.4398685,-104.8418,-87.104845, 'cent_plains', '2011-12-10 23:30:00','2012-12-11 00:00:00' )




select region_east_long,region_west_long,region_north_lat,region_south_lat,start_date,end_date from latlong
where region = 'cent_plains' AND 
(start_date <= '06/05/2015 2200' AND end_date >= '2011-06-05 22:00:00') OR (start_date <= '2011-06-06 08:00:00' AND end_date >= '2011-06-06 08:00:00');



startdate = '2011-12-10 23:00:00' 
enddate = '2011-12-11 02:00:00'


-Complex :        -89.104845        -106.8418       43.4398685        33.039404
-Simple  :        -89.104845        -106.8418       43.4398685        33.039404




longLeft=-106.8418;
		longRight=-89.104845;
		
		longDiff=longRight-longLeft;

		latTop=43.4398685;
		latBottom=33.039404;


insert into  latlong(region_south_lat, region_north_lat, region_west_long, region_east_long, region,start_date ,end_date)
values (33.039404,43.4398685,-106.8418,-89.104845, 'cent_plains', '2015-05-25 23:30:00','2015-07-11 00:00:00' )


		
		
		
		

select region_east_long,region_west_long,region_north_lat,region_south_lat from latlong 
where region = 'cent_plains' AND start_date <= '2011-06-05 22:00:00' AND end_date >= '2011-06-06 08:00:00'

select region_east_long,region_west_long,region_north_lat,region_south_lat from latlong
where region = 'cent_plains' AND 
(DATE_FORMAT(`start_date`, '%m/%d/%Y %H%i') <= '12/10/2011 2300' AND end_date >= '2011-12-10 23:00:00') OR (start_date <= '2011-12-11 02:00:00' AND end_date >= '2011-12-11 02:00:00');

06/17/2015 0030

DATE_FORMAT(`start_date`, '%Y-%m-%d %H:%i')

select * from latlong where ;

longLeft=-106.8418;
		longRight=-89.104845;
		latTop=43.4398685;
		latBottom=33.039404;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		-------------------------
		
insert into null_events (classification_id, info_id, state) values (20,11,'IA'),(22,12,'KS');