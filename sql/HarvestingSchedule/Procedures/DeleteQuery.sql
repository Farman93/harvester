IF OBJECT_ID ( 'DeleteQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE DeleteQuery;
GO
CREATE PROCEDURE DeleteQuery 
    @qid int,
    @uid int
AS 
   delete from Query where @qid = id and @uid = [user_id]
GO