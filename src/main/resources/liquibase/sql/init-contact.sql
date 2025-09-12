-- create table for contacts
create table if not exists conference_room.contact (
    user_Id int NOT NULL references conference_room.user(id),
	contact_id int NOT NULL references conference_room.user(id), 
	Primary Key (user_Id, contact_id)
);
-- add description
comment on table conference_room.contact is 'This table is intended to store user contacts.';
