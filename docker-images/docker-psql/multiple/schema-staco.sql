drop table if exists sta_co;

create table if not exists sta_co
(
    id uuid DEFAULT gen_random_uuid(),
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

alter table sta_co owner to postgres;
