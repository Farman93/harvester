IF OBJECT_ID ( 'CheckNextSchedule', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckNextSchedule;
GO
CREATE PROCEDURE CheckNextSchedule 
    
    
AS 
   select query_id, id,
		datepart(d,date_time) as d,
		datepart(m,date_time) as mo,
		datepart(yy,date_time) as y,
		datepart(hh,date_time) as h,
		datepart(minute,date_time) as mi,
		datepart(ss,date_time) as s
		from NextHarvest
GO