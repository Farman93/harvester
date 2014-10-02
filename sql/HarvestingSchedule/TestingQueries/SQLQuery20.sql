exec SelectQueryForUser login1
exec AddQuery 'testName', 'someEndURL', 'someStartURL', 1, '2013-03-09 16:54:00', 1, 17, 'D:\\'
select * from query
select * from schedule
select * from [user]