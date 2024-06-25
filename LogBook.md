# STACO App LogBook

---
#### 2024/06/25

Due to java upgrade and new Alpine image, health checks had to be changed:

1. test: ["CMD-SHELL", "timeout 10s bash -c ':> /dev/tcp/127.0.0.1/8081'"]
2. test: [ "CMD", "/usr/bin/curl", "http://0.0.0.0:8081" ]


#### 2024/06/21

Upgrades to webpack:

###### Updates dependencies

```shell
npm install @angular-builders/custom-webpack --save-dev
npm install karma-chrome-launcher --save-dev
```

###### webpack.config.js

```javascript
module.exports = {
  resolve: {
    fallback: {
      "util": false,
    },
  },
};
```

###### angular.json

```json
{
  "architect": {
    "test": {
      "builder": "@angular-builders/custom-webpack:karma",
      "options": {
        "customWebpackConfig": {
          "path": "./webpack.config.js"
        },
        "main": "src/test.ts",
        "polyfills": "src/polyfills.ts",
        "tsConfig": "src/tsconfig.spec.json",
        "karmaConfig": "./karma.conf.js",
        "scripts": [],
        "styles": [],
        "assets": []
      }
    }
  }
}
```

---
