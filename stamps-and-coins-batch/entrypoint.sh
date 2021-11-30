#!/usr/bin/env bash

java -jar -Dspring.profiles.active=${STACO_APP_BACKEND_PROFILE} stamps-and-coins-batch.jar
