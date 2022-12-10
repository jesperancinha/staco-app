#!/usr/bin/env sh
java -jar -Daws.endpoint=http://localstack:4566 -Dspring.profiles.active=docker stamps-and-coins-ls-service.jar
