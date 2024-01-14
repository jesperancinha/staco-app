# Stamps and Coins Demo

## I. Startup

```shell
export NODE_PORT=4566
export NODE_IP=127.0.0.1
export LOCAL_STACK=http://$NODE_IP:$NODE_PORT
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=eu-central-1
echo http://$NODE_IP:$NODE_PORT
alias aws="aws --endpoint-url $LOCAL_STACK"
```

## II. stamps-and-coins-ls-service

#### 1.  Talking with the API
```shell
curl -v -F "image=@stamp-sample.png" http://localhost:8082/api/staco/ls/images/save/$(uuidgen)
curl -v -F "image=@coin-sample.png" http://localhost:8082/api/staco/ls/images/save/$(uuidgen)
```

```shell
curl -v -H "Content-Type: application/json" -d @coin.json http://localhost:8082/api/staco/ls/stacos/coin
#This test fails because description exists and is shorter than 10 characters
curl -v -H "Content-Type: application/json" -d @coin_desc_fail.json http://localhost:8082/api/staco/ls/stacos/coin
aws dynamodb describe-table --table-name stacos
aws dynamodb scan --table-name stacos
aws s3api list-buckets
aws s3api list-objects --bucket images
aws s3api list-objects --bucket stacos
```

```shell
curl http://localhost:8082/api/staco/ls/stacos/all
```

#### 2.  Checking results with aws
>Remember to run `. ../bash/docker-setup.sh`

```shell
aws dynamodb scan --table-name stacos
aws dynamodb delete-table --table-name stacos
```

## III. stamps-and-coins-batch

#### 1.  Checking results with aws

```shell
aws s3api create-bucket --bucket stacos
aws s3api create-bucket --bucket images
```
```shell
aws dynamodb scan --table-name stacos
aws dynamodb delete-table --table-name stacos
```

## IV. stamps-and-coins-ls-service

#### 1.  Endpoints

-   [http://localhost:8082/api/staco/ls/stacos/search/1/20/year](http://localhost:8082/api/staco/ls/stacos/search/1/20/year)
-   [http://localhost:8082/api/staco/ls/stacos/search/1/10/year/ASC](http://localhost:8082/api/staco/ls/stacos/search/1/10/year)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
