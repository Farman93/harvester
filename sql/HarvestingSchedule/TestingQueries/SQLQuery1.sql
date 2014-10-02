select * from query
select * from schedule
select * from NextHarvest
insert into query(time, regularity) values('01-03-2013 12:20:30.263',1)
delete from query
select * from schedule
update schedule
set status_id = 2, attempts = (select attempts from NextHarvest) + 1
where id = (select id from NextHarvest)
select * from schedule

update schedule
set status_id = 1,
		attempts = 2
where id = 39