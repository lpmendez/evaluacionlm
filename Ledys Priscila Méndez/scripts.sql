--create database bank;
drop schema if exists users cascade;

CREATE SCHEMA users;

CREATE TABLE users.bnk_usr_user(
  usr_user varchar(10) NOT NULL,
  usr_pwd varchar(100) NOT null,
  usr_tries numeric,
  usr_status varchar(1), -- A: active, I: inactive
  usr_last_login_date date NULL,
  usr_created_by varchar(25) NOT NULL DEFAULT USER,
  usr_created_date timestamp NOT NULL DEFAULT NOW(),
  usr_modified_by varchar(25) NULL,
  usr_modified_date timestamp,
  CONSTRAINT bnk_user_pk PRIMARY KEY (usr_code)
);



drop schema if exists accounts cascade;

CREATE SCHEMA accounts;

CREATE TABLE accounts.bnk_acc_account(
  acc_code varchar(10) NOT NULL,
  acc_name varchar(25) not null,
  acc_description varchar(100),
  acc_created_by varchar(25) NOT NULL DEFAULT USER,
  acc_created_date timestamp NOT NULL DEFAULT NOW(),
  acc_modified_by varchar(25) NULL,
  acc_modified_date timestamp,
  CONSTRAINT bnk_account_pk PRIMARY KEY (acc_code)
);
