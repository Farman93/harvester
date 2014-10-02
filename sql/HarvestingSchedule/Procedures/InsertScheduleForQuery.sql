IF OBJECT_ID ( 'InsertScheduleForQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE InsertScheduleForQuery;
GO
CREATE PROCEDURE InsertScheduleForQuery 
    @dt datetime,
    @reg int,
    @qid int,
    @f bit
AS 
   declare @idt datetime;
   declare @prv int;
   set @idt = @dt;
   SET NOCOUNT ON;
	if @f = 0
	begin
		insert into Schedule(date_time,status_id,attempts, query_id)
		values(@idt, 0, 0, @qid);
		select @prv = id from Schedule where query_id = @qid;
		while datepart(day,@idt) = datepart(day,@dt)
		begin
			
			set @idt = dateadd(minute,@reg,@idt);
			insert into Schedule(date_time,status_id,attempts, query_id, prev_sch_id)
			values(@idt, 0, 0, @qid, @prv);
			select @prv = id from Schedule where prev_sch_id = @prv;
		end;
	end;
	else
	begin
		Select @prv = id from Schedule where query_id = @qid and date_time =@dt;
		while datepart(day,@idt) = datepart(day,@dt)
		begin
			set @idt = dateadd(minute,@reg,@idt);
			insert into Schedule(date_time,status_id,attempts, query_id, prev_sch_id)
			values(@idt, 0, 0, @qid, @prv);
			select @prv = id from Schedule where prev_sch_id = @prv;
		end;
	end;
	
	
	UPDATE SCHEDULE
	set status_id = 5 
	where id in (select id from MissedSchedule)
GO