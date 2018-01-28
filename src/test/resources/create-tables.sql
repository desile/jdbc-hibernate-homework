create table currency
(
  id serial primary key,
  full_name varchar(64) not null,
  short_name varchar(8) not null,
  update_timestamp timestamp not null,
  rate_to_dollar double precision not null
)
;


create table users
(
  id serial primary key,
  last_name varchar(100) not null,
  first_name varchar(100) not null,
  deleted boolean default false,
  creation_timestamp timestamp default now()
)
;


create table deposit
(
  id serial  primary key,
  owner_id integer
    constraint deposit_users_id_fk
    references users,
  open_timestamp timestamp default now(),
  expiration_timestamp timestamp,
  amount double precision default 0,
  interest double precision default 0,
  closed boolean default false
)
;