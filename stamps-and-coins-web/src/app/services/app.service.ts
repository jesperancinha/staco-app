import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, of, retry} from 'rxjs';

import {User} from "../model/user";
import {StacoResponse} from "../model/staco.response";

@Injectable()
export class AppService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  token: any;
  failedLogin: any;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string) {

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
      })
    };
    return this.http.post<any>(`/api/staco/service/login`, `username=${username}&password=${password}`, httpOptions)
      .pipe(
        retry(3), catchError(this.handleError<StacoResponse>()))
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }


  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      console.log(result)
      return of(result as T);
    };
  }
}
