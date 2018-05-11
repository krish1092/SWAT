
#Main Query


#Dont meddle with this!

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



#-------------------------------------------


#Playgorund

#With State



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
	coalesce(t5_tornado.EF5, 0) as EF5
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
			select information.info_id from information where date(information.start_time) >= '2015-06-01' and date(information.end_time) <= '2015-06-23 '
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
on t5_tornado.classification = t1_morphology.classification;






#Without State;



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
		classification.info_id in 
		(
			select information.info_id from information where date(information.start_time) >= '2015-06-01' and date(information.end_time) <= '2015-06-30'
			and monthname(information.start_time) = 'june'
	
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
	from classification
	where 
		classification.classification_id not in (select classification_id from hail) 
		and 
		classification.classification_id not in (select classification_id from tornado) 
		and 
		classification.classification_id not in (select classification_id from thunderstorm) 
		and 
		classification.classification_id not in (select classification_id from flashflood) 
		and
		classification.info_id in 
		(
			select information.info_id from information where date(information.start_time) >= '2015-06-01' and date(information.end_time) <= '2015-06-30'
			and monthname(information.start_time) = 'june'
		)
    
	group by classification.classification

) t6_null_events
on t6_null_events.classification = t1_morphology.classification;












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




#-------------------------- Profile Work



select information.start_time, information.end_time 

from information where information.email_address = 'krish@iastate.edu';

select classification.classification from classification 
where classification.info_id in (select info_id from information where email_address = 'krish@iastate.edu' limit 10); 



#First table


select start_time, end_time , user_classification, hail_count, flashflood_count, thunderstorm_count, tornado_count, null_event_count

from

__________ Profile working query


DATE_FORMAT(`start_date`, '%m/%d/%Y %H%i')

select 
t1.info_id, 
DATE_FORMAT(t1.start_time, '%m/%d/%Y %H%i') as start_time, 
DATE_FORMAT(t1.end_time, '%m/%d/%Y %H%i') as end_time, 
DATE_FORMAT(t1.centre_img_time, '%m/%d/%Y %H%i') as centre_img_time, 
t1.region,
t2.classification, 
coalesce(t3.hail_count,0) as hail_count, coalesce(t4.thunderstorm_wind_count,0) as thunderstorm_wind_count, 
coalesce(t5.flashflood_count,0) as flashflood_count, coalesce(t6.tornado_count,0) as tornado_count,
coalesce(t7.null_count,0) as null_count
from 
(
	select information.info_id, information.start_time, information.end_time , information.centre_img_time, information.region
	from information
	where information.email_address = 'krish@iastate.edu' 
	order by info_id desc
	limit 2
	
) t1
left outer join
(
	select * from classification  
) t2
on t1.info_id = t2.info_id

left outer join
(
	select info_id, count(*) as hail_count,classification_id from hail group by hail.classification_id
) t3

on t1.info_id = t3.info_id and t2.classification_id = t3.classification_id

left outer join
(
	select info_id, count(*) as thunderstorm_wind_count,classification_id from thunderstorm group by thunderstorm.classification_id
) t4

on t1.info_id = t4.info_id and t2.classification_id = t4.classification_id

left outer join
(
	select info_id, count(*) as flashflood_count,classification_id from flashflood group by flashflood.classification_id
) t5

on t1.info_id = t5.info_id and t2.classification_id = t5.classification_id

left outer join
(
	select info_id, count(*) as tornado_count,classification_id from tornado group by tornado.classification_id
) t6

on t1.info_id = t6.info_id and t2.classification_id = t6.classification_id

left outer join
(
	select info_id, count(*) as null_count,classification_id from classification
	where
	(
		classification_id not in (select classification_id from hail)
		and
		classification_id not in (select classification_id from flashflood)
		and
		classification_id not in (select classification_id from thunderstorm)
		and
		classification_id not in (select classification_id from tornado)
	)
	
	group by classification.classification_id
) t7

on t1.info_id = t7.info_id and t2.classification_id = t7.classification_id
order by t1.info_id ;



#Analysis Part

#- Large table - With Filter


 #- Analytics large table - Final Query
 
 select date_time, count(date_time) as count 
 from unmodified_user_classification 
 where 
 email_address in (select email_address from users)
 group by date_time having count(date_time) > 1
 order by count(date_time) desc, date_time;

#- Analytics Small Table- Date specific

select classification, email_address  from unmodified_user_classification 
where date_time = ' 2015-06-13 00:00:00' order by email_address;


#-Analytics Large table with Date window and User Expert level

select date_time, count(date_time) as count 
 from unmodified_user_classification 
 where 
 email_address in (select email_address from users )
 group by date_time 
 having count(date_time) > 1 
 and 
 date_time >= '2015-06-06 00:00:00' and date_time <= '2015-06-13 02:30:00'
 order by count(date_time) desc, date_time;



 

#Alternate Query- Little too complicated
select t1.date_time, t1.count from 
(
 select date_time, count(date_time) as count 
 from unmodified_user_classification 
 where 
 email_address in (select email_address from users)
 group by date_time 
 order by count(date_time) desc, date_time
) t1
 where t1.count > 1
 group by t1.date_time 
 order by count(t1.date_time) desc, t1.date_time;


