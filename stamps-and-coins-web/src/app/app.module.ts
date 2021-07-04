import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SearchComponent} from "./components/search/search.component";
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatCardModule} from "@angular/material/card";
import {MatListModule} from "@angular/material/list";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule} from "@angular/forms";
import {MatTableModule} from "@angular/material/table";
import {NgxPaginationModule} from "ngx-pagination";
import {LoginComponent} from "./components/login/login.component";
import {AppService} from "./services/app.service";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  constructor(private appService: AppService) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + this.appService.token)
    });
    return next.handle(xhr);
  }
}

@NgModule({
  declarations: [
    AppComponent, SearchComponent, LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCardModule,
    MatListModule,
    BrowserAnimationsModule,
    MatTableModule,
    FormsModule,
    NgxPaginationModule
  ],
  providers: [AppService, {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
