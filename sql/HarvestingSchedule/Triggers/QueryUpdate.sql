IF OBJECT_ID ('QueryUpdate', 'TR') IS NOT NULL
   DROP TRIGGER QueryUpdate;
GO
CREATE TRIGGER QueryUpdate
ON Query
FOR UPDATE
AS
	DECLARE @act0 int;
	DECLARE @act1 int;
	DECLARE @dt0 datetime;
	DECLARE @reg0 int;
	DECLARE @qid0 int;
	select @act0 = [active] from inserted;
	select @act1 = [active] from deleted;
	select @dt0 = [time] from inserted;
	select @reg0 = regularity from inserted;
	select @qid0 = id from inserted;
	if @act0 = @act1
	begin
	delete Schedule where query_id = (select id from deleted);
	exec InsertScheduleForQuery
		@dt = @dt0,
		@reg = @reg0,
		@qid = @qid0,
		@f = 0;
	end
GO	