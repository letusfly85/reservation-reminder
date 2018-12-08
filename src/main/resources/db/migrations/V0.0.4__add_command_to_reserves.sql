ALTER TABLE reserves ADD command VARCHAR(2000) AFTER description;
ALTER TABLE reserves ADD notification_id VARCHAR(128) AFTER command;
ALTER TABLE reserves DROP job_id;
ALTER TABLE reserves DROP job_status;

