IF OBJECT_ID ('UpdateScheduleStatus', 'TR') IS NOT NULL
   DROP TRIGGER UpdateScheduleStatus;
GO
CREATE TRIGGER UpdateScheduleStatus
ON Schedule
FOR UPDATE
AS
IF UPDATE(status_id) 
BEGIN
	declare @stat int;
	declare @att int;
	declare @dt datetime;
	declare @qid int;
	declare @sid int;
	declare @psid int;
	declare @nsid int;
	declare @msid int
	select @stat = status_id from inserted;
	select @att = attempts from inserted;
	select @dt = date_time from inserted;
	select @qid = query_id from inserted;
	select @sid = id from inserted;
	select @psid = prev_sch_id from inserted;
	select @nsid = id from Schedule where prev_sch_id = @sid;
	
	IF @stat > 1
	BEGIN
		if @att < 12
		begin
			insert into Schedule(date_time,status_id,attempts,query_id, prev_sch_id) 
			values(DATEADD(minute,5,@dt),0,@att,@qid,@sid);
			select @msid = id from Schedule where prev_sch_id = @sid and id <> @nsid;
			update Schedule
			set prev_sch_id = @msid
			where id = @nsid; 
		end;
		
		else
		begin
			if @att < 35
			begin
				insert into Schedule(date_time,status_id,attempts,query_id, prev_sch_id) 
					values(DATEADD(hour,1,@dt),0,@att,@qid,@sid);
				select @msid = id from Schedule where prev_sch_id = @sid and id <> @nsid;
				update Schedule
				set prev_sch_id = @msid
				where id = @nsid; 	
			end;
			else
			begin
				update Schedule
				set status_id = 4
				where id = @sid;
			end;
		end;
	END;
	
	IF @stat = 1
	BEGIN
		update Query 
		set last_succ = @dt
		where id = @qid;
	END;
END;