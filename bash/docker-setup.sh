#!/bin/bash
export NODE_PORT=4566
export NODE_IP=127.0.0.1
export LOCAL_STACK=http://$NODE_IP:$NODE_PORT
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=eu-central-1
echo http://$NODE_IP:$NODE_PORT
alias aws="aws --endpoint-url $LOCAL_STACK"

aws ssm put-parameter --name /dev/postgres/username --value "postgres"
aws ssm put-parameter --name /dev/postgres/password --value "password"

aws ssm put-parameter --name /config/StaCoLsService/username --value "postgres"
aws ssm put-parameter --name /config/StaCoLsService/password --value "password"
aws ssm put-parameter --name /config/application/server.port --value 8080


aws ssm put-parameter --name /config/StaCoLsService/dev/username --value "postgres"
aws ssm put-parameter --name /config/StaCoLsService/dev/password --value "password"

