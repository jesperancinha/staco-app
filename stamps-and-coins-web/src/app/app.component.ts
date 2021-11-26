import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AppService} from "./services/app.service";
import {catchError, Observable, of, retry} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'stamps-and-coins-manager';

  constructor(public appService: AppService, private http: HttpClient, private router: Router) {
  }

  logout() {
    let url = '/api/staco/service/logout';
    this.http.post(url, {}).pipe(
      retry(3), catchError(this.handleError<string>())).subscribe(() => {
        this.appService.token = null;
        window.location.href = "/login";
      }
    );
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      this.router.navigateByUrl('/login');
      return of(result as T);
    };
  }

}
