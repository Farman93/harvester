IF OBJECT_ID ( 'SelectScheduleForQuery', 'P' ) IS NOT NULL 
    DROP PROCEDURE SelectScheduleForQuery;
GO
CREATE PROCEDURE SelectScheduleForQuery 
    @qid int
    
AS 
   select * from schedule where query_id = @qid
GO