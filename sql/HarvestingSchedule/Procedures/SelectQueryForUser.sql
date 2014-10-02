IF OBJECT_ID ( 'SelectQueryForUser', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectQueryForUser;
GO
CREATE PROCEDURE SelectQueryForUser 
    @uid int
    
    
AS 
   select * from query where [user_id] = @uid
GO