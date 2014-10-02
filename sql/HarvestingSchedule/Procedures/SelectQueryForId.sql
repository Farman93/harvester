IF OBJECT_ID ( 'SelectQueryForId', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectQueryForId;
GO
CREATE PROCEDURE SelectQueryForId 
    
   @qid int
AS 
   select * from [Query] where id = @qid
GO