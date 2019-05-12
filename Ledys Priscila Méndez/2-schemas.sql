--create database bank;

drop schema if exists users cascade;

CREATE SCHEMA users;

CREATE TABLE users.bnk_usr_user(
  usr_code varchar(10) NOT NULL,
  usr_pwd varchar(200) NOT null,
  usr_tries numeric,
  usr_status varchar(1), -- A: active, I: inactive
  usr_last_login_date date NULL,
  usr_created_by varchar(25) NOT NULL DEFAULT USER,
  usr_created_date timestamp NOT NULL DEFAULT NOW(),
  usr_modified_by varchar(25) NULL,
  usr_modified_date timestamp,
  CONSTRAINT bnk_user_pk PRIMARY KEY (usr_code)
);

insert into users.bnk_usr_user(usr_code, usr_pwd, usr_tries, usr_status)
values ('LPMENDEZ','f6abd149295d60ccb5558539c3fddd539be00e69f34c4b952e154eb61fcfb26cdfd9dd0aed8cdeaa5e2758858697a85cec239621d11233b1e76f8cb4187a04c2', 0, 'A');
insert into users.bnk_usr_user(usr_code, usr_pwd, usr_tries, usr_status)
values ('PRUEBA','54234481fdc12eec1a2bba9780491ffdfa817667ac1f0f6aeb70fdde79ef36a4062afb6ce6b777907b2ba8634aa6fe3e7611ae46d7390109f96ad34afa86c9e2', 0, 'A');


drop schema if exists products cascade;

CREATE SCHEMA products;

CREATE TABLE products.bnk_acc_account( -- tipos de cuenta, de ahorro planilla, etc
  acc_code varchar(6) NOT NULL,
  acc_name varchar(25) not null,
  acc_description varchar(100),
  acc_created_by varchar(25) NOT NULL DEFAULT USER,
  acc_created_date timestamp NOT NULL DEFAULT NOW(),
  acc_modified_by varchar(25) NULL,
  acc_modified_date timestamp,
  CONSTRAINT bnk_acc_pk PRIMARY KEY (acc_code)
);


CREATE TABLE products.bnk_axu_account_user(
  axu_code varchar(10) not null, -- codigo de la cuenta, puede tener varias de ahorro
  axu_acccod varchar(6) NOT NULL,
  axu_usrcod varchar(25) not null,
  axu_balance numeric,
  axu_status varchar(1) not null default 'A',
  axu_created_by varchar(25) NOT NULL DEFAULT USER,
  axu_created_date timestamp NOT NULL DEFAULT NOW(),
  axu_modified_by varchar(25) NULL,
  axu_modified_date timestamp,
  CONSTRAINT bnk_axu_pk PRIMARY KEY (axu_code),
  constraint bnk_axu_acc_fk foreign key (axu_acccod) references products.bnk_acc_account(acc_code)
);


CREATE TABLE products.bnk_crd_card( --tipos de tarjeta
  crd_code varchar(6) NOT NULL,
  crd_name varchar(25) not null,
  crd_description varchar(100),
  crd_created_by varchar(25) NOT NULL DEFAULT USER,
  crd_created_date timestamp NOT NULL DEFAULT NOW(),
  crd_modified_by varchar(25) NULL,
  crd_modified_date timestamp,
  CONSTRAINT bnk_crd_pk PRIMARY KEY (crd_code)
);


CREATE TABLE products.bnk_cxu_card_user(
  cxu_code varchar(10) not null, -- codigo de la tarjeta, puede tener varias, silver, lm, platinum..
  cxu_crdcod varchar(6) NOT NULL,
  cxu_usrcod varchar(25) not null,
  cxu_limit numeric,
  cxu_available numeric,
  cxu_interest_rate numeric,
  cxu_interest_amount numeric,
  cxu_monthly_cut int,
  cxu_status varchar(1) not null default 'A',
  cxu_created_by varchar(25) NOT NULL DEFAULT USER,
  cxu_created_date timestamp NOT NULL DEFAULT NOW(),
  cxu_modified_by varchar(25) NULL,
  cxu_modified_date timestamp,
  CONSTRAINT bnk_cxu_pk PRIMARY KEY (cxu_code),
  constraint bnk_cxu_crd_fk foreign key (cxu_crdcod) references products.bnk_crd_card(crd_code)
);


CREATE TABLE products.bnk_loa_loan( --tipos de prestamos, hipotecario, personal, etc
  loa_code varchar(6) NOT NULL,
  loa_name varchar(25) not null,
  loa_description varchar(100),
  loa_created_by varchar(25) NOT NULL DEFAULT USER,
  loa_created_date timestamp NOT NULL DEFAULT NOW(),
  loa_modified_by varchar(25) NULL,
  loa_modified_date timestamp,
  CONSTRAINT bnk_loa_pk PRIMARY KEY (loa_code)
);


CREATE TABLE products.bnk_lxu_loan_user(
  lxu_code varchar(10) not null, -- codigo de la tarjeta, puede tener varias, silver, lm, platinum..
  lxu_loacod varchar(6) NOT NULL,
  lxu_usrcod varchar(25) not null,
  lxu_total numeric,
  lxu_debt numeric,
  lxu_interest_rate numeric,
  lxu_interest_amount numeric,
  lxu_status varchar(1) not null default 'A',
  lxu_created_by varchar(25) NOT NULL DEFAULT USER,
  lxu_created_date timestamp NOT NULL DEFAULT NOW(),
  lxu_modified_by varchar(25) NULL,
  lxu_modified_date timestamp,
  CONSTRAINT bnk_lxu_pk PRIMARY KEY (lxu_code),
  constraint bnk_lxu_loa_fk foreign key (lxu_loacod) references products.bnk_loa_loan(loa_code)
);


insert into products.bnk_acc_account(acc_code, acc_name, acc_description)
values ('ACCPER', 'Personal account', '');
insert into products.bnk_acc_account(acc_code, acc_name, acc_description)
values ('ACCSAV', 'Saving account', 'Savings');

insert into products.bnk_crd_card(crd_code, crd_name, crd_description)
values ('CRDSIL', 'Silver card', 'Silver credit card');
insert into products.bnk_crd_card(crd_code, crd_name, crd_description)
values ('CRDPLA', 'Platinum card', 'Platinum credit card');

insert into products.bnk_loa_loan(loa_code, loa_name, loa_description)
values ('LOAMOR', 'Mortgage', '');
insert into products.bnk_loa_loan(loa_code, loa_name, loa_description)
values ('LOAPER', 'Personal loan', '');

-- los id de cuentas son unicos para cualquier producto deben serlo
insert into products.bnk_axu_account_user(axu_code,axu_acccod, axu_usrcod, axu_balance)
values ('0000001', 'ACCSAV', 'LPMENDEZ', 50.09);
insert into products.bnk_axu_account_user(axu_code,axu_acccod, axu_usrcod, axu_balance)
values ('0000002', 'ACCPER', 'LPMENDEZ', 300.45);
insert into products.bnk_lxu_loan_user(lxu_code, lxu_loacod, lxu_usrcod, 
lxu_debt, lxu_interest_amount, lxu_interest_rate, lxu_total)
values('0000003','LOAPER', 'LPMENDEZ', 3250.43,125.43, 5.25, 7500);

insert into products.bnk_axu_account_user(axu_code,axu_acccod, axu_usrcod, axu_balance)
values ('0000004', 'ACCPER', 'PRUEBA', 300.45);
insert into products.bnk_cxu_card_user(cxu_code, cxu_crdcod, cxu_usrcod, cxu_limit, cxu_available,
cxu_interest_rate, cxu_interest_amount,cxu_monthly_cut)
values ('0000005', 'CRDSIL', 'PRUEBA', 8000, 7405.70, 10.5, 100.40, 18);

-- select * from products.bnk_axu_account_user;
-- select * from products.bnk_cxu_card_user;
-- select * from products.bnk_lxu_loan_user;

drop schema if exists transactions cascade;

CREATE SCHEMA transactions;

CREATE TABLE transactions.bnk_tra_transaction( 
  tra_code serial NOT NULL, --autorización
  tra_accid varchar(10) not null, --id de producto involucrado
  tra_usrcod varchar(10) not null,
  tra_description varchar(200),
  tra_amount numeric,
  tra_status varchar(1), -- si dio error, o está pendiente..
  tra_created_by varchar(25) NOT NULL DEFAULT USER,
  tra_created_date timestamp NOT NULL DEFAULT NOW(),
  tra_modified_by varchar(25) NULL,
  tra_modified_date timestamp,
  CONSTRAINT bnk_acc_pk PRIMARY KEY (tra_code)
);

insert into transactions.bnk_tra_transaction(tra_accid, tra_usrcod, tra_description, tra_amount, 
tra_status, tra_created_date)
values('0000001', 'LPMENDEZ','', -100, 'A', '2019-02-14');

insert into transactions.bnk_tra_transaction(tra_accid, tra_usrcod, tra_description, tra_amount, 
tra_status, tra_created_date)
values('0000002', 'LPMENDEZ','Descripción corta', 100, 'A', '2019-02-14');

insert into transactions.bnk_tra_transaction(tra_accid, tra_usrcod, tra_description, tra_amount, 
tra_status, tra_created_date)
values('0000001', 'LPMENDEZ','', 34.50, 'A', '2019-01-07');

insert into transactions.bnk_tra_transaction(tra_accid, tra_usrcod, tra_description, tra_amount, 
tra_status, tra_created_date)
values('0000001', 'LPMENDEZ','', 30.90, 'A', '2019-03-05');

-- select * from transactions.bnk_tra_transaction --where tra_created_date;

drop schema if exists beneficiary cascade;

CREATE SCHEMA beneficiary;


CREATE TABLE beneficiary.bnk_ben_beneficiary( 
  ben_code serial NOT NULL,
  ben_usrcod varchar(10) NOT NULL,
  ben_name varchar(50) not null, --será el tipo de producto involucrado.
  ben_account varchar(10) not null, --id de producto 
  ben_type varchar(5), -- producto, LOAN, ACC, CARD
  ben_email varchar(30),
  ben_status varchar(1), --A:activo, I:inactivo
  ben_created_by varchar(25) NOT NULL DEFAULT USER,
  ben_created_date timestamp NOT NULL DEFAULT NOW(),
  ben_modified_by varchar(25) NULL,
  ben_modified_date timestamp,
  CONSTRAINT bnk_acc_pk PRIMARY KEY (ben_code)
);

insert into beneficiary.bnk_ben_beneficiary(ben_usrcod, ben_name, ben_account, ben_type, ben_email, ben_status)
values('LPMENDEZ', 'Prueba', '0000004', 'ACC', 'prueba@test.com', 'A');
insert into beneficiary.bnk_ben_beneficiary(ben_usrcod, ben_name, ben_account, ben_type, ben_email, ben_status)
values('PRUEBA', 'Ledys Méndez', '0000003', 'LOAN', 'ledys@test.com', 'A');
insert into beneficiary.bnk_ben_beneficiary(ben_usrcod, ben_name, ben_account, ben_type, ben_email, ben_status)
values('LPMENDEZ', 'Prueba tarjeta', '0000005', 'CARD', 'prueba@test.com', 'A');
--cuenta para eliminar (desactivar)
insert into beneficiary.bnk_ben_beneficiary(ben_usrcod, ben_name, ben_account, ben_type, ben_email, ben_status)
values('PRUEBA', 'Ledys savings', '0000001', 'ACC', 'prueba@test.com', 'A');
--select * from beneficiary.bnk_ben_beneficiary;
--select * from users.bnk_usr_user;
-- delete from beneficiary.bnk_ben_beneficiary;