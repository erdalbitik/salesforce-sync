CREATE TABLE access_account (
	id int8 NOT NULL,
	"label" varchar(255) NULL,
	"password" varchar(255) NULL,
	scheduled bool NOT NULL default false,
	sync_period int4 NULL,
	username varchar(255) NULL,
	CONSTRAINT access_account_pkey PRIMARY KEY (id)
);