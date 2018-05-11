
select hail.radar_UTC_time, count(hail.radar_UTC_time) from hail
where hail.info_id = 23
group by hail.radar_UTC_time
order by hail.hail_id;

select tornado.radar_UTC_time, count(tornado.radar_UTC_time) from tornado
where tornado.info_id = 64
group by tornado.radar_UTC_time
order by tornado.tornado_id;


select thunderstorm.radar_UTC_time, count(thunderstorm.radar_UTC_time) from thunderstorm
where thunderstorm.info_id = 64
group by thunderstorm.radar_UTC_time
order by thunderstorm.thunderstorm_id;


select flashflood.radar_UTC_time, count(flashflood.radar_UTC_time) from flashflood
where flashflood.info_id = 64
group by flashflood.radar_UTC_time
order by flashflood.flashflood_id;
































select * from

	(
	select information.info_id,information.start_time, information.end_time 
	from information
	where information.email_address = 'krish@iastate.edu' 
	order by information.info_id desc limit 5) as t1 

	left outer join

	(
	select classification.info_id,classification.classification_id, classification.classification 
	from classification
	) as t2 
	on t1.info_id = t2.info_id

	left outer join
	(
	select hail.info_id, hail.classification_id, hail.magnitude, hail.state from hail 
	) as t3
	on t3.info_id = t1.info_id and t3.classification_id = t2.classification_id
	
	left outer join
	(
	select tornado.info_id, tornado.classification_id, tornado.magnitude, tornado.state from tornado 
	) as t4
	on t4.info_id = t1.info_id and t4.classification_id = t2.classification_id
	
	left outer join
	(
	select thunderstorm.info_id, thunderstorm.classification_id, thunderstorm.magnitude, thunderstorm.state from thunderstorm 
	) as t5
	on t5.info_id = t1.info_id and t5.classification_id = t2.classification_id
	
	left outer join
	(
	select flashflood.info_id, flashflood.classification_id, flashflood.state from flashflood 
	) as t6
	on t6.info_id = t1.info_id and t6.classification_id = t2.classification_id;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
select 
distinct 
t1.info_id, t1.start_time, t1.end_time,
t2.classification as classification, 
t3.magnitude as hail_magnitude,
t4.magnitude as tornado_magnitude,
t5.magnitude as thunderstorm_magnitude
from

	(
	select information.info_id,information.start_time, information.end_time 
	from information
	where information.email_address = 'krish@iastate.edu' and information.info_id = 23
	) as t1 

	left outer join

	(
	select classification.info_id,classification.classification_id, classification.classification 
	from classification
	) as t2 
	on t1.info_id = t2.info_id

	left outer join
	(
	select hail.info_id, hail.classification_id, hail.magnitude, hail.state from hail 
	) as t3
	on t3.info_id = t1.info_id and t3.classification_id = t2.classification_id
	
	left outer join
	(
	select tornado.info_id, tornado.classification_id, tornado.magnitude, tornado.state from tornado 
	) as t4
	on t4.info_id = t1.info_id and t4.classification_id = t2.classification_id
	
	left outer join
	(
	select thunderstorm.info_id, thunderstorm.classification_id, thunderstorm.magnitude, thunderstorm.state from thunderstorm 
	) as t5
	on t5.info_id = t1.info_id and t5.classification_id = t2.classification_id
	
	left outer join
	(
	select flashflood.info_id, flashflood.classification_id, flashflood.state from flashflood 
	) as t6
	on t6.info_id = t1.info_id and t6.classification_id = t2.classification_id;
	
	