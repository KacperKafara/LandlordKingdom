CREATE DATABASE ssbd02;
CREATE USER 'ssbd02admin'@'%' IDENTIFIED BY 'adminP@ssw0rd';
CREATE USER 'ssbd02mok'@'%' IDENTIFIED BY 'mokP@ssw0rd';
CREATE USER 'ssbd02mol'@'%' IDENTIFIED BY 'molP@ssw0rd';
CREATE USER 'ssbd02auth'@'%' IDENTIFIED BY 'authP@ssw0rd';
USE ssbd02;

GRANT RELOAD ON *.* TO 'ssbd02admin'@'%';
GRANT ALL PRIVILEGES ON ssbd02.* TO 'ssbd02admin'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON ssbd02.* TO 'ssbd02admin'@'localhost' WITH GRANT OPTION;

FLUSH PRIVILEGES;
