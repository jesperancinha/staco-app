# STACO App LogBook

---

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
