drop table if exists authorities;
drop table if exists users;
drop table if exists sta_co;
create table users
(
    username varchar(50)  not null primary key,
    password varchar(255) not null,
    enabled  boolean      not null
);
create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);


create table sta_co
(
    id                   varchar(50),
    description          varchar(50),
    year                 varchar(50),
    value                varchar(50),
    currency             varchar(50),
    height_mm            varchar(50),
    width_mm             varchar(50),
    diameter_mm          varchar(50),
    internal_diameter_mm varchar(50),
    version              int
);