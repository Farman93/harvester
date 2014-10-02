IF OBJECT_ID ( 'SelectUser', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectUser;
GO
CREATE PROCEDURE SelectUser 
    @lg nvarchar(max)
AS 
   SELECT* from [User] where login = @lg
GO