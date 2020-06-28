--liquibase formatted sql

--changeset marcopollivier:1

 CREATE TABLE users (
	id uuid NOT NULL,
	email varchar(255) NOT NULL,
	full_name varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);



--rollback DROP TABLE users;