IF OBJECT_ID ( 'CheckCookie', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckCookie;
GO
CREATE PROCEDURE CheckCookie 
    @ck int
AS 
   SELECT* from [User] where cookie = @ck
GO