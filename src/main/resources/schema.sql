CREATE DATABASE IF NOT EXISTS hrms_db;
USE hrms_db;

CREATE TABLE IF NOT EXISTS organizations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  address VARCHAR(255) NOT NULL,
  pan VARCHAR(10) NOT NULL,
  gst VARCHAR(20),
  department VARCHAR(100),
  email VARCHAR(150) NOT NULL UNIQUE,
  password_hash VARCHAR(64) NOT NULL,
  employee_count INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS employees (
  id INT AUTO_INCREMENT PRIMARY KEY,
  organization_id INT NOT NULL,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(150),
  phone VARCHAR(20),
  department VARCHAR(100),
  designation VARCHAR(100),
  FOREIGN KEY (organization_id) REFERENCES organizations(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS attendance (
  id INT AUTO_INCREMENT PRIMARY KEY,
  organization_id INT NOT NULL,
  employee_id INT NOT NULL,
  attendance_date DATE NOT NULL,
  status ENUM('PRESENT','ABSENT','LEAVE') NOT NULL,
  FOREIGN KEY (organization_id) REFERENCES organizations(id) ON DELETE CASCADE,
  FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS salaries (
  id INT AUTO_INCREMENT PRIMARY KEY,
  organization_id INT NOT NULL,
  employee_id INT NOT NULL,
  salary_month VARCHAR(7) NOT NULL,
  basic DECIMAL(12,2) NOT NULL,
  allowances DECIMAL(12,2) NOT NULL DEFAULT 0,
  deductions DECIMAL(12,2) NOT NULL DEFAULT 0,
  net_salary DECIMAL(12,2) NOT NULL,
  FOREIGN KEY (organization_id) REFERENCES organizations(id) ON DELETE CASCADE,
  FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS notices (
  id INT AUTO_INCREMENT PRIMARY KEY,
  organization_id INT NOT NULL,
  title VARCHAR(150) NOT NULL,
  message TEXT NOT NULL,
  notice_date DATE NOT NULL,
  FOREIGN KEY (organization_id) REFERENCES organizations(id) ON DELETE CASCADE
);
