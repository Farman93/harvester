select * from [user]
select * from query
select * from schedule where query_id = 89 order by date_time asc, status_id desc
union 
select top 1 convert(varchar(100),date_time,20) as  date_time, status_id, attempts,msg from schedule, status where status_id = status.id and status_id = 0 order by date_time, status_id
exec CheckScheduleForQuery 89
exec UpdateStatusForSchedule 2164, 3
select * from schedule
delete from schedule where id = 2133
update schedule set prev_sch_id = null where prev_sch_id = 2133