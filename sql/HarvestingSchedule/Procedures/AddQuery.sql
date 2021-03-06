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