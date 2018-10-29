ALTER TABLE reserves DROP serial_number;

CREATE TABLE job_transactions (
  id                INT AUTO_INCREMENT,
  reserve_id        INT NOT NULL DEFAULT 0,
  job_type          ENUM('cron', 'once') NOT NULL DEFAULT 'cron',
  job_id            VARCHAR(300) NOT NULL,
  job_name          VARCHAR(300) NOT NULL,
  job_status        VARCHAR(300) NOT NULL DEFAULT 'STOPPED',
  executed_at       DATETIME NOT NULL,
  finished_at       DATETIME NOT NULL,
  failed_at         DATETIME,
  fail_message      VARCHAR(300),
  cancled_at        DATETIME,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

