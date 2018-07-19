CREATE TABLE reserves (
  id                INT AUTO_INCREMENT,
  name              VARCHAR(300) NOT NULL,
  description       VARCHAR(2000),
  reserved_from     DATETIME NOT NULL,
  reserved_to       DATETIME NOT NULL,
  job_id            VARCHAR(300) NOT NULL,
  job_status        VARCHAR(300) NOT NULL DEFAULT 'PENDING',
  serial_number     VARCHAR(300) NOT NULL,
  executed_at       DATETIME,
  cancled_at        DATETIME,
  created_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

