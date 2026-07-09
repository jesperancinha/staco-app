import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, of, retry} from 'rxjs';
import {Router} from '@angular/router';
import {StaCo} from '../model/sta-co';

const root = '/api/staco/service/stacos/';
const searchUrl = root + 'search';
const unfilteredUrl = root + 'unfiltered';

@Injectable({
  providedIn: 'root'
})
export class StaCoService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  searchByTerm(term: string, page: number, sizePage: number, sortColumn: string, order: string): Observable<StaCo[]> {
    const url = searchUrl + '/' + (term ? term : "") + '/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<StaCo[]>(url).pipe(
      retry(3), catchError(this.handleError<StaCo[]>()));
  }

  searchUnfiltered(page: number, sizePage: number, sortColumn: string, order: string): Observable<StaCo[]> {
    const url = unfilteredUrl + '/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<StaCo[]>(url).pipe(
      retry(3), catchError(this.handleError<StaCo[]>()));
  }

  countByTerm(term: string, page: number, sizePage: number, sortColumn: string, order: string): Observable<number> {
    const url = root + 'count/search/' + (term ? term : "") + '/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<number>(url).pipe(
      retry(3), catchError(this.handleError<number>()));
  }

  countUnfiltered(page: number, sizePage: number, sortColumn: string, order: string): Observable<number> {
    const url = root + 'count/unfiltered/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<number>(url).pipe(
      retry(3), catchError(this.handleError<number>()));
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: HttpErrorResponse): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      this.router.navigateByUrl("/login");
      return of(result as T);
    };
  }
}
