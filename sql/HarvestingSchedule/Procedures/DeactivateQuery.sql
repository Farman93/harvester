IF OBJECT_ID ( 'DeactivateQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE DeactivateQuery;
GO
CREATE PROCEDURE DeactivateQuery 
    @qid int,
    @uid int
AS 
   update query set active = 0 where @qid = query.id and [user_id] = @uid
GO