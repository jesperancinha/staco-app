update:
	npm install -g npm-check-updates
	ncu -u
	npm update --legacy-peer-deps
	npm audit fix --legacy-peer-deps
update-force: update
	npm audit fix --legacy-peer-deps --force
	npm audit fix --force
	npm audit fix --force
	npm audit fix --force
	npm audit fix --force
build:
	if [ -d node_modules ]; then rm -r node_modules; fi
	yarn install && npm run build
checkup: build
	npm test
	npm start


