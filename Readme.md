# StaCo - A Stamps and Coins application

---

[![Twitter URL](https://img.shields.io/twitter/url?logoColor=blue&style=social&url=https%3A%2F%2Fimg.shields.io%2Ftwitter%2Furl%3Fstyle%3Dsocial)](https://twitter.com/intent/tweet?text=%20Checkout%20this%20%40github%20repo%20by%20%40joaofse%20%F0%9F%91%A8%F0%9F%8F%BD%E2%80%8D%F0%9F%92%BB%3A%20https%3A//github.com/jesperancinha/staco-app)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=StaCo%20App%20🪙%20&color=informational)](https://github.com/jesperancinha/staco-app)
[![GitHub release](https://img.shields.io/github/release-pre/jesperancinha/kotlin-test-drives.svg)](#)

[![CircleCI](https://circleci.com/gh/jesperancinha/staco-app/tree/main.svg?style=svg)](https://circleci.com/gh/jesperancinha/staco-app/tree/main)
[![staco-app](https://github.com/jesperancinha/staco-app/actions/workflows/staco-app.yml/badge.svg)](https://github.com/jesperancinha/staco-app/actions/workflows/staco-app.yml)
[![e2e-staco-app](https://github.com/jesperancinha/staco-app/actions/workflows/staco-app-e2e.yml/badge.svg)](https://github.com/jesperancinha/staco-app/actions/workflows/staco-app-e2e.yml)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/8d9267cddf834bff8f27cf8419253243)](https://www.codacy.com/gh/jesperancinha/staco-app/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/staco-app&amp;utm_campaign=Badge_Grade)

[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/8d9267cddf834bff8f27cf8419253243)](https://www.codacy.com/gh/jesperancinha/staco-app/dashboard?utm_source=github.com&utm_medium=referral&utm_content=jesperancinha/staco-app&utm_campaign=Badge_Coverage)
[![codecov](https://codecov.io/gh/jesperancinha/staco-app/branch/main/graph/badge.svg?token=JmvRFw1ir1)](https://codecov.io/gh/jesperancinha/staco-app)
[![Coverage Status](https://coveralls.io/repos/github/jesperancinha/staco-app/badge.svg?branch=main)](https://coveralls.io/github/jesperancinha/staco-app?branch=main)

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
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/kotest-50.png "Kotest")](https://kotest.io/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/mockk-50.png "MockK")](https://mockk.io/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/jupiter5-50.png "Jupiter 5")](https://junit.org/junit5/docs/current/user-guide/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/docker-50.png "Docker")](https://www.docker.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/docker-compose-50.png "Docker Compose")](https://docs.docker.com/compose/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/testcontainers-50.png "Test containers")](https://www.testcontainers.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/swagger-50.png "Swagger")](https://swagger.io/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/cypress-50.png "Cypress")](https://www.cypress.io/)

---

## Introduction

This repo is the official support repo to my article on medium:

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/medium-20.png "Medium")](https://itnext.io/learning-aws-with-localstack-and-reactive-kotlin-a-stamps-and-coins-implementation-d10278f98587) [Learning AWS with Localstack and Reactive Kotlin - A stamps and coins implementation](https://itnext.io/learning-aws-with-localstack-and-reactive-kotlin-a-stamps-and-coins-implementation-d10278f98587)

<div align="center">
      <a title="Learning AWS with Localstack and Reactive Kotlin - A stamps and coins implementation" href="https://itnext.io/learning-aws-with-localstack-and-reactive-kotlin-a-stamps-and-coins-implementation-d10278f98587">
     <img 
          src="./docs/images/article.staco.banner.jpg" 
          style="width:100%;">
      </a>
</div>

#### Stable releases

-   [1.0.0](https://github.com/jesperancinha/staco-app/tree/1.0.0) - [58e5cd42080a7950423020a64fa08515299d406a](https://github.com/jesperancinha/staco-app/tree/1.0.0)

### Project Layout

##### Article related

-   [Stamps and Coins Demo](./stamps-and-coins-demo) - Module used to create Demo data. It sends Coin and Stamps images
  and it can generate the initial data
-   [Stamps and Coins Common](./stamps-and-coins-common) - Contains common libraries, namely, Data Transfer Objects,
  Domain model
-   [Stamps and Coins Common Cloud](./stamps-and-coins-common-cloud) - Contains common libraries for the cloud projects.
-   [Stamps and Coins Batch](./stamps-and-coins-batch) - Spring batch Quartz based Jobs. They dump the data from
  PostgreSQL to a file and ship it to S3. Another Job retrieves the data, unpacks it and sends it to DynamoDB.
-   [Stamps and Coins Service](./stamps-and-coins-service) - This is our starting point. It contains a Reactive
  Application which uses PostgreSQL using R2DBC repos on coroutines
-   [Stamps and Coins Local Stack Service](./stamps-and-coins-ls-service) - This application serves data in the same way
  as the above except that it connects to DynamoDB. All Localstack implementations are manual
-   [Stamps and Coins Web](./stamps-and-coins-web) - Front End Application to explore the different implementations.
  Pagination is implemented

##### External to article

-   [Stamps and Coins Cloud Server](./stamps-and-coins-cloud-service) - Uses automated configuration and tries to use
  Localstack as much as possible.
-   [Stamps and Coins Blocking Service](./stamps-and-coins-blocking-service) - The initial application that started this
  project back in April 2021. It is now a login authentication/authorization security exploration module, which contains
  implementations of BASIC Auth, OAUTH2 local Auth and OAUTH with GitHub.

---

## Setup environment

Be sure to have [Docker](https://www.docker.com/products/docker-desktop) installed

#### Install sdk7

Be sure to have [SDK-MAN](https://sdkman.io/) installed for this to work. You can choose another way. It is only
important to have JDK17 installed to have this working.

```shell
. ./sdk17.sh
```

#### K8s

```shell
. ./bash/k8s-setup.sh 
```

#### Docker

```shell
. ./bash/docker-setup.sh
```

---

## How to run

#### Running all automatically

```shell
make docker-clean-build-start
```

-   Endpoint:  [http://localhost:8080](http://localhost:8080)

> These containers take a while to start, so depending on your machine, the containers can start in anywhere between 10 seconds up to 5 minutes. Keep checking the logs with `docker logs` or `docker-compose logs`.

## Sequence diagram

To visualize these diagrams you may need
the [mermaid-diagrams](https://chrome.google.com/webstore/detail/mermaid-diagrams/phfcghedmopjadpojhmmaffjmfiakfil)
plugin installation.

To visualize them in Intellij, please install the [mermaid plugin](https://mermaid-js.github.io/mermaid/#/).


#### Old Sketch

```mermaid

sequenceDiagram
    participant User
    participant StaCo App
    participant StaCo Service
    participant PostgreSQL Database
    
    rect rgb(200,200,200)
    
    User->>StaCo App: User logs into application
    StaCo App->>StaCo Service: A list of coins and stamps is requested by filter
    StaCo Service->>PostgreSQL Database: Data is fetched via the filter
    PostgreSQL Database ->>StaCo Service: Data is returned
    StaCo Service->>StaCo App: Minimal data manipulation and return
    StaCo App->>User: Results given to the User
    
    end
```

#### Current Sequence Diagram

```mermaid

sequenceDiagram
    participant Web
    participant Reactive Service
    participant Localstack Reactive Service
    participant Batch
    participant Blocking Service
    participant Cloud Service
    participant PostgreSQL Database
    participant S3
    participant DynamoDB
    participant SSM
    
    rect rgb(200,200,200)
    
    Web->>Reactive Service: User logs into application
    Web->>Blocking Service: User logs into application
    Web->>Blocking Service: User asks data from application
    Reactive Service->>PostgreSQL Database: Request Data
    PostgreSQL Database->>Reactive Service: Returns Data
    Reactive Service->>Web: Returns Data
    Web->>Localstack Reactive Service: User asks data from application
    Localstack Reactive Service->>DynamoDB: Requests Data
    DynamoDB->>Localstack Reactive Service: Sends data back
    Localstack Reactive Service->>Web: Sends data back
    Batch->>PostgreSQL Database: Requests Data
    PostgreSQL Database->>Batch: Sends Data Back
    Batch->>Batch: Processes data and creates CSV
    Batch->>S3: Sends GZ file
    Batch->>S3: Asks for data
    S3->>Batch: Sends GZ file back
    Batch->>Batch: Uncompresses file
    Batch->>DynamoDB: Saves data in data base
    Reactive Service->>SSM: Startup
    SSM->>Reactive Service: Configuration Data
    Localstack Reactive Service->>SSM: Startup
    SSM->>Localstack Reactive Service: Configuration Data
    Batch->>SSM: Startup
    SSM->>Batch: Configuration Data
    Cloud Service->>SSM: Startup
    SSM->>Cloud Service: Configuration Data
    end
```

#### Walk through

<div align="center">
      <a title="Learning AWS with Localstack and Reactive Kotli DEMO- A stamps and coins implementation" href="https://www.youtube.com/watch?v=Xe8qjnfWJp4">
     <img 
          src="https://img.youtube.com/vi/Xe8qjnfWJp4/0.jpg" 
          style="width:50%;">
      </a>
</div>

#### Demo

To make it easy for you, I've created a few scripts to run this demo in a seamless way.

The most important script however, is the `demo-full-manual`.

If you run:

```shell
make demo-full-manual
```

Then just wait until it completes. The cypress pop-up will appear. You can then start the tests manually and see how this application works!

#### Swagger tests

You can make tests for this application using the Swagger UI at:

###### Stamps and Coins LocalStack Service

-   [Stamps and Coins LocalStack Service stamps-and-coins-ls-service 8080](http://localhost:8080/api/staco/ls/webjars/swagger-ui/index.html)
-   [Stamps and Coins LocalStack Service stamps-and-coins-ls-service 8082 (local)](http://localhost:8082/api/staco/ls/webjars/swagger-ui/index.html)

###### Stamps and Coins Reactive Service

-   [Stamps and Coins Reactive Service stamps-and-coins-ls-service 8080](http://localhost:8080/api/staco/service/webjars/swagger-ui/index.html)
-   [Stamps and Coins Reactive Service stamps-and-coins-ls-service 8081 (local)](http://localhost:8081/api/staco/service/webjars/swagger-ui/index.html)

## References

### Videos

-   [you need to learn AWS RIGHT NOW!! (Amazon Web Services)](https://www.youtube.com/watch?v=bgPuPSPZe2U)
-   [Top 50+ AWS Services Explained in 10 Minutes](https://www.youtube.com/watch?v=JIbIYCM48to)
-   [Spring Boot CRUD Example using AWS DynamoDB](https://www.youtube.com/watch?v=3ay92ZdCgwQ)
-   [Setting up AWS v2 with Spring Boot and Localstack](https://www.youtube.com/watch?v=FOzAdoxdnSc)
-   [Running AWS Services In A Laptop Using LocalStack](https://www.youtube.com/watch?v=8hi9P1ffaQk)


<div align="center">
      <a title="you need to learn AWS RIGHT NOW!! (Amazon Web Services)" href="https://www.youtube.com/watch?v=bgPuPSPZe2U">
     <img 
          src="https://img.youtube.com/vi/bgPuPSPZe2U/0.jpg" 
          style="width:10%;">
      </a>
      <a title="Top 50+ AWS Services Explained in 10 Minutes" href="https://www.youtube.com/watch?v=JIbIYCM48to">
     <img 
          src="https://img.youtube.com/vi/JIbIYCM48to/0.jpg" 
          style="width:10%;">
      </a>
      <a title="Spring Boot CRUD Example using AWS DynamoDB" href="https://www.youtube.com/watch?v=3ay92ZdCgwQ">
     <img 
          src="https://img.youtube.com/vi/3ay92ZdCgwQ/0.jpg" 
          style="width:10%;">
      </a>
      <a title="Setting up AWS v2 with Spring Boot and Localstack" href="https://www.youtube.com/watch?v=FOzAdoxdnSc">
     <img 
          src="https://img.youtube.com/vi/FOzAdoxdnSc/0.jpg" 
          style="width:10%;">
      </a>
      <a title="Running AWS Services In A Laptop Using LocalStack" href="https://www.youtube.com/watch?v=8hi9P1ffaQk">
     <img 
          src="https://img.youtube.com/vi/8hi9P1ffaQk/0.jpg" 
          style="width:10%;">
      </a>
</div>

### Online

-   [How to externalize Spring Boot Properties to an AWS System Manager Parameter Store](https://towardsaws.com/how-to-externalize-spring-boot-properties-to-an-aws-system-manager-parameter-store-2a945b1e856f)
-   [Representational state transfer](https://www.wikiwand.com/en/Representational_state_transfer)
-   [REST (REpresentational State Transfer)](https://searchapparchitecture.techtarget.com/definition/REST-REpresentational-State-Transfer)
-   [Wat is on-premise en wat is de cloud?](https://www.dynamicsonline.nl/erp-software/wat-is-on-premise-en-wat-is-de-cloud/)
-   [Who Coined 'Cloud Computing'?](https://www.technologyreview.com/2011/10/31/257406/who-coined-cloud-computing/)
-   [The history of cloud computing](https://www.scality.com/solved/the-history-of-cloud-computing/)
-   [My First 12 Years at Amazon.com](http://jeff-barr.com/2014/08/19/my-first-12-years-at-amazon-dot-com/)
-   [A Brief History of AWS](https://mediatemple.net/blog/cloud-hosting/brief-history-aws/)
-   [When to use (and when not to use) DynamoDB Filter Expressions](https://www.alexdebrie.com/posts/dynamodb-filter-expressions/)
-   [DynamoDB Scan vs Query - Everything You Need To Know](https://dynobase.dev/dynamodb-scan-vs-query/)
-   [What is "/var/folders"?](http://www.magnusviri.com/what-is-var-folders.html)
-   [From PostgreSQL to DynamoDB](https://www.dyspatch.io/blog/from-postgresql-to-dynamodb/)
-   [Amazon Web Services Systems Manager](https://docs.aws.amazon.com/cli/latest/reference/ssm/index.html#cli-aws-ssm)
-   [Don’t Be Intimidated — Learn How to Run AWS on Your Local Machine With LocalStack](https://betterprogramming.pub/dont-be-intimidated-learn-how-to-run-aws-on-your-local-machine-with-localstack-2f3448462254)
-   [Spring Boot Integration Tests With AWS Services Using LocalStack](https://rieckpil.de/test-spring-applications-using-aws-with-testcontainers-and-localstack/)
-   [Using LocalStack for Development Environments](https://www.maxcode.net/blog/using-localstack-for-development-environments/)
-   [Configuring Docker Swarm as Container Orchestrator on Windows](https://koukia.ca/configuring-docker-swarm-as-container-orchestrator-on-windows-1f89a2037dac)
-   [What is a Docker Swarm](https://www.sumologic.com/glossary/docker-swarm/)
-   [Kubernetes vs. Docker](https://azure.microsoft.com/en-us/topic/kubernetes-vs-docker/)
-   [Working with Queries in DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Query.html)
-   [How to determine if Amazon DynamoDB is appropriate for your needs, and then plan your migration](https://aws.amazon.com/blogs/database/how-to-determine-if-amazon-dynamodb-is-appropriate-for-your-needs-and-then-plan-your-migration/)
-   [5 Use Cases for DynamoDB](https://rockset.com/blog/5-use-cases-for-dynamodb/)
-   [Local Stack Pro Features](https://localstack.cloud/features/#pro)
-   [LocalStack](https://github.com/localstack/localstack)
-   [LocalStack Cloud](https://localstack.cloud/)

## About me

<div align="center">

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-100/JEOrgLogo-27.png "João Esperancinha Homepage")](http://joaofilipesabinoesperancinha.nl)
[![](https://img.shields.io/badge/youtube-%230077B5.svg?style=for-the-badge&logo=youtube&color=FF0000)](https://www.youtube.com/@joaoesperancinha)
[![](https://img.shields.io/badge/Medium-12100E?style=for-the-badge&logo=medium&logoColor=white)](https://medium.com/@jofisaes)
[![](https://img.shields.io/badge/Buy%20Me%20A%20Coffee-%230077B5.svg?style=for-the-badge&logo=buymeacoffee&color=yellow)](https://www.buymeacoffee.com/jesperancinha)
[![](https://img.shields.io/badge/Twitter-%230077B5.svg?style=for-the-badge&logo=twitter&color=white)](https://twitter.com/joaofse)
[![](https://img.shields.io/badge/Mastodon-%230077B5.svg?style=for-the-badge&logo=mastodon&color=afd7f7)](https://masto.ai/@jesperancinha)
[![](https://img.shields.io/badge/Sessionize-%230077B5.svg?style=for-the-badge&logo=sessionize&color=cffff6)](https://sessionize.com/joao-esperancinha)
[![](https://img.shields.io/badge/Instagram-%230077B5.svg?style=for-the-badge&logo=instagram&color=purple)](https://www.instagram.com/joaofisaes)
[![](https://img.shields.io/badge/Tumblr-%230077B5.svg?style=for-the-badge&logo=tumblr&color=192841)](https://jofisaes.tumblr.com)
[![](https://img.shields.io/badge/Spotify-1ED760?style=for-the-badge&logo=spotify&logoColor=white)](https://open.spotify.com/user/jlnozkcomrxgsaip7yvffpqqm)
[![](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/joaoesperancinha/)
[![](https://img.shields.io/badge/Xing-%230077B5.svg?style=for-the-badge&logo=xing&color=064e40)](https://www.xing.com/profile/Joao_Esperancinha/cv)
[![](https://img.shields.io/badge/YCombinator-%230077B5.svg?style=for-the-badge&logo=ycombinator&color=d0d9cd)](https://news.ycombinator.com/user?id=jesperancinha)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
[![](https://img.shields.io/badge/bitbucket-%230077B5.svg?style=for-the-badge&logo=bitbucket&color=blue)](https://bitbucket.org/jesperancinha)
[![](https://img.shields.io/badge/gitlab-%230077B5.svg?style=for-the-badge&logo=gitlab&color=orange)](https://gitlab.com/jesperancinha)
[![](https://img.shields.io/badge/Sonatype%20Search%20Repos-%230077B5.svg?style=for-the-badge&color=red)](https://central.sonatype.com/search?smo=true&q=org.jesperancinha)
[![](https://img.shields.io/badge/Stack%20Overflow-%230077B5.svg?style=for-the-badge&logo=stackoverflow&color=5A5A5A)](https://stackoverflow.com/users/3702839/joao-esperancinha)
[![](https://img.shields.io/badge/Credly-%230077B5.svg?style=for-the-badge&logo=credly&color=064e40)](https://www.credly.com/users/joao-esperancinha)
[![](https://img.shields.io/badge/Coursera-%230077B5.svg?style=for-the-badge&logo=coursera&color=000080)](https://www.coursera.org/user/da3ff90299fa9297e283ee8e65364ffb)
[![](https://img.shields.io/badge/Docker-%230077B5.svg?style=for-the-badge&logo=docker&color=cyan)](https://hub.docker.com/u/jesperancinha)
[![](https://img.shields.io/badge/Reddit-%230077B5.svg?style=for-the-badge&logo=reddit&color=black)](https://www.reddit.com/user/jesperancinha/)
[![](https://img.shields.io/badge/Hackernoon-%230077B5.svg?style=for-the-badge&logo=hackernoon&color=0a5d00)](https://hackernoon.com/@jesperancinha)
[![](https://img.shields.io/badge/Dev.TO-%230077B5.svg?style=for-the-badge&color=black&logo=dev.to)](https://dev.to/jofisaes)
[![](https://img.shields.io/badge/Code%20Project-%230077B5.svg?style=for-the-badge&logo=codeproject&color=063b00)](https://www.codeproject.com/Members/jesperancinha)
[![](https://img.shields.io/badge/Free%20Code%20Camp-%230077B5.svg?style=for-the-badge&logo=freecodecamp&color=0a5d00)](https://www.freecodecamp.org/jofisaes)
[![](https://img.shields.io/badge/Hackerrank-%230077B5.svg?style=for-the-badge&logo=hackerrank&color=1e2f97)](https://www.hackerrank.com/jofisaes)
[![](https://img.shields.io/badge/LeetCode-%230077B5.svg?style=for-the-badge&logo=leetcode&color=002647)](https://leetcode.com/jofisaes)
[![](https://img.shields.io/badge/Codewars-%230077B5.svg?style=for-the-badge&logo=codewars&color=722F37)](https://www.codewars.com/users/jesperancinha)
[![](https://img.shields.io/badge/CodePen-%230077B5.svg?style=for-the-badge&logo=codepen&color=black)](https://codepen.io/jesperancinha)
[![](https://img.shields.io/badge/HackerEarth-%230077B5.svg?style=for-the-badge&logo=hackerearth&color=00035b)](https://www.hackerearth.com/@jofisaes)
[![](https://img.shields.io/badge/Khan%20Academy-%230077B5.svg?style=for-the-badge&logo=khanacademy&color=00035b)](https://www.khanacademy.org/profile/jofisaes)
[![](https://img.shields.io/badge/Pinterest-%230077B5.svg?style=for-the-badge&logo=pinterest&color=FF0000)](https://nl.pinterest.com/jesperancinha)
[![](https://img.shields.io/badge/Quora-%230077B5.svg?style=for-the-badge&logo=quora&color=FF0000)](https://nl.quora.com/profile/Jo%C3%A3o-Esperancinha)
[![](https://img.shields.io/badge/Google%20Play-%230077B5.svg?style=for-the-badge&logo=googleplay&color=purple)](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
[![](https://img.shields.io/badge/Coderbyte-%230077B5.svg?style=for-the-badge&color=blue&logo=coderbyte)](https://coderbyte.com/profile/jesperancinha)
[![](https://img.shields.io/badge/InfoQ-%230077B5.svg?style=for-the-badge&color=blue&logo=coderbyte)](https://www.infoq.com/profile/Joao-Esperancinha.2/)
[![](https://img.shields.io/badge/OCP%20Java%2011-%230077B5.svg?style=for-the-badge&logo=oracle&color=064e40)](https://www.credly.com/badges/87609d8e-27c5-45c9-9e42-60a5e9283280)
[![](https://img.shields.io/badge/OCP%20JEE%207-%230077B5.svg?style=for-the-badge&logo=oracle&color=064e40)](https://www.credly.com/badges/27a14e06-f591-4105-91ca-8c3215ef39a2)
[![](https://img.shields.io/badge/VMWare%20Spring%20Professional%202021-%230077B5.svg?style=for-the-badge&logo=spring&color=064e40)](https://www.credly.com/badges/762fa7a4-9cf4-417d-bd29-7e072d74cdb7)
[![](https://img.shields.io/badge/IBM%20Cybersecurity%20Analyst%20Professional-%230077B5.svg?style=for-the-badge&logo=ibm&color=064e40)](https://www.credly.com/badges/ad1f4abe-3dfa-4a8c-b3c7-bae4669ad8ce)
[![](https://img.shields.io/badge/Deep%20Learning-%230077B5.svg?style=for-the-badge&logo=ibm&color=064e40)](https://www.credly.com/badges/8d27e38c-869d-4815-8df3-13762c642d64)
[![](https://img.shields.io/badge/Certified%20Neo4j%20Professional-%230077B5.svg?style=for-the-badge&logo=neo4j&color=064e40)](https://graphacademy.neo4j.com/certificates/c279afd7c3988bd727f8b3acb44b87f7504f940aac952495ff827dbfcac024fb.pdf)
[![](https://img.shields.io/badge/Certified%20Advanced%20JavaScript%20Developer-%230077B5.svg?style=for-the-badge&logo=javascript&color=064e40)](https://cancanit.com/certified/1462/)
[![](https://img.shields.io/badge/Kong%20Champions-%230077B5.svg?style=for-the-badge&logo=kong&color=064e40)](https://konghq.com/kong-champions)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=JEsperancinhaOrg&color=064e40&style=for-the-badge "jesperancinha.org dependencies")](https://github.com/JEsperancinhaOrg)
[![Generic badge](https://img.shields.io/static/v1.svg?label=All%20Badges&message=Badges&color=064e40&style=for-the-badge "All badges")](https://joaofilipesabinoesperancinha.nl/badges)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Status&message=Project%20Status&color=orange&style=for-the-badge "Project statuses")](https://github.com/jesperancinha/project-signer/blob/master/project-signer-quality/Build.md)

</div>
