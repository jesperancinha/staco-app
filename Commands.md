# Stamps and Coins App - List of commands

>Make sure to Enable Kubernetes Cluster on Docker Desktop
>Make sure to Reset Kubernetes Cluster

```shell
brew install kubectl kubelet kubectl
brew install helm aws
helm repo add localstack http://helm.localstack.cloud
helm repo update
helm upgrade --install localstack localstack/localstack --namespace localstack --create-namespace --values values.yaml
```

## Extras

```shell
kubectl config use-context docker-for-desktop
kubectl config view --raw >~/.kube/config
```

## Test Variables

```shell
export KUBERNETES_MASTER=http://127.0.0.1:8080
export KUBECONFIG=/etc/rancher/k3s/k3s.yaml
```

## Checks

```shell
cat ~/.kube/config
export NODE_PORT=$(kubectl get --namespace localstack -o jsonpath="{.spec.ports[0].nodePort}" services localstack)
export NODE_IP=$(kubectl get nodes --namespace localstack -o jsonpath="{.items[0].status.addresses[0].address}")
echo http://$NODE_IP:$NODE_PORT
```

## Locally

```shell
export NODE_IP=127.0.0.1
export LOCAL_STACK_BACKEND=http://$NODE_IP:$NODE_PORT
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=eu-central-1
```

## AWS commands

```shell
alias aws="aws --endpoint-url $LOCAL_STACK"
aws s3api list-buckets
aws s3api create-bucket --bucket staco
aws s3api put-object --bucket staco --key warehouse1 --body docker-psql/init-scripts/stamps_coins.json
aws s3api list-objects --bucket staco
aws s3api get-object --bucket staco --key warehouse1 test.json
aws s3api delete-object --bucket staco --key warehouse1
aws s3api delete-bucket --bucket staco
aws rds create-db-instance --db-instance-identifier staco-app --db-instance-class c1 --engine postgres
aws ecr create-repository --repository-name staco-app
kubectl --namespace localstack logs --selector app.kubernetes.io/name=localstack --tail 100
aws eks create-cluster --name staco-cluster --role-arn staco-role --resources-vpc-config '{}'
aws eks list-clusters
aws configure
aws eks describe-cluster --name staco-cluster
aws dynamodb list-tables
aws ssm put-parameter --name love --value "What is love"
aws ssm get-parameter --name love
aws ssm describe-parameters
aws ssm put-parameter --name /dev/postgres/username --value "postgres"
aws ssm put-parameter --name /dev/postgres/password --value "password"
aws s3api get-object --bucket staco --key staco-image-e4b80aa3-5b49-49b4-829a-463501279615.png test.png
```

## References

- [AWS SDK: “Unable to locate credentials”, a cheat sheet for solving the issue](https://faun.pub/aws-sdk-unable-to-locate-credentials-a-cheat-sheet-for-solving-the-issue-f72f8965a2c1 )
- [How to Install Kubernetes on Ubuntu 18.04](https://phoenixnap.com/kb/install-kubernetes-on-ubuntu)
- [kubectl Cheat Sheet](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)
- [LocalStack](https://github.com/localstack/localstack)
- [LocalStack Cloud](https://localstack.cloud/)
