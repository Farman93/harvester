IF OBJECT_ID ( 'CheckPass', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckPass;
GO
CREATE PROCEDURE CheckPass 
    @lg nvarchar(max),
    @pw nvarchar(max)
AS 
   SELECT* from [User] where login = @lg and password = @pw
GO