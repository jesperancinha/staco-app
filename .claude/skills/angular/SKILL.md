---
name: angular
description: Conventions for Angular applications 
---

## 1. In all setup files, the target should be esnext

### Example in `tsconfig.json`:

Replace: 

```json
{
  "compilerOptions": {
    "target": "es5",
    "lib": ["es5", "dom"],
    "types": ["cypress", "node"]
  },
  "include": ["**/*.ts"]
}
```

with 

```json
{
  "compilerOptions": {
    "target": "esnext",
    "lib": ["esnext", "dom"],
    "types": ["cypress", "node"]
  },
  "include": ["**/*.ts"]
}
```

## 2. Make sure that the code strictly follows all angular standars

Please find the list of alle angular standards on the following page: https://angular.dev/style-guide.
It is of the utmost importance that all configuration, HTML, TypeScript, JavaScript, and everything that consists of an angular project, to follow these standards.
Changes should be made accordingly.
Modernize the build tooling too

## 3. Make sure that Angular projects are completely reactive with the usage of signals.

With the inception of signals, reactive programming for the front-end has been made easy. Make sure that, whenever possible, that the code uses signals instead of the typical subscriber model.
Find information on how to do this with good practices over at: https://angular.dev/guide/signals

## 4. Checklist

[] All targets in `tsconfig.json` files should be set to `esnext`
[] No target should remain with old target compiler option versions
