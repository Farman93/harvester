IF OBJECT_ID ( 'CheckProtocol', 'P' ) IS NOT NULL 
    DROP PROCEDURE CheckProtocol;
GO
CREATE PROCEDURE CheckProtocol 
    @pid int
    
AS 
   select * from [protocol] where id = @pid
GO