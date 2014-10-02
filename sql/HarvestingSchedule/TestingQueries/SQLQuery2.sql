exec AddQuery
@name = 'queryTest',
@eURL = 'google.ru',
@sURL = 'localhost',
@pid = 2,
@time = '2013-03-05',
@reg = 2,
@uid = 1,
@sloc = 'd:\\temp_folder\\file.xml';
select * from Query
select * from Schedule where query_id = 67

exec SelectScheduleForQuery
@qid = 65
exec SelectQueryForUser
@uid = 3
exec UpdateStatusForSchedule
@sid = 146,
@stid = 2

select * from NextHarvest


delete from query