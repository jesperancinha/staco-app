#!/usr/bin/env bash
export STACO_AWS_LOCALSTACK_IP=${STACO_AWS_LOCALSTACK_IP}
java -jar -Dpostgres.host=${STACO_AWS_POSTGRESQL_IP} -Daws.endpoint=http://${STACO_AWS_LOCALSTACK_IP}:4566 -Dspring.profiles.active=docker stamps-and-coins-batch.jar
