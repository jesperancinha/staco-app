## Troubleshooting

1.  If you are running the integration tests on your local machine and realize that the containers remain still and don't respond for a while, this could be a sign of a problem with Docker machine. Please restart your docker desktop/docker-machine and try again.
2.  If for some reason you cannot start docker-compose and Docker gives this response:

```shell
Creating network "staco-app_staco_net" with driver "bridge"
ERROR: Pool overlaps with other one on this address space
make: *** [docker] Error 1
```

then this just means that you may still have docker containers running with a network bridge driver. You need to stop them and then remove the network with `docker network prune`.
