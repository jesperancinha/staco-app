import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of, retry} from "rxjs";
import {AppService} from "./app.service";
import {StacoResponse} from "../model/staco.response";

const searchUrl = '/api/staco/service/stacos/search';
const unfilteredUrl = '/api/staco/service/stacos/unfiltered';

@Injectable({
  providedIn: 'root'
})
export class StaCoService {

  constructor(private http: HttpClient, private appService: AppService) {
  }

  searchByTerm(term: string, page: number, sizePage: number, sortColumn: string, order: string): Observable<StacoResponse> {
    let url = searchUrl + '/' + (term ? term : "") + '/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<StacoResponse>(url).pipe(
      retry(3), catchError(this.handleError<StacoResponse>()));
  }
  searchUnfiltered(page: number, sizePage: number, sortColumn: string, order: string): Observable<StacoResponse> {
    let url = unfilteredUrl + '/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<StacoResponse>(url).pipe(
      retry(3), catchError(this.handleError<StacoResponse>()));
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      window.location.href = "http://localhost:4200/login";
      return of(result as T);
    };
  }
}

