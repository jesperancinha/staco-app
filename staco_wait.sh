#!/bin/bash
GITHUB_RUN_ID=${GITHUB_RUN_ID:-123}

function checkServiceByNameAndMessage() {
    name=$1
    message=$2
    message2=$3
    docker-compose -p "${GITHUB_RUN_ID}" logs "$name" > "logs"
    string=$(cat logs)
    counter=0
    echo "Project $GITHUB_RUN_ID"
    echo -n "Starting service $name "
    while [[ "$string" != *"$message"* || "$string" != *"$message2"* ]]
    do
      echo -e -n "\e[93m-\e[39m"
      docker-compose -p "${GITHUB_RUN_ID}" logs "$name" > "logs"
      string=$(cat logs)
      sleep 1
      counter=$((counter+1))
      if [ $counter -eq 200 ]; then
          echo -e "\e[91mFailed after $counter tries! Cypress tests may fail!!\e[39m"
          echo "$string"
          docker-compose logs
          exit 1
      fi
    done
    counter=$((counter+1))
    echo -e "\e[92m Succeeded starting $name Service after $counter tries!\e[39m"
}

checkServiceByNameAndMessage postgres 'database system is ready to accept connections'
checkServiceByNameAndMessage nginx 'test is successful'
checkServiceByNameAndMessage staco-app-batch 'Started StampsAndCoinsBatchLauncher' 'Started StampsAndCoinsBatchLauncherKt'
checkServiceByNameAndMessage staco-app-service-reactive 'Started StaCoSearchReactApplicationKt'
checkServiceByNameAndMessage staco-app-service-localstack 'Bucket(Name=stacos'
checkServiceByNameAndMessage localstack 'Initializing DynamoDB'
