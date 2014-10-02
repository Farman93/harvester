IF OBJECT_ID ('NewMissedSchedule', 'TR') IS NOT NULL
   DROP TRIGGER NewMissedSchedule;
GO
CREATE TRIGGER NewMissedSchedule
ON MissedSchedule
instead of INSERT
AS
declare @sid int
while exists (select id from MissedSchedule)
begin
select top 1 @sid = id from MissedSchedule
exec UpdateStatusForSchedule @sid, 5
end