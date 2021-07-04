drop table if exists authorities;
drop table if exists users;
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