-- create database
create database conference;
-- create user
create user conference_admin with password 'conference_admin_password';
-- grant privileges to user
grant all privileges on database conference to conference_admin;

-- create default user
insert into conference_room.user (login, name, password, role) values ('admin', 'admin', 'cnYn3YN48IlzflY4xQuwqGwv1jUtYRSP33sF5X5JrT/ar9AT4fBA1Q==', 'ADMIN');