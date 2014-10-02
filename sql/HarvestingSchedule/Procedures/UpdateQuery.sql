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