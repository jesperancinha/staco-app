import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AppService} from "./services/app.service";
import {Observable, of} from "rxjs";
import {catchError, retry} from "rxjs/internal/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'stamps-and-coins-manager';

  constructor(private app: AppService, private http: HttpClient, private router: Router) {
    this.app.authenticate(undefined, undefined);
  }

  logout() {
    let url
    let destination
    if (this.app.token) {
      url = '/api/staco/logout';
      destination = () => this.router.navigateByUrl('/');
    } else {
      url = '/logout';
      destination = () => window.location.href = 'http://localhost:4200/search';
    }
    this.http.post(url, {}).pipe(
      retry(3), catchError(this.handleError<string>())).subscribe(() => {
        this.app.token = null;
        destination();
      }
    );
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      this.router.navigateByUrl('/');
      return of(result as T);
    };
  }

}
