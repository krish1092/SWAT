#Overall Analytics Report
select 
t1.date_time, count(t1.date_time) as count,
t2.region
from 
(
	select  * from unmodified_user_classification
	where 
	email_address is not null
) as t1
left outer join
(
	select * from information
	where email_address is not null
) as t2
on t1.info_id = t2.info_id
group by t1.date_time
order by count(t1.date_time) desc, date_time
limit 20;


#Analytics Classification for a specific Date and Region

select 
t1.classification, t1.email_address 
from
(
	select classification, email_address, info_id from unmodified_user_classification
	where date_time = '' and email_address is not null
) as t1
left outer join
(	
	select info_id from information where email_address is not null and region = ''
) as t2
on t1.info_id = t2.info_id
order by t1.email_address;