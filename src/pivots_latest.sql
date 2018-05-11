		
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

#Pivots End