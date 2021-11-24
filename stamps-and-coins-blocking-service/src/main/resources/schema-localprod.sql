drop table if exists authorities;
drop table if exists users;
drop sequence if exists hibernate_sequence;
drop table if exists oauth_access_token;

drop table if exists oauth_refresh_token;

create sequence hibernate_sequence;

create table oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          bytea,
    authentication bytea
);

create table oauth_access_token
(
    authentication_id VARCHAR(256),
    token_id          VARCHAR(256),
    token             bytea,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    bytea,
    refresh_token     VARCHAR(256)
);
