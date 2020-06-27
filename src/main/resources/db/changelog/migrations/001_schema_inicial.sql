--liquibase formatted sql

--changeset marcopollivier:1
CREATE TABLE IF NOT EXISTS users (
 id uuid NOT NULL GENERATED BY DEFAULT AS IDENTITY CONSTRAINT users_pkey PRIMARY KEY ,
 email VARCHAR(255) NOT NULL,
 full_name VARCHAR(255) NOT NULL,
 password varchar(255) NOT NULL
 );



--rollback DROP TABLE users;