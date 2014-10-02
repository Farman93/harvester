IF OBJECT_ID ( 'SelectProtocolForId', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectProtocolForId;
GO
CREATE PROCEDURE SelectProtocolForId 
    
   @pid int
AS 
   select [name] from [protocol] where id = @pid
GO