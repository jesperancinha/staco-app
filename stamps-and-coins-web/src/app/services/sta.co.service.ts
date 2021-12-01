import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of, retry} from "rxjs";
import {Router} from "@angular/router";
import {StaCo} from "../model/staCo";

const root = '/api/staco/service/stacos/';
const searchUrl = root + 'search';
const unfilteredUrl = root + 'unfiltered';

@Injectable({
  providedIn: 'root'
})
export class StaCoService {

  constructor(private http: HttpClient, public router: Router) {
  }

  searchByTerm(term: string, page: number, sizePage: number, sortColumn: string, order: string): Observable<StaCo[]> {
    let url = searchUrl + '/' + (term ? term : "") + '/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<StaCo[]>(url).pipe(
      retry(3), catchError(this.handleError<StaCo[]>()));
  }

  searchUnfiltered(page: number, sizePage: number, sortColumn: string, order: string): Observable<StaCo[]> {
    let url = unfilteredUrl + '/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<StaCo[]>(url).pipe(
      retry(3), catchError(this.handleError<StaCo[]>()));
  }

  countByTerm(term: string, page: number, sizePage: number, sortColumn: string, order: string): Observable<number> {
    let url = root + 'count/search/' + (term ? term : "") + '/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<number>(url).pipe(
      retry(3), catchError(this.handleError<number>()));
  }

  countUnfiltered(page: number, sizePage: number, sortColumn: string, order: string): Observable<number> {
    let url = root + 'count/unfiltered/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<number>(url).pipe(
      retry(3), catchError(this.handleError<number>()));
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      this.router.navigateByUrl("/login");
      return of(result as T);
    };
  }
}

