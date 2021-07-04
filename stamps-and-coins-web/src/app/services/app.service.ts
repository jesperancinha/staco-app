import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, retry} from "rxjs/internal/operators";
import {StaCo} from "../model/staCo";
import {Observable, of} from "rxjs";

@Injectable()
export class AppService {

  token: string;
  failedLogin: string;

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, callback) {

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    if (credentials) {
      this.http.post('/oauth/token',
        'grant_type=password&username=' + credentials.username + '&password=' + credentials.password + '&client_id=stamps-and-coins-client&client_secret=stamps-and-coins&scope=read&redirect_uri=http://localhost:8081/oauth',
        {headers: headers})
        .pipe(retry(3), catchError(this.handleError<StaCo[]>()))
        .subscribe(response => {
          if (response) {
            this.token = response['access_token'];
            this.failedLogin = null;
            return callback && callback();
          }
        });
    }
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      if (error.status == 401) {
        this.failedLogin = "Authentication Failed!"
      }
      return of(result as T);
    };
  }
}
