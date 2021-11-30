import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of, retry} from "rxjs";
import {StacoResponse} from "../model/staco.response";
import {Router} from "@angular/router";

const searchDynamoUrl = '/api/staco/ls/stacos';

@Injectable({
  providedIn: 'root'
})
export class StaCoDynamodbService {

  constructor(private http: HttpClient, public router: Router) {
  }

  searchUnfilteredDynamoDb(page: number, sizePage: number): Observable<StacoResponse> {
    let url = searchDynamoUrl + '/search/' + page + '/' + sizePage;
    return this.http.get<StacoResponse>(url).pipe(
      retry(3), catchError(this.handleError<StacoResponse>()));
  }

  countRecordsDynamoDb(): Observable<number> {
    let url = searchDynamoUrl + '/count';
    return this.http.get<number>(url).pipe(
      retry(3), catchError(this.handleError<number>()));
  }

  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      this.router.navigateByUrl("/dynamo/login");
      return of(result as T);
    };
  }
}

