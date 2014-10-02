IF OBJECT_ID ('QueryDelete', 'TR') IS NOT NULL
   DROP TRIGGER QueryDelete;
GO
CREATE TRIGGER QueryDelete
ON Query
INSTEAD OF DELETE
AS
DELETE FROM Schedule
WHERE query_id IN (select id from deleted)
DELETE FROM Query
Where id in (select id from deleted)