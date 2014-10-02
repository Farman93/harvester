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