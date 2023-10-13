create table if not exists "user"
(
    user_id integer generated by default as identity
        primary key,
    email   varchar(64) not null,
    name    varchar(64) not null
);

alter table "user"
    owner to postgres;

create table if not exists wallet
(
    wallet_id integer generated by default as identity
        primary key,
    user_id   integer generated by default as identity
        constraint fk_user_id_user
            references "user",
    type      integer generated by default as identity,
    money     numeric(10, 2) not null,
    name      varchar(64)    not null
);

alter table wallet
    owner to postgres;

create table if not exists wallet_type
(
    type_int     integer generated by default as identity
        primary key,
    type_varchar varchar(64) not null
);

alter table wallet_type
    owner to postgres;

create table if not exists money_operation
(
    id                serial,
    user_id           bigint,
    operation_type    varchar,
    regularity_type   varchar,
    single_income_id  bigint,
    regular_income_id bigint,
    actual            boolean
);

alter table money_operation
    owner to postgres;

create table if not exists single_operation
(
    id                serial,
    wallet_id         bigint,
    amount_of_money   numeric(10, 2),
    date              date,
    completed         boolean,
    category          varchar,
    base_operation_id bigint
);

alter table single_operation
    owner to postgres;

create table if not exists regular_operation
(
    base_operation_id    bigint,
    id                   serial,
    period_of_regularity varchar,
    dates                integer[],
    amount_of_money      numeric(10, 2),
    name                 varchar
);

alter table regular_operation
    owner to postgres;

create table if not exists regular_operation_unit
(
    base_regular_operation_id bigint,
    id                        serial,
    real_amount_of_money      numeric(10, 2),
    unit_operation_date       date,
    completed                 boolean
);

alter table regular_operation_unit
    owner to postgres;

