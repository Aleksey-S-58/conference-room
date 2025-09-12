-- create table for chat
create table if not exists conference_room.chat (
    id int Primary Key GENERATED ALWAYS AS IDENTITY,
	owner_id int references conference_room.user(id), 
	name varchar(50) NOT NULL
);
-- add description
comment on table conference_room.chat is 'This table is intended to store chat description.';

-- create table for chat participants
create table if not exists conference_room.participant (
    chat_Id int NOT NULL references conference_room.chat(id),
	user_Id int NOT NULL references conference_room.user(id), 
	Primary Key (chat_Id, user_Id)
);
-- add description
comment on table conference_room.participant is 'This table is intended to store chat participants.';

-- create table to store attachments
create table if not exists conference_room.attachment (
	id bigint Primary Key GENERATED ALWAYS AS IDENTITY,
	media_type varchar(50),
	file_name varchar(250) NOT NULL,
	bytes bytea
);
comment on table conference_room.attachment is 'This table is intended to store attachments for messages.';

-- create table to store messages
create table if not exists conference_room.message (
	id bigint Primary Key GENERATED ALWAYS AS IDENTITY,
	chat_Id int NOT NULL references conference_room.chat(id),
	user_Id int NOT NULL references conference_room.user(id), 
	attachment_id bigint references conference_room.attachment(id),
	message_order bigint NOT NULL,
	message_text text,
	message_timestamp TIMESTAMP WITH TIME ZONE default current_timestamp,
	delivered BOOLEAN NOT NULL DEFAULT FALSE,
	read BOOLEAN NOT NULL DEFAULT FALSE,
	edited BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON conference_room.message(chat_Id);
CREATE INDEX ON conference_room.message(user_Id);
CREATE INDEX ON conference_room.message(attachment_id);
CREATE INDEX ON conference_room.message(message_order);
comment on table conference_room.message is 'This table is intended to messages.';
