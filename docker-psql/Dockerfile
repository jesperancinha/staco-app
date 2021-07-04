FROM postgres:11.5-alpine

COPY postgresql.conf /etc/postgresql/postgresql.conf

EXPOSE 5433

COPY init-scripts/initial.sql /docker-entrypoint-initdb.d/initial.sql

COPY init-scripts/stamps_coins.json /docker-entrypoint-initdb.d/stamps_coins.json

