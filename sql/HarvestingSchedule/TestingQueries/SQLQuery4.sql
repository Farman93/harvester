set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SelectQueryForUser] 
    @lg nvarchar(MAX)
    
    
AS 
   select query.id,[name],endURL,startURL,protocol_id,[time],regularity,[user_id],struct_loc,last_succ,active from query,[user] where [user_id] = [user].id and [login] = @lg


