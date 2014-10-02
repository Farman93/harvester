CREATE VIEW NextHarvest
	AS
	SELECT TOP (1) query_id, id, date_time, attempts
	FROM Schedule
	WHERE (date_time >= GETDATE() AND status_id = 0)
	ORDER BY date_time;