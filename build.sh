#!/usr/bin/env bash
docker-compose down
mvn clean install
docker-compose up -d --build --remove-orphans
