b: build-npm build-maven
build: build-npm
	mvn clean install
build-npm:
	cd stamps-and-coins-web && yarn install && npm run build
build-maven:
	mvn clean install -DskipTests
test:
	mvn test
test-maven:
	mvn test
local: no-test
	mkdir -p bin
no-test:
	mvn clean install -DskipTests
docker:
	docker-compose up -d --build --remove-orphans
	make localstack-config
docker-databases: stop local
build-images:
build-docker: stop no-test build-npm
	docker-compose up -d --build --remove-orphans
show:
	docker ps -a  --format '{{.ID}} - {{.Names}} - {{.Status}}'
docker-delete-idle:
	docker ps --format '{{.ID}}' -q --filter="name=staco_" | xargs docker rm
docker-delete: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=staco_" | xargs docker stop
	docker ps -a --format '{{.ID}}' -q --filter="name=staco_" | xargs docker rm
docker-cleanup: docker-delete
	docker images -q | xargs docker rmi
docker-delete-apps: stop
docker-localstack:
	docker-compose rm -svf
	docker-compose up -d --build --remove-orphans localstack aws-cli-1 aws-cli-2
docker-cli:
	docker-compose up -d --build --remove-orphans aws-cli-1 aws-cli-2
localstack-config:
	sleep 1
	aws ssm --endpoint-url http://localhost:4566 put-parameter --name /config/StaCoLsService/dev/username --value "postgres"
	aws ssm --endpoint-url http://localhost:4566 put-parameter --name /config/StaCoLsService/dev/password --value "password"
prune-all: stop
	docker ps -a --format '{{.ID}}' -q | xargs docker stop
	docker ps -a --format '{{.ID}}' -q | xargs docker rm
	docker system prune --all
	docker builder prune
	docker system prune --all --volumes
stop:
	docker-compose down --remove-orphans
mac-os-install:
	curl "https://awscli.amazonaws.com/AWSCLIV2.pkg" -o "AWSCLIV2.pkg"
	sudo installer -pkg AWSCLIV2.pkg -target /
	brew tap aws/tap
	brew install aws-sam-cli
	brew install kubectl kubectl helm
k8-endpoint:
	./bash/endpoint.sh
minikube-vmware:
	minikube start --driver=vmware
