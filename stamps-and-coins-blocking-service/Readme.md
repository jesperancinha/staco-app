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

## About me 👨🏽‍💻🚀🏳️‍🌈

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/JEOrgLogo-20.png "João Esperancinha Homepage")](http://joaofilipesabinoesperancinha.nl)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=social "GitHub")](https://github.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/mastodon-20.png "Mastodon")](https://masto.ai/@jesperancinha)
[![Twitter Follow](https://img.shields.io/twitter/follow/joaofse?label=João%20Esperancinha&style=social "Twitter")](https://twitter.com/joaofse)
| [Sessionize](https://sessionize.com/joao-esperancinha/)
| [Spotify](https://open.spotify.com/user/jlnozkcomrxgsaip7yvffpqqm?si=b54b89eae8894960)
| [Medium](https://medium.com/@jofisaes)
| [Buy me a coffee](https://www.buymeacoffee.com/jesperancinha)
| [Credly Badges](https://www.credly.com/users/joao-esperancinha)
| [Google Apps](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
| [Sonatype Search Repos](https://search.maven.org/search?q=org.jesperancinha)
| [Docker Images](https://hub.docker.com/u/jesperancinha)
| [Stack Overflow Profile](https://stackoverflow.com/users/3702839/joao-esperancinha)
| [Reddit](https://www.reddit.com/user/jesperancinha/)
| [Dev.TO](https://dev.to/jofisaes)
| [Hackernoon](https://hackernoon.com/@jesperancinha)
| [Code Project](https://www.codeproject.com/Members/jesperancinha)
| [BitBucket](https://bitbucket.org/jesperancinha)
| [GitLab](https://gitlab.com/jesperancinha)
| [Coursera](https://www.coursera.org/user/da3ff90299fa9297e283ee8e65364ffb)
| [FreeCodeCamp](https://www.freecodecamp.org/jofisaes)
| [HackerRank](https://www.hackerrank.com/jofisaes)
| [LeetCode](https://leetcode.com/jofisaes)
| [Codebyte](https://coderbyte.com/profile/jesperancinha)
| [CodeWars](https://www.codewars.com/users/jesperancinha)
| [Code Pen](https://codepen.io/jesperancinha)
| [Hacker Earth](https://www.hackerearth.com/@jofisaes)
| [Khan Academy](https://www.khanacademy.org/profile/jofisaes)
| [Hacker News](https://news.ycombinator.com/user?id=jesperancinha)
| [InfoQ](https://www.infoq.com/profile/Joao-Esperancinha.2/)
| [LinkedIn](https://www.linkedin.com/in/joaoesperancinha/)
| [Xing](https://www.xing.com/profile/Joao_Esperancinha/cv)
| [Tumblr](https://jofisaes.tumblr.com/)
| [Pinterest](https://nl.pinterest.com/jesperancinha/)
| [Quora](https://nl.quora.com/profile/Jo%C3%A3o-Esperancinha)
| [VMware Spring Professional 2021](https://www.credly.com/badges/762fa7a4-9cf4-417d-bd29-7e072d74cdb7)
| [Oracle Certified Professional, Java SE 11 Programmer](https://www.credly.com/badges/87609d8e-27c5-45c9-9e42-60a5e9283280)
| [Oracle Certified Professional, JEE7 Developer](https://www.credly.com/badges/27a14e06-f591-4105-91ca-8c3215ef39a2)
| [IBM Cybersecurity Analyst Professional](https://www.credly.com/badges/ad1f4abe-3dfa-4a8c-b3c7-bae4669ad8ce)
| [Certified Advanced JavaScript Developer](https://cancanit.com/certified/1462/)
| [Certified Neo4j Professional](https://graphacademy.neo4j.com/certificates/c279afd7c3988bd727f8b3acb44b87f7504f940aac952495ff827dbfcac024fb.pdf)
| [Deep Learning](https://www.credly.com/badges/8d27e38c-869d-4815-8df3-13762c642d64)
| [![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=JEsperancinhaOrg&color=yellow "jesperancinha.org dependencies")](https://github.com/JEsperancinhaOrg)
[![Generic badge](https://img.shields.io/static/v1.svg?label=All%20Badges&message=Badges&color=red "All badges")](https://joaofilipesabinoesperancinha.nl/badges)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Status&message=Project%20Status&color=red "Project statuses")](https://github.com/jesperancinha/project-signer/blob/master/project-signer-quality/Build.md)
