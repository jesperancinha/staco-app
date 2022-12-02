drop type if exists sta_co_json;
create type sta_co_json as
(id varchar(255) ,
    description varchar (255),
    "year" varchar (255),
    "value" varchar (255),
    currency varchar (255),
    "type" varchar (255),
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
into sta_co(description, year, value, currency, type, diameter_mm, internal_diameter_mm, height_mm, width_mm)
select
       description,
       "year",
       "value",
       "currency",
       "type",
       "diameterMM"         as diameter_mm,
       "internalDiameterMM" as internal_diameter_mm,
       "heightMM"           as height_mm,
       "widthMM"            as width_mm
from sta_co_json l
         cross join lateral json_populate_recordset(null::sta_co_json, doc);
