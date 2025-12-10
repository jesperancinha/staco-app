import {enableProdMode} from '@angular/core';
import {bootstrapApplication} from '@angular/platform-browser';
import {provideRouter} from '@angular/router';
import {provideHttpClient, withInterceptorsFromDi, HTTP_INTERCEPTORS} from '@angular/common/types/http';

import {environment} from './environments/environment';
import {AppComponent} from './app/app.component';
import {routes} from './app/app-routing.module';
import {AppService} from './app/services/app.service';
import {XhrInterceptor} from './app/app.module';
import {provideAnimations} from "@angular/platform-browser/types/animations";

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideHttpClient(withInterceptorsFromDi()),
    AppService,
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}
  ]
}).catch(err => console.error(err));
