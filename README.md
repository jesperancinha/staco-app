# StaCo - A Stamps and Coins application

---

[![Twitter URL](https://img.shields.io/twitter/url?logoColor=blue&style=social&url=https%3A%2F%2Fimg.shields.io%2Ftwitter%2Furl%3Fstyle%3Dsocial)](https://twitter.com/intent/tweet?text=%20Checkout%20this%20%40github%20repo%20by%20%40joaofse%20%F0%9F%91%A8%F0%9F%8F%BD%E2%80%8D%F0%9F%92%BB%3A%20https%3A//github.com/jesperancinha/staco-app)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=StaCo%20App%20&color=informational)](https://github.com/jesperancinha/staco-app)
[![GitHub release](https://img.shields.io/github/release-pre/jesperancinha/kotlin-test-drives.svg)](#)

[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinha/staco-app.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinha/staco-app.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinha/staco-app.svg)](#)

---

## Technologies used

---

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/kotlin-50.png "Kotlin")](https://kotlinlang.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/spring-50.png "Spring Framework")](https://spring.io/projects/spring-framework)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/spring-boot-50.png "Spring Boot")](https://spring.io/projects/spring-boot)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/spring-webflux-50.png "Spring Webfllux")](https://spring.io/projects/spring-boot)

---

## How to run

There are three modes to run this application:

1. The application runs with a local and hard-coded BASIC authentication
2. The application uses OAuth in combination with GitHub. You can make your own app with it
3. The application runs with a local (and outdated) local OAuth token repository under PostgreSQL

### Run details

1. Start docker compose with the provided [build.sh](./build.sh).

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

1.  In a non-oauth environment(default) use admin/admin as passwords.

2.  In a oauth-enabled environment enabled with github(prod) use your own github credentials

3.  In a local oauth-enabled environment with local tokens (locaprod) Login angular 2 page next...

##### Testing with OAuth

1. Main page

[http://localhost:4200](http://localhost:4200/)

2. Login with credentials admin/admin

3. Alternatively you can use OAuth2 token creation as described in this BASH list of commands:

```bash
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "grant_type=password&username=admin&password=admin&client_id=stamps-and-coins-client&client_secret=stamps-and-coins&scope=read&redirect_uri=http://localhost:8081/oauth" http://localhost:8081/oauth/token
curl -H "Authorization: Bearer TOKEN" http://localhost:4200/api/staco/all/
curl -H "Authorization: Bearer TOKEN" http://localhost:4200/api/staco/logout
```

## Sequence diagram

```mermaid

sequenceDiagram
    participant User
    participant StaCo App
    participant StaCo Service
    participant PostgreSQL Database
    
    rect rgb(200,200,200)
    
    User->>StaCo App: Use logs into application
    StaCo App->>StaCo Service: A list of coins and stamps is requested by filter
    StaCo Service->>PostgreSQL Database: Data is fetched via the filter
    PostgreSQL Database ->>StaCo Service: Data is returned
    StaCo Service->>StaCo App: Minimal data manipulationb and return
    StaCo App->>User: Results given to the User
    
    end

```

## Docker image

[![dockeri.co](https://dockeri.co/image/library/postgres)](https://hub.docker.com/r/library/postgres)

- Remove all Docker images and builds

```bash
docker system prune --all
```

## Import database file

```bash
psql -p 5433 -h localhost -U postgres -d staco -f initial.sql 
```