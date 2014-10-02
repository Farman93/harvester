CREATE VIEW MissedSchedule
	AS SELECT id, date_time, status_id
	FROM Schedule
	WHERE (date_time < DATEADD(mi, - 5, GETDATE())) AND (status_id = 0);	