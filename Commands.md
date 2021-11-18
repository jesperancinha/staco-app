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


## References

- [How to Install Kubernetes on Ubuntu 18.04](https://phoenixnap.com/kb/install-kubernetes-on-ubuntu)
- [kubectl Cheat Sheet](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)
- [LocalStack](https://github.com/localstack/localstack)
- [LocalStack Cloud](https://localstack.cloud/)
