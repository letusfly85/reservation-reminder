ALTER TABLE reserves ADD all_day_flag boolean DEFAULT false AFTER command;
ALTER TABLE reserves ADD cron_cycle VARCHAR(500) AFTER all_day_flag;
