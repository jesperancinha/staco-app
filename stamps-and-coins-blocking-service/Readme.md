# Stamps and Coins Blocking Service

## Introduction

Hi there. This module is independent of the rest of the project and its goal is to explore security when using
authentication and authorization with Spring.

In this module you'll find 3 different implementations and code. In short, you'll find BASIC, OAUTH local and OAUTH2
with GitHub.

## How to startup

You can start this SpringBoot application without environment variables.
However, it will preferably use STACO_AWS_LOCALSTACK_IP as a variable to configure where localstack actually is.
You can also add STACO_AWS_LOCALSTACK_PORT to specify the port. It uses 4566 by default

There are three modes to run this application:

1.  The application runs with a local and hard-coded BASIC authentication
2.  The application uses OAuth in combination with GitHub. You can make your own app with it
3.  The application runs with a local (and outdated) local OAuth token repository under PostgreSQL

### Run details

1.  Start docker compose with the provided [build.sh](./build.sh).

If that doesn't work please try these steps:

```bash
docker-compose down
docker-compose up -d --build --remove-orphans
```

##### Run the backend

###### For local tests

```bash
cd stamps-and-coins-service
mvn clean install spring-boot:run
```

###### Using OAuth2 via GitHub

```bash
cd stamps-and-coins-service
mvn clean install spring-boot:run -Dspring-boot.run.profiles=prod -Dspring-boot.run.arguments="--staco.search.api.key=AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA --staco.search.api.client.id=AAAAAAAAAAAAAAAAAAAA"
```

###### Using OAuth2 via Local

```bash
cd stamps-and-coins-service
mvn clean install spring-boot:run -Dspring-boot.run.profiles=locaprod
```

##### Run Front end

```bash
cd stamps-and-coins-web
npm start
```

##### Testing

[http://localhost:4200](http://localhost:4200/)

1.  In a non-oauth environment(default) use admin/admin as username/password.

2.  In a oauth-enabled environment enabled with github(prod) use your own github credentials

3.  In a local oauth-enabled environment with local tokens (locaprod) Login angular 2 page next...

##### Testing with OAuth

1.  Main page

[http://localhost:4200](http://localhost:4200/)

2.  Login with credentials admin/admin

3.  Alternatively you can use OAuth2 token creation as described in this BASH list of commands:

```bash
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "grant_type=password&username=admin&password=admin&client_id=stamps-and-coins-client&client_secret=stamps-and-coins&scope=read&redirect_uri=http://localhost:8081/oauth" http://localhost:8081/oauth/token
curl -H "Authorization: Bearer TOKEN" http://localhost:4200/api/staco/all/
curl -H "Authorization: Bearer TOKEN" http://localhost:4200/api/staco/logout
```

## Docker image

[![dockeri.co](https://dockeri.co/image/library/postgres)](https://hub.docker.com/r/library/postgres)

-   Remove all Docker images and builds

```bash
docker system prune --all
```

## Import database file

```bash
psql -p 5433 -h localhost -U postgres -d staco -f initial-staco.sql 
```

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
