IF OBJECT_ID ('QueryInsert', 'TR') IS NOT NULL
   DROP TRIGGER QueryInsert;
GO
CREATE TRIGGER QueryInsert
ON Query
FOR Insert
AS
	DECLARE @dt0 datetime;
	DECLARE @reg0 int;
	DECLARE @qid0 int;
	select @dt0 = [time] from inserted;
	select @reg0 = regularity from inserted;
	select @qid0 = id from inserted;
	exec InsertScheduleForQuery
		@dt = @dt0,
		@reg = @reg0,
		@qid = @qid0,
		@f = 0;
GO	