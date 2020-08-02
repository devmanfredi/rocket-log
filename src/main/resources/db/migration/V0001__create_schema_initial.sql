CREATE TABLE users (
	id uuid NOT NULL,
	full_name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE customer (
	id uuid NOT NULL,
	api_key uuid NOT NULL,
	created_date timestamp NOT NULL,
	updated_date timestamp NOT NULL,
	user_id uuid NOT NULL REFERENCES users(id),
	CONSTRAINT customer_pkey PRIMARY KEY (id)
);