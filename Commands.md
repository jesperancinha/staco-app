# Stamps and Coins App - List of commands

>Make sure to Enable Kubernetes Cluster on Docker Desktop
>Make sure to Reset Kubernetes Cluster

```shell
brew install kubectl kubelet kubectl
brew install helm
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
```

## References

- [How to Install Kubernetes on Ubuntu 18.04](https://phoenixnap.com/kb/install-kubernetes-on-ubuntu)
- [kubectl Cheat Sheet](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)
