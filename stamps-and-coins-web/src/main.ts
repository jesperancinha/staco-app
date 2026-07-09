import {enableProdMode} from '@angular/core';
import {bootstrapApplication} from '@angular/platform-browser';
import {provideAnimations} from '@angular/platform-browser/animations';
import {provideRouter} from '@angular/router';
import {provideHttpClient, withInterceptors} from '@angular/common/http';

import {environment} from './environments/environment';
import {AppComponent} from './app/app.component';
import {routes} from './app/app.routes';
import {xhrInterceptor} from './app/interceptors/xhr.interceptor';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideHttpClient(withInterceptors([xhrInterceptor]))
  ]
}).catch(err => console.error(err));
