-- create schema
create schema if not exists conference_room;
-- create table for user accounts
create table if not exists conference_room.user (
    id int Primary Key GENERATED ALWAYS AS IDENTITY,
	login varchar(50) unique NOT NULL, 
	name varchar(50) NOT NULL,
	password varchar(64) NOT NULL,
	role varchar(20) NOT NULL
);
-- add description
comment on table conference_room.user is 'This table is intended to store user credentials (the password is SHA256 representation of a password).';