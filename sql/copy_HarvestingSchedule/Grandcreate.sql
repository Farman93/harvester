CREATE DATABASE HarvestingSchedule

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Schedule]') AND type in (N'U'))
DROP TABLE [dbo].[Schedule]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Query]') AND type in (N'U'))
DROP TABLE [dbo].[Query]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Status]') AND type in (N'U'))
DROP TABLE [dbo].[Status]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[User]') AND type in (N'U'))
DROP TABLE [dbo].[User]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Protocol]') AND type in (N'U'))
DROP TABLE [dbo].[Protocol]
GO

CREATE TABLE Protocol (
	id int not null primary key,
	name nvarchar(max),
	class nvarchar(max),
	path nvarchar(max),
	xml xml
);

CREATE TABLE [User] (
	id int not null primary key, 
	login nvarchar(max),
	password nvarchar(max),
	cookie int
);

CREATE TABLE [Status] (
	id int not null primary key,
	msg nvarchar(max)
);

CREATE TABLE Query (
	id int not null primary key,
	name nvarchar(max),
	endURL nvarchar(max),
	startURL nvarchar(max),
	protocol_id int references Protocol,
	time datetime not null,
	regularity int not null,
	user_id int references [User],
	struct_loc nvarchar(max),
	last_succ datetime,
	active bit
);

CREATE TABLE Schedule (
	id int not null primary key,
	date_time datetime,
	status_id int references [Status],
	attempts int,
	query_id int references Query,
	prev_sch_id int references Schedule
);

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

IF OBJECT_ID ( 'ActivateQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE ActivateQuery;
GO
CREATE PROCEDURE ActivateQuery 
    @qid int,
    @uid int
AS 
   update query set active = 1 where @qid = query.id and [user_id] = @uid
GO

IF OBJECT_ID ( 'AddQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE AddQuery;
GO
CREATE PROCEDURE AddQuery 
    @name nvarchar(max),
    @eURL nvarchar(max),
    @sURL nvarchar(max),
    @pid int,
    @time datetime,
    @reg int,
    @uid int,
    @sloc nvarchar(max)
    
AS 
   INSERT INTO [Query]([name],endURL,startURL,protocol_id,[time],regularity,user_id, active, struct_loc) 
   VALUES(@name,
    @eURL ,
    @sURL ,
    @pid ,
    @time ,
    @reg ,
    @uid ,
    1,
    @sloc)
GO

IF OBJECT_ID ( 'AddUser', 'P' ) IS NOT NULL 
    DROP PROCEDURE AddUser;
GO
CREATE PROCEDURE AddUser 
    @lg nvarchar(max),
    @pw nvarchar(max)
AS 
   INSERT INTO [User]([login], password) VALUES(@lg, @pw)
GO

IF OBJECT_ID ( 'CheckCookie', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckCookie;
GO
CREATE PROCEDURE CheckCookie 
    @ck int
AS 
   SELECT* from [User] where cookie = @ck
GO

IF OBJECT_ID ( 'CheckNextSchedule', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckNextSchedule;
GO
CREATE PROCEDURE CheckNextSchedule 
    
    
AS 
   select query_id, id,
		datepart(d,date_time) as d,
		datepart(m,date_time) as mo,
		datepart(yy,date_time) as y,
		datepart(hh,date_time) as h,
		datepart(minute,date_time) as mi,
		datepart(ss,date_time) as s
		from NextHarvest
GO

IF OBJECT_ID ( 'CheckPass', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckPass;
GO
CREATE PROCEDURE CheckPass 
    @lg nvarchar(max),
    @pw nvarchar(max)
AS 
   SELECT* from [User] where login = @lg and password = @pw
GO

IF OBJECT_ID ( 'CheckProtocol', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckProtocol;
GO
CREATE PROCEDURE CheckProtocol 
    @pid int
    
AS 
   select * from [protocol] where id = @pid
GO

IF OBJECT_ID ( 'CheckQueryExistance', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckQueryExistance;
GO
CREATE PROCEDURE CheckQueryExistance 
    @eURL nvarchar(max),
    @sURL nvarchar(max),
    @pid int,
    @time datetime,
    @reg int,
    @uid int,
    @sloc nvarchar(max)
    
AS 
   select * from query where
		@eURL = endURL AND
		@sURL = startURL AND
		@pid = protocol_id AND
		@time = [time] AND
		@reg = regularity AND
		@uid = user_id AND
		@sloc = struct_loc
GO

IF OBJECT_ID ( 'DeactivateQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE DeactivateQuery;
GO
CREATE PROCEDURE DeactivateQuery 
    @qid int,
    @uid int
AS 
   update query set active = 0 where @qid = query.id and [user_id] = @uid
GO

IF OBJECT_ID ( 'DeleteQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE DeleteQuery;
GO
CREATE PROCEDURE DeleteQuery 
    @qid int,
    @uid int
AS 
   delete from Query where @qid = id and @uid = [user_id]
GO

IF OBJECT_ID ( 'GetProtocolClassForId', 'P' ) IS NOT NULL 
    DROP PROCEDURE GetProtocolClassForId;
GO
CREATE PROCEDURE GetProtocolClassForId
	@pid int
AS 
   select [class] from [protocol] where id = @pid
GO
  
IF OBJECT_ID ( 'GetProtocolPathForId', 'P' ) IS NOT NULL 
    DROP PROCEDURE GetProtocolPathForId;
GO 
CREATE PROCEDURE GetProtocolPathForId
	@pid int
AS 
   select [path] from [protocol] where id = @pid
GO

IF OBJECT_ID ( 'SelectProtocolForId', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectProtocolForId;
GO
CREATE PROCEDURE SelectProtocolForId 
    
   @pid int
AS 
   select [name] from [protocol] where id = @pid
GO

IF OBJECT_ID ( 'SelectProtocols', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectProtocols;
GO
CREATE PROCEDURE SelectProtocols    
AS 
   select id, [name] from [protocol]
GO

IF OBJECT_ID ( 'SelectQueryForId', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectQueryForId;
GO
CREATE PROCEDURE SelectQueryForId  
   @qid int
AS 
   select * from [Query] where id = @qid
GO

IF OBJECT_ID ( 'SelectQueryForQid', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectQueryForQid;
GO
CREATE PROCEDURE SelectQueryForQid 
    @qid int    
AS 
   select * from query where id = @qid
GO

IF OBJECT_ID ( 'SelectQueryForUser', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectQueryForUser;
GO
CREATE PROCEDURE SelectQueryForUser 
    @uid int   
AS 
   select * from query where [user_id] = @uid
GO

IF OBJECT_ID ( 'SelectScheduleForQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectScheduleForQuery;
GO
CREATE PROCEDURE SelectScheduleForQuery 
    @qid int    
AS 
   select * from schedule where query_id = @qid
GO

IF OBJECT_ID ( 'SelectUser', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectUser;
GO
CREATE PROCEDURE SelectUser 
    @lg nvarchar(max)
AS 
   SELECT* from [User] where login = @lg
GO

IF OBJECT_ID ( 'SelectScheduleForQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectScheduleForQuery;
GO
CREATE PROCEDURE SelectScheduleForQuery 
    @qid int
    
AS 
   select * from schedule where query_id = @qid
GO

IF OBJECT_ID ( 'UpdateQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE UpdateQuery;
GO
CREATE PROCEDURE UpdateQuery 
    @qid int,
    @name nvarchar(max),
    @eURL nvarchar(max),
    @sURL nvarchar(max),
    @pid int,
    @time datetime,
    @reg int,
    @sloc nvarchar(max)
    
AS 
   UPDATE [Query]
   set 
		[name] = @name,
		endURL = @eURL,
		startURL = @sURL,
		protocol_id = @pid,
		[time] = @time,
		regularity = @reg,
		struct_loc = @sloc
  WHERE id = @qid
GO

IF OBJECT_ID ( 'UpdateCookie', 'P' ) IS NOT NULL 
    DROP PROCEDURE UpdateCookie;
GO
CREATE PROCEDURE UpdateCookie 
    @login varchar(max),
    @cookie int
    
    
AS 	
   UPDATE [User]
   set cookie = @cookie 
   where [login] = @login
GO

IF OBJECT_ID ( 'CheckScheduleForQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckScheduleForQuery;
GO

CREATE PROCEDURE CheckScheduleForQuery
    @qid int, @uid int
AS 
   select top 10 convert(char(10), [date_time], 104) +' '+ convert(char(5), [date_time], 114) as date_time, status_id, attempts,msg 
		from schedule, status, query 
		where status_id = status.id and status_id != 0 and 
		query_id = @qid and query_id = query.id and [user_id] = @uid
		order by date_time  
GO 
   
IF OBJECT_ID ('QueryDelete', 'TR') IS NOT NULL
   DROP TRIGGER QueryDelete;
GO
CREATE TRIGGER QueryDelete
	ON Query
	INSTEAD OF DELETE
	AS
	DELETE FROM Schedule
	WHERE query_id IN (select id from deleted)
	DELETE FROM Query
	Where id in (select id from deleted)IF OBJECT_ID ('QueryInsert', 'TR') IS NOT NULL
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

set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [dbo].[UpdateScheduleStatus]
ON [dbo].[Schedule]
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
	
	IF @stat > 1 and @stat != 5
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

IF  EXISTS (SELECT * FROM sys.database_principals WHERE name = N'QueryLogin')
DROP USER [QueryLogin]
GO

IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'QueryLogin')
CREATE LOGIN [QueryLogin] WITH PASSWORD=N'QueryLogin', DEFAULT_DATABASE=[Protocols], DEFAULT_LANGUAGE=[русский], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO

ALTER LOGIN [QueryLogin] DISABLE
GO

IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = N'QueryLogin')
CREATE USER [QueryLogin] FOR LOGIN [QueryLogin] WITH DEFAULT_SCHEMA=[dbo]
GO

GRANT CONNECT TO [QueryLogin]
GO
DENY ALTER TO [QueryLogin]
GO
DENY BACKUP DATABASE TO [QueryLogin]
GO
DENY BACKUP LOG TO [QueryLogin]
GO
DENY CREATE DEFAULT TO [QueryLogin]
GO
DENY CREATE FUNCTION TO [QueryLogin]
GO
DENY CREATE PROCEDURE TO [QueryLogin]
GO
DENY CREATE RULE TO [QueryLogin]
GO
DENY CREATE TABLE TO [QueryLogin]
GO
DENY CREATE VIEW TO [QueryLogin]
GO
DENY DELETE TO [QueryLogin]
GO
GRANT CONNECT TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[CheckCookie] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[DeleteQuery] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[DeactivateQuery] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[ActivateQuery] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[CheckQueryExistance] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[SelectProtocols] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[CheckProtocol] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[SelectProtocolForId] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[SelectQueryForId] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[UpdateQuery] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[GetProtocolPathForId] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[GetProtocolClassForId] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[UpdateCookie] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[SelectUser] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[AddQuery] TO [QueryLogin]
GO
GRANT SELECT ON [dbo].[Schedule] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[AddUser] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[CheckPass] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[SelectQueryForUser] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[CheckScheduleForQuery] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[UpdateCookie] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[UpdateCookie] TO [QueryLogin]
GO
GRANT EXECUTE ON [dbo].[CheckNextSchedule] TO [QueryLogin]
GO
