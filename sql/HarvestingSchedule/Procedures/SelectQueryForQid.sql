IF OBJECT_ID ( 'SelectQueryForQid', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectQueryForQid;
GO
CREATE PROCEDURE SelectQueryForQid 
    @qid int
    
    
AS 
   select * from query where id = 53@qid
GO