-- This file does nothing. It is here only to demonstrate the changes in the create databases script
create table if not exists sta_co
(
    id                  varchar(255) not null
        constraint staco_pkey
            primary key,
    description         varchar(255),
    year                varchar(255),
    value               varchar(255),
    currency            varchar(255),
    type                varchar(255),
    diameter_mm          varchar(255),
    internal_diameter_mm varchar(255),
    height_mm            varchar(255),
    width_mm             varchar(255),
    version              int
);
