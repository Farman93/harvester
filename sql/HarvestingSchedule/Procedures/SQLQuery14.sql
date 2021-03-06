USE [HarvestingSchedule]
GO
/****** Объект:  StoredProcedure [dbo].[CheckScheduleForQuery]    Дата сценария: 04/22/2013 17:36:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[CheckScheduleForQuery] 
    @qid int, @uid int
AS 
   select top 10 convert(char(10), [date_time], 104) +' '+ convert(char(5), [date_time], 114) as date_time, status_id, attempts,msg 
		from schedule, status, query 
		where status_id = status.id and status_id != 0 and 
		query_id = @qid and query_id = query.id and [user_id] = @uid
		order by date_time desc

