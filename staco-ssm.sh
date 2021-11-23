#!/bin/bash
aws ssm --endpoint-url http://192.168.0.11:4566 put-parameter --name /config/StaCoLsService/dev/username --value postgres --type text
aws ssm --endpoint-url http://192.168.0.11:4566 put-parameter --name /config/StaCoLsService/dev/password --value password --type text
