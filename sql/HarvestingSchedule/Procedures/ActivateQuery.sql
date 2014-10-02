IF OBJECT_ID ( 'ActivateQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE ActivateQuery;
GO
CREATE PROCEDURE ActivateQuery 
    @qid int,
    @uid int
AS 
   update query set active = 1 where @qid = query.id and [user_id] = @uid
GO