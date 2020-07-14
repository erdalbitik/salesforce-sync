CREATE TABLE salesforce_account (
	id varchar(255) NOT NULL,
	name varchar(255) NULL,
	external_id varchar(255) NULL,
	annual_revenue numeric(19, 2) NULL,
	last_modified_date timestamp with time zone NULL,
	access_account_id int8 not null,
	CONSTRAINT salesforce_account_pkey PRIMARY KEY (id)
);

CREATE TABLE salesforce_contact (
	id varchar(255) NOT NULL,
	name varchar(255) NULL,
	email varchar(255) NULL,
	last_modified_date timestamp with time zone NULL,
	access_account_id int8 not null,
	CONSTRAINT salesforce_contact_pkey PRIMARY KEY (id)
);

CREATE TABLE salesforce_lead (
	id varchar(255) NOT NULL,
	name varchar(255) NULL,
	email varchar(255) NULL,
	last_modified_date timestamp with time zone NULL,
	access_account_id int8 not null,
	CONSTRAINT salesforce_lead_pkey PRIMARY KEY (id)
);