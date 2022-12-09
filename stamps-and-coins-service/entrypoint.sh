#!/usr/bin/env sh
java -jar -Dpostgres.host=postgres -Daws.endpoint=http://localstack:4566 -Dspring.profiles.active=docker stamps-and-coins-service.jar
