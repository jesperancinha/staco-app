# StaCo - A Stamps and Coins application

---


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

Please check the [TechStack.md](TechStack.md) file for details.

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

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
