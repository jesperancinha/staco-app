import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of, retry} from "rxjs";
import {Router} from "@angular/router";
import {StaCo} from "../model/staCo";

const searchDynamoUrl = '/api/staco/ls/stacos';

@Injectable({
  providedIn: 'root'
})
export class StaCoDynamodbService {

  constructor(private http: HttpClient, public router: Router) {
  }

  searchUnfilteredDynamoDb(page: number, sizePage: number): Observable<StaCo[]> {
    let url = searchDynamoUrl + '/search/' + page + '/' + sizePage;
    return this.http.get<StaCo[]>(url).pipe(
      retry(3), catchError(this.handleError<StaCo[]>()));
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

