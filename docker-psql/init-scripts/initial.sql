drop table if exists sta_co;

create table if not exists sta_co
(
    id                  bigint not null
        constraint staco_pkey
            primary key,
    description         varchar(255),
    year                varchar(255),
    value               varchar(255),
    currency            varchar(255),
    diameter_mm          varchar(255),
    internal_diameter_mm varchar(255),
    height_mm            varchar(255),
    width_mm             varchar(255)
);

alter table sta_co owner to postgres;

drop
type if exists sta_co_json;
create type sta_co_json as
(id bigint ,
    description varchar (255),
    "year" varchar (255),
    "value" varchar (255),
    currency varchar (255),
    "diameterMM" varchar (255),
    "internalDiameterMM" varchar (255),
    "heightMM" varchar (255),
    "widthMM" varchar (255));

\set content `cat /docker-entrypoint-initdb.d/stamps_coins.json`

with sta_co_json (doc) as (
    values
    (:'content'::json)
)

insert
into sta_co (id, description, year, value, currency, diametermm, internal_diametermm, heightmm, widthmm)
select ROW_NUMBER() OVER (
    ORDER BY description
    ),
       description,
       "year",
       "value",
       currency,
       "diameterMM"         as diameter_mm,
       "internalDiameterMM" as internal_diameter_mm,
       "heightMM"           as height_mm,
       "widthMM"            as width_mm
from sta_co_json l
         cross join lateral json_populate_recordset(null::sta_co_json, doc);
