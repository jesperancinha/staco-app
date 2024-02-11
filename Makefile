SHELL := /bin/bash
GITHUB_RUN_ID ?=123

b: build-npm build-maven
build: build-npm
	mvn clean install
build-npm:
	cd stamps-and-coins-web && yarn install && npm run build
build-maven:
	mvn clean install package -DskipTests
build-nginx:
	docker-compose -p ${GITHUB_RUN_ID} build nginx
test:
	mvn test
test-maven:
	mvn test
local: no-test
	mkdir -p bin
no-test:
	mvn clean install -DskipTests
clean:
	 if [[ -d ${HOME}/.m2/repository/org/jetbrains/kotlin/kotlin-reflect ]]; then rm -rf ${HOME}/.m2/repository/org/jetbrains/kotlin/kotlin-reflect; fi
docker:
	docker-compose -p ${GITHUB_RUN_ID} rm -svf
	docker-compose -p ${GITHUB_RUN_ID} up -d --build --remove-orphans
docker-action:
	docker-compose -p ${GITHUB_RUN_ID} rm -svf
	docker-compose -p ${GITHUB_RUN_ID} -f docker-compose.yml up -d --build --remove-orphans
docker-databases: stop local
build-images:
build-docker: stop no-test build-npm
	docker-compose -p ${GITHUB_RUN_ID} up -d --build --remove-orphans
show:
	docker ps -a  --format '{{.ID}} - {{.Names}} - {{.Status}}'
docker-delete-idle:
	docker ps --format '{{.ID}}' -q --filter="name=staco_" | xargs docker rm
docker-delete: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=staco_" | xargs -I {}  docker stop {}
	docker ps -a --format '{{.ID}}' -q --filter="name=staco_" | xargs -I {}  docker rm {}
docker-stop-all:
	docker ps -a --format '{{.ID}}' | xargs -I {}  docker stop {}
docker-cleanup: docker-delete
	docker network prune
	docker images -q | xargs docker rmi
docker-clean:
	docker-compose -p ${GITHUB_RUN_ID} rm -svf -a
	docker network prune -f
docker-clean-build-start: docker-clean b docker
docker-delete-apps: stop
docker-localstack:
	docker-compose -p ${GITHUB_RUN_ID} rm -svf
	docker-compose -p ${GITHUB_RUN_ID} rm localstack
	docker-compose -p ${GITHUB_RUN_ID} up -d --build --remove-orphans localstack
docker-stamps-and-coins-service:
	cd stamps-and-coins-service && mvn clean install -DskipTests
	docker-compose -p ${GITHUB_RUN_ID} rm staco-app-service-reactive
	docker-compose -p ${GITHUB_RUN_ID} build staco-app-service-reactive
	docker-compose -p ${GITHUB_RUN_ID} up -d --build --remove-orphans staco-app-service-reactive
docker-staco-app-batch:
	cd stamps-and-coins-batch && mvn clean install -DskipTests
	docker-compose -p ${GITHUB_RUN_ID} rm staco-app-batch
	docker-compose -p ${GITHUB_RUN_ID} build staco-app-batch
	docker-compose -p ${GITHUB_RUN_ID} up -d --build --remove-orphans staco-app-batch
docker-cli:
	docker-compose -p ${GITHUB_RUN_ID} up -d --build --remove-orphans aws-cli-1 aws-cli-2 aws-cli-3 aws-cli-4 aws-cli-5 aws-cli-6
localstack-config:
	sleep 1
	aws ssm --endpoint-url http://localhost:4566 put-parameter --name /config/StaCoLsService/dev/username --value "postgres"
	aws ssm --endpoint-url http://localhost:4566 put-parameter --name /config/StaCoLsService/dev/password --value "password"
prune-all: docker-delete
	docker network prune
	docker system prune --all
	docker builder prune
	docker system prune --all --volumes
stop:
	docker-compose -p ${GITHUB_RUN_ID} down --remove-orphans
delete-all:
	docker ps -a --format '{{.ID}}' | xargs -I {}  docker stop {}
	docker ps -a --format '{{.ID}}' | xargs -I {}  docker rm {}
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
#MAC-OS
install-mac-os:
	xcode-select --install
update:
	find . -name "package-lock.json" | xargs rm; \
	find . -name "yarn.lock" | xargs rm; \
	git pull
	npm install caniuse-lite
	npm install -g npm-check-updates
	cd stamps-and-coins-web; \
 		yarn; \
 		npx browserslist --update-db; \
 		ncu -u; \
 		yarn
install-update: update
	npm i -g snyk
audit:
	cd stamps-and-coins-web && npm audit fix && yarn
staco-wait:
	bash staco_wait.sh
dynamo-wait:
	bash staco_dynamowait.sh
dcup-light:
	docker-compose -p ${GITHUB_RUN_ID} up -d postgres localstack
dcd:
	docker-compose -p ${GITHUB_RUN_ID} down
dcup: dcd docker-clean docker staco-wait
dcup-full: docker-clean-build-start staco-wait
dcup-full-action: docker-clean b docker-action staco-wait dynamo-wait
cypress-open:
	cd e2e && yarn && npm run cypress:open:electron
cypress-electron:
	cd e2e && make cypress-electron
cypress-chrome:
	cd e2e && make cypress-chrome
cypress-firefox:
	cd e2e && make cypress-firefox
cypress-edge:
	cd e2e && make cypress-edge
demo: dcup cypress
demo-full: dcup-full cypress
demo-full-manual: dcup-full cypress-open
aws-client-logs:
	docker ps -a --format '{{.ID}}' -q --filter="name=aws-cli-1" | xargs docker logs
	docker ps -a --format '{{.ID}}' -q --filter="name=aws-cli-2" | xargs docker logs
	docker ps -a --format '{{.ID}}' -q --filter="name=aws-cli-3" | xargs docker logs
	docker ps -a --format '{{.ID}}' -q --filter="name=aws-cli-4" | xargs docker logs
	docker ps -a --format '{{.ID}}' -q --filter="name=aws-cli-5" | xargs docker logs
	docker ps -a --format '{{.ID}}' -q --filter="name=aws-cli-6" | xargs docker logs
hotkey-fix:
	sudo add-apt-repository ppa:nrbrtx/xorg-hotkeys
	sudo apt-get update
	sudo apt-get dist-upgrade
	sudo apt install --reinstall xserver-xorg-input-all
	sudo dpkg-reconfigure keyboard-configuration
	sudo apt-get install kwin-addons
