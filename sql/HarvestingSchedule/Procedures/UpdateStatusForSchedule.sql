IF OBJECT_ID ( 'UpdateCookie', 'P' ) IS NOT NULL 
    DROP PROCEDURE UpdateCookie;
GO
CREATE PROCEDURE UpdateCookie 
    @login varchar(max),
    @cookie int
    
    
AS 	
   UPDATE [User]
   set cookie = @cookie 
   where [login] = @login
GO