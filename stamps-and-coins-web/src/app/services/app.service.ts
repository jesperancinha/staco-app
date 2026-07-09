import {inject, Injectable, signal} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {catchError, Observable, of, retry} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  private readonly http = inject(HttpClient);

  readonly token = signal<unknown>(null);
  readonly failedLogin = signal<string | null>(null);

  login(username: string, password: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
      })
    };
    return this.http.post<object>(`/api/staco/service/login`, `username=${username}&password=${password}`, httpOptions)
      .pipe(
        retry(3), catchError(this.handleError<object>()))
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.token.set(null);
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: HttpErrorResponse): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
