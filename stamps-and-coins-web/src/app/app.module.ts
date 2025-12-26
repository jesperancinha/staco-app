import {bootstrapApplication} from '@angular/platform-browser';
import {importProvidersFrom, Injectable} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {
  HTTP_INTERCEPTORS,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  provideHttpClient,
  withInterceptorsFromDi
} from "@angular/common/types/http";
import {MatInputModule} from "@angular/material/types/input";
import {MatFormFieldModule} from "@angular/material/types/_form-field-module-chunk";
import {MatSelectModule} from "@angular/material/types/select";
import {MatCardModule} from "@angular/material/types/card";
import {MatListModule} from "@angular/material/types/list";
import {provideAnimations} from "@angular/platform-browser/types/animations";
import {MatTableModule} from "@angular/material/types/table";
import {AppService} from "./services/app.service";
import {MatGridListModule} from "@angular/material/types/grid-list";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.append("X-Requested-With", "XMLHttpRequest")
    });
    return next.handle(xhr);
  }
}
bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(
      AppRoutingModule,
      MatInputModule,
      MatFormFieldModule,
      MatSelectModule,
      MatCardModule,
      MatListModule,
      MatTableModule,
      MatGridListModule
    ),
    provideHttpClient(
      withInterceptorsFromDi()
    ),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: XhrInterceptor,
      multi: true
    },
    provideAnimations(),
    AppService
  ]
});
