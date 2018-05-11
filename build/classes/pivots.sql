use swat;


#Hail
drop table if exists hail;

create table hail
(
	hail_id bigint primary KEY not null auto_increment,
    classification varchar(3),
    magnitude double,
    info_id bigint not null,
    foreign key (info_id)
    	references information(info_id)
    		on cascade delete
);

insert into hail values
(null,'BE',0.4),
(null,'BE',0.8),
(null,'NS',0.9),
(null,'NS',1.4),
(null,'NS',2.4),
(null,'NS',1.4),
(null,'NS',3.4);


insert into hail values
(null,'BE',0.4),
(null,'BE',0.8),
(null,'NS',0.9),
(null,'NS',1.4),
(null,'NS',2.4),
(null,'NS',1.4),
(null,'NS',3.4);


insert into hail values
(null,'TS',0.4),
(null,'TS',0.8),
(null,'BL',0.9),
(null,'BL',1.4),
(null,'MC',2.4),
(null,'MC',1.4),
(null,'MC',3.4);





select * from hail;

drop view if exists hail_extended;

create view hail_extended as (
  select
    (select classification from classification,hail where hail.classification_id=classification.classification_id) as classification,
    case when magnitude >= 0.75 AND magnitude < 1 then magnitude end as hailhalf,
    case when magnitude >= 1 AND magnitude < 2 then magnitude end as hailone,
    case when magnitude >= 2 then magnitude end as hailaboveone
  from hail
);




select * from hail_extended;

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

select * from hail_final;

#Hail End

#Thunderstorm

drop table if exists thunderstorm;

create table thunderstorm
(
	thunder_id bigint primary KEY not null auto_increment,
    classification varchar(3),
    magnitude double
);

insert into thunderstorm values
(null,'BE',56),
(null,'BE',76),
(null,'NS',76),
(null,'NS',54),
(null,'NS',87),
(null,'NS',55),
(null,'NS',56);


insert into thunderstorm values
(null,'TS',56),
(null,'TS',76),
(null,'BL',76),
(null,'BL',54),
(null,'BL',87),
(null,'IC',55),
(null,'IC',56);





select * from thunderstorm;

drop view if exists thunderstorm_extended;

create view thunderstorm_extended as (
  select
    thunderstorm.classification,
    case when magnitude >= 50 AND magnitude < 65 then magnitude end as thunderstorm50to65,
    case when magnitude >= 65 then magnitude end as thunderstorm65
  from thunderstorm
);




select * from thunderstorm_extended;

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

select * from thunderstorm_final;


#Thunderstorm End

#Tornado

drop table if exists tornado;

create table tornado
(
	thunder_id bigint primary KEY not null auto_increment,
    classification varchar(3),
    magnitude varchar(4)
);

insert into tornado values
(null,'BE','EF1'),
(null,'BE','EF3'),
(null,'NS','EF1'),
(null,'NS','EF1'),
(null,'NS','EF1'),
(null,'NS','EF4'),
(null,'NS','EF5');


insert into tornado values
(null,'TS','EF1'),
(null,'TS','EF2'),
(null,'BL','EF1'),
(null,'BL','EF1'),
(null,'BL','EF2'),
(null,'IC','EF4'),
(null,'IC','EF3');





select * from tornado;

drop view if exists tornado_extended;

create view tornado_extended as (
  select
    tornado.classification,
    case when magnitude = 'EF1' then magnitude end as level1,
    case when magnitude = 'EF2' then magnitude end as level2,
    case when magnitude = 'EF3' then magnitude end as level3,
    case when magnitude = 'EF4' then magnitude end as level4,
    case when magnitude = 'EF5' then magnitude end as level5
  from tornado
);




select * from tornado_extended;

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

select * from tornado_final;

#Tornado End

#FlashFlood
drop table if exists flashflood;

create table flashflood
(
	thunder_id bigint primary KEY not null auto_increment,
    classification varchar(3)
);

insert into flashflood values
(null,'BE'),
(null,'BE'),
(null,'NS'),
(null,'NS'),
(null,'NS'),
(null,'NS'),
(null,'NS');


insert into flashflood values
(null,'TS'),
(null,'TS'),
(null,'BL'),
(null,'BL'),
(null,'BL'),
(null,'IC'),
(null,'IC');




select * from hail_final;

select * from thunderstorm_final;

select * from tornado_final;


SELECT classification,COUNT(*) AS EventCount
    FROM flashflood
GROUP BY classification;















create view flashfloodview as(
SELECT classification,COUNT(*) AS EventCount
    FROM flashflood
GROUP BY classification);


select h.class,count(h.magnitude) from hail h where h.magnitude > 1 group by class  ;

drop view if exists flashflood_extended;

create view flashflood_extended as (
  select
  case when class = 'BE' then class end as BE,
  case when class = 'IC' then class end as IC,
  case when class = 'MC' then class end as MC,
  case when class = 'CC' then class end as CC,
  case when class = 'BL' then class end as BL,
  case when class = 'NL' then class end as NL,
  case when class = 'PS' then class end as PS,
  case when class = 'TS' then class end as TS,
  case when class = 'NS' then class end as NS,
  case when class = 'LS' then class end as LS
  from flashflood
);




select * from flashflood_extended;

drop view if exists flashflood_extended_Pivot;

create view flashflood_extended_Pivot as (
  select
    count(BE) as BE ,
    count(IC) as IC ,
    count(MC) as MC ,
    count(CC) as CC ,
    count(BL) as BL ,
    count(NL) as NL ,
    count(PS) as PS ,
    count(TS) as TS ,
    count(NS) as NS ,
    count(LS) as LS 
  from flashflood_extended
  
);


SELECT * FROM flashflood_extended_Pivot;

drop view if exists overallview;

create view overallview as 
	(select hail_final.class,
    hailhalf,hailone,hailaboveone,
    thunderstorm50to65,thunderstorm65,
    level1,level2,level3,level4,level5,
	eventcount
	
    from hail_final,thunderstorm_final,tornado_final,flashfloodview  group by hail_final.class) 
    
    ;
    
select * from overallview;

select * from hail_final;

select * from thunderstorm_final;

select * from tornado_final;

SELECT class,COUNT(*) AS EventCount
    FROM flashflood
GROUP BY class;



#Flashflood End
