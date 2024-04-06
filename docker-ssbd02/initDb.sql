CREATE DATABASE ssbd02;
CREATE USER ssbd02admin password 'adminP@ssw0rd';
CREATE USER ssbd02mok password 'mokP@ssw0rd';
CREATE USER ssbd02mol password 'molP@ssw0rd';
CREATE USER ssbd02auth password 'authP@ssw0rd';
\c ssbd02;
GRANT ALL ON SCHEMA public TO ssbd02admin;