#!/bin/bash
helm upgrade --install localstack localstack/localstack --namespace localstack --create-namespace --values values.yaml
# shellcheck disable=SC2155
export NODE_PORT=$(kubectl get --namespace localstack -o jsonpath="{.spec.ports[0].nodePort}" services localstack)
# shellcheck disable=SC2155
export NODE_IP=$(kubectl get nodes --namespace localstack -o jsonpath="{.items[0].status.addresses[0].address}")
export NODE_IP=127.0.0.1
export LOCAL_STACK=http://$NODE_IP:$NODE_PORT
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=eu-central-1
echo http://$NODE_IP:$NODE_PORT
alias aws="aws --endpoint-url $LOCAL_STACK"

aws ssm put-parameter --name /dev/postgres/username --value "postgres"
aws ssm put-parameter --name /dev/postgres/password --value "password"
