IF OBJECT_ID ( 'SelectProtocols', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectProtocols;
GO
CREATE PROCEDURE SelectProtocols 
    
    
AS 
   select id, [name] from [protocol]
GO