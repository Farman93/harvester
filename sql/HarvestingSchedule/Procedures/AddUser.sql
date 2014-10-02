IF OBJECT_ID ( 'AddUser', 'P' ) IS NOT NULL 
    DROP PROCEDURE AddUser;
GO
CREATE PROCEDURE AddUser 
    @lg nvarchar(max),
    @pw nvarchar(max)
AS 
   INSERT INTO [User]([login], password) VALUES(@lg, @pw)
GO