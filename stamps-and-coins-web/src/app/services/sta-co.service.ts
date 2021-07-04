import {catchError, retry} from 'rxjs/internal/operators';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from "rxjs";
import {AppService} from "./app.service";
import {StaCoResponse} from "../model/staCoResponse";

const localUrl = '/api/staco/all';

@Injectable({
  providedIn: 'root'
})
export class StaCoService {

  constructor(private http: HttpClient, private appService: AppService) {
  }

  searchByTerm(term: string, page: number, sizePage: number, sortColumn: string, order: string): Observable<StaCoResponse> {
    let url = localUrl;
    url += '/' + (term ? term : "") + '/' + page + '/' + sizePage + '/' + sortColumn + '/' + order;
    return this.http.get<StaCoResponse>(url).pipe(
      retry(3), catchError(this.handleError<StaCoResponse>()));
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      if (!this.appService.token) {
        window.location.href = "http://localhost:4200/api/staco/login";
      } else {
        window.location.href = "http://localhost:4200";
      }
      return of(result as T);
    };
  }
}

