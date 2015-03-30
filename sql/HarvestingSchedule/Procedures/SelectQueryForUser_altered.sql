USE [HarvestingSchedule]
GO
/****** Объект:  StoredProcedure [dbo].[SelectQueryForUser]    Дата сценария: 04/18/2013 15:40:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SelectQueryForUser] 
    @lg nvarchar(MAX)
    
    
AS 
   select query.id,[name],endURL,startURL,protocol_id,convert(char(10), [time], 104) +' '+ convert(char(5), [time], 114) as [time],regularity,[user_id],struct_loc,last_succ,active from query,[user] where [user_id] = [user].id and [login] = @lg


