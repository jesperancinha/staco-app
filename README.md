# StaCo - A Stamps and Coins application

---

[![Twitter URL](https://img.shields.io/twitter/url?logoColor=blue&style=social&url=https%3A%2F%2Fimg.shields.io%2Ftwitter%2Furl%3Fstyle%3Dsocial)](https://twitter.com/intent/tweet?text=%20Checkout%20this%20%40github%20repo%20by%20%40joaofse%20%F0%9F%91%A8%F0%9F%8F%BD%E2%80%8D%F0%9F%92%BB%3A%20https%3A//github.com/jesperancinha/staco-app)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=StaCo%20App%20🪙%20&color=informational)](https://github.com/jesperancinha/staco-app)
[![GitHub release](https://img.shields.io/github/release-pre/jesperancinha/kotlin-test-drives.svg)](#)

[![CircleCI](https://circleci.com/gh/jesperancinha/staco-app/tree/main.svg?style=svg)](https://circleci.com/gh/jesperancinha/staco-app/tree/main)
[![staco-app](https://github.com/jesperancinha/staco-app/actions/workflows/staco-app.yml/badge.svg)](https://github.com/jesperancinha/staco-app/actions/workflows/staco-app.yml)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/3a1f6cb09e9a40c4aa6bf4d372e848fa)](https://www.codacy.com/gh/jesperancinha/staco-app/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/staco-app&amp;utm_campaign=Badge_Grade)

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

## Troubleshooting

1. If you are running the integration tests on your local machine and realize that the containers remain still and don't respond for a while, this could be a sign of a problem with Docker machine. Please restart your docker desktop/docker-machine and try again. 
2. If for some reason you cannot start docker-compose and Docker gives this response:

```shell
Creating network "staco-app_staco_net" with driver "bridge"
ERROR: Pool overlaps with other one on this address space
make: *** [docker] Error 1
```

then this just means that you may still have docker containers running with a network bridge driver. You need to stop them and then remove the network with `docker network prune`.

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

## Buy me a coffee

I hope you enjoyed this repository. If you did, you can optionally please buy me a coffee, which supports me to constantly improve and make new free content regularly for everyone. Thank you so much!

[![Buy me a coffee](https://img.buymeacoffee.com/button-api/?text=Buy%20me%20a%20coffee&emoji=&slug=jesperancinha&button_colour=046c46&font_colour=ffffff&font_family=Cookie&outline_colour=ffffff&coffee_colour=FFDD00 "title")](https://www.buymeacoffee.com/jesperancinha)

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

## About me 👨🏽‍💻🚀🏳️‍🌈

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/JEOrgLogo-20.png "João Esperancinha Homepage")](http://joaofilipesabinoesperancinha.nl)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/medium-20.png "Medium")](https://medium.com/@jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/credly-20.png "Credly")](https://www.credly.com/users/joao-esperancinha)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=joaofilipesabinoesperancinha.nl&color=6495ED "João Esperancinha Homepage")](https://joaofilipesabinoesperancinha.nl/)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=jesperancinha&style=social "GitHub")](https://github.com/jesperancinha)
[![Twitter Follow](https://img.shields.io/twitter/follow/joaofse?label=João%20Esperancinha&style=social "Twitter")](https://twitter.com/joaofse)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=JEsperancinhaOrg&color=yellow "jesperancinha.org dependencies")](https://github.com/JEsperancinhaOrg)   
[![Generic badge](https://img.shields.io/static/v1.svg?label=Articles&message=Across%20The%20Web&color=purple)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Articles.md)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Webapp&message=Image%20Train%20Filters&color=6495ED)](http://itf.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=All%20Badges&message=Badges&color=red "All badges")](https://joaofilipesabinoesperancinha.nl/badges)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Status&message=Project%20Status&color=red "Project statuses")](https://github.com/jesperancinha/project-signer/blob/master/project-signer-quality/Build.md)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/coursera-20.png "Coursera")](https://www.coursera.org/user/da3ff90299fa9297e283ee8e65364ffb)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/google-apps-20.png "Google Apps")](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)   
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/sonatype-20.png "Sonatype Search Repos")](https://search.maven.org/search?q=org.jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/docker-20.png "Docker Images")](https://hub.docker.com/u/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/stack-overflow-20.png)](https://stackoverflow.com/users/3702839/joao-esperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/reddit-20.png "Reddit")](https://www.reddit.com/user/jesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/devto-20.png "Dev To")](https://dev.to/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hackernoon-20.jpeg "Hackernoon")](https://hackernoon.com/@jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codeproject-20.png "Code Project")](https://www.codeproject.com/Members/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/github-20.png "GitHub")](https://github.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/bitbucket-20.png "BitBucket")](https://bitbucket.org/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/gitlab-20.png "GitLab")](https://gitlab.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/bintray-20.png "BinTray")](https://bintray.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/free-code-camp-20.jpg "FreeCodeCamp")](https://www.freecodecamp.org/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hackerrank-20.png "HackerRank")](https://www.hackerrank.com/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codeforces-20.png "Code Forces")](https://codeforces.com/profile/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codebyte-20.png "Codebyte")](https://coderbyte.com/profile/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codewars-20.png "CodeWars")](https://www.codewars.com/users/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codepen-20.png "Code Pen")](https://codepen.io/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hacker-news-20.png "Hacker News")](https://news.ycombinator.com/user?id=jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/infoq-20.png "InfoQ")](https://www.infoq.com/profile/Joao-Esperancinha.2/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/linkedin-20.png "LinkedIn")](https://www.linkedin.com/in/joaoesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/xing-20.png "Xing")](https://www.xing.com/profile/Joao_Esperancinha/cv)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/tumblr-20.png "Tumblr")](https://jofisaes.tumblr.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/pinterest-20.png "Pinterest")](https://nl.pinterest.com/jesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/quora-20.png "Quora")](https://nl.quora.com/profile/Jo%C3%A3o-Esperancinha)

## Achievements

[![VMware Spring Professional 2021](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/vmware-spring-professional-2021.png "VMware Spring Professional 2021")](https://www.credly.com/badges/762fa7a4-9cf4-417d-bd29-7e072d74cdb7)
[![Oracle Certified Professional, JEE 7 Developer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-ee-7-application-developer-100.png "Oracle Certified Professional, JEE7 Developer")](https://www.credly.com/badges/27a14e06-f591-4105-91ca-8c3215ef39a2)
[![Oracle Certified Professional, Java SE 11 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-se-11-developer-100.png "Oracle Certified Professional, Java SE 11 Programmer")](https://www.credly.com/badges/87609d8e-27c5-45c9-9e42-60a5e9283280)
[![IBM Cybersecurity Analyst Professional](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/ibm-cybersecurity-analyst-professional-certificate-100.png "IBM Cybersecurity Analyst Professional")](https://www.credly.com/badges/ad1f4abe-3dfa-4a8c-b3c7-bae4669ad8ce)
[![Certified Advanced JavaScript Developer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/cancanit-badge-1462-100.png "Certified Advanced JavaScript Developer")](https://cancanit.com/certified/1462/)
[![Certified Neo4j Professional](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/professional_neo4j_developer-100.png "Certified Neo4j Professional")](https://graphacademy.neo4j.com/certificates/c279afd7c3988bd727f8b3acb44b87f7504f940aac952495ff827dbfcac024fb.pdf)
[![Deep Learning](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/deep-learning-100.png "Deep Learning")](https://www.credly.com/badges/8d27e38c-869d-4815-8df3-13762c642d64)
